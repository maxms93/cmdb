package cmdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import jdk.internal.util.xml.impl.Pair;
import model.ApplicationSoftware;
import model.CI;
import model.Person;
import model.RAM;
import model.Server;
import model.SystemSoftware;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class CmdbController {

	private static String remoteIP = "xxx.xx.xx.xx";
	private static String localIP = "localhost";

	private static String currentIP = localIP;

	public static String updateEndPoint = "http://" + currentIP + ":3030/cmdb/update";
	public static String queryEndPoint = "http://" + currentIP + ":3030/cmdb/query";

	public static String propertyPrefix = "PREFIX prop: <http://artmayr.com/property/>\n";
	public static String ontologyPrefix = "PREFIX ont: <http://artmayr.com/ontology/>\n";

	private static void getDataFromFuseki() {
		ArrayList<CI> listOfCI = getRAMFromFuseki();
		listOfCI.addAll(getSoftwareFromFuseki());

		for (CI ci : listOfCI) {
			System.out.println(ci.toString());
		}

	}

	public static ArrayList<CI> getDataFromFusekiAll() {
		ArrayList<CI> listOfCI = getRAMFromFuseki();
		listOfCI.addAll(getSoftwareFromFuseki());

		return listOfCI;
	}

	private static void writeStuffToFuseki() {
		// Demo data
		ArrayList<CI> myComponents = new ArrayList<CI>();
		RAM myRam1 = new RAM(101, "Corsair MEGAAA RAM", "16 TB", "9001 MHz");
		RAM myRam2 = new RAM(102, "Corsair MEGAAA RAM", "16 TB", "9001 MHz");
		myComponents.add(myRam1);
		myComponents.add(myRam2);
		Server myServer = new Server(1, "myServer", true, false, myComponents);

		ArrayList<CI> mySoftware = new ArrayList<CI>();
		ApplicationSoftware myAS = new ApplicationSoftware(80, "SAP", 9001);
		SystemSoftware mySS = new SystemSoftware(90, "Windows 10", true);
		mySoftware.add(myAS);
		mySoftware.add(mySS);
		Person myPerson = new Person(990, "123456789", "Hans", "Huber", mySoftware);
		// End Demo data

		ArrayList<Server> myServers = new ArrayList<Server>();
		ArrayList<Person> myPeople = new ArrayList<Person>();
		myServers.add(myServer);
		myPeople.add(myPerson);

		writeServersToFuseki(myServers);
		writePeopleToFuseki(myPeople);
	}

	private static void writeServersToFuseki(ArrayList<Server> myServers) {
		for(Server s : myServers)
		{
			String nameNew = s.getBezeichnung().replace(" ", "_");
			UpdateRequest update = UpdateFactory.create(
					CmdbController.propertyPrefix +
	                CmdbController.ontologyPrefix +
                    "INSERT DATA\n{\n<http://artmayr.com/resource/" + nameNew +"/" + s.getId()+ "> " +
                    "prop:type \"" + s.getType() + "\" ;\n" +
                    "prop:name \"" + s.getBezeichnung() + "\" ;\n" +
                    "prop:isVirtualized \"" + s.isVirtualized() + "\" ;\n" +
                    "prop:isSharedServer \"" + s.isSharedServer() + "\".\n}");
		    UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
		    processor.execute();

			if(s.getListComponents() != null)
			{			
				for(CI comp : s.getListComponents())
				{
					if(comp instanceof RAM)
					{
						update = UpdateFactory.create(
								CmdbController.propertyPrefix +
				                CmdbController.ontologyPrefix +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/"  + comp.getBezeichnung().replace(" ", "_") + "/" + comp.getId() + "> " +
				                "prop:type \"" + comp.getType() + "\" ;\n" +
				                "prop:name \"" + comp.getBezeichnung() + "\" ;\n" +
				                "prop:groesse \"" + ((RAM) comp).getGroesse() + "\" ;\n" +
				                "prop:taktung \"" + ((RAM) comp).getTaktung() + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					    
					    update = UpdateFactory.create(
					    		CmdbController.propertyPrefix +
				                CmdbController.ontologyPrefix +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + nameNew +"/" + s.getId()+ ">" +
				                "prop:hasComponent \"" + comp.getBezeichnung().replace(" ", "_") + "/" + comp.getId() + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					}
				}
			}
		}
	}

	private static void writePeopleToFuseki(ArrayList<Person> myPeople) {
		for (Person p : myPeople) {
			String nameNew = p.getBezeichnung().replace(" ", "_");
			UpdateRequest update = UpdateFactory.create(CmdbController.propertyPrefix + CmdbController.ontologyPrefix
					+ "INSERT DATA\n{\n<http://artmayr.com/resource/" + nameNew + "/" + p.getId() + "> " +
					"prop:type \"" + p.getType() + "\" ;\n" +
					"prop:name \"" + p.getBezeichnung() + "\" ;\n" +
					"prop:vorname \"" + p.getVorname() + "\" ;\n" +
					"prop:nachname \"" + p.getNachname() + "\".\n}");
			UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
			processor.execute();

			if (p.getListUsage() != null) {
				for (CI usage : p.getListUsage()) {
					if (usage instanceof ApplicationSoftware) {
						update = UpdateFactory.create(CmdbController.propertyPrefix + CmdbController.ontologyPrefix
								+ "INSERT DATA\n{\n<http://artmayr.com/resource/" + usage.getBezeichnung().replace(" ", "_") + "/" + usage.getId() + "> " +
								"prop:type \"" + usage.getType() + "\" ;\n" +
								"prop:name \"" + usage.getBezeichnung() + "\" ;\n" +
								"prop:linesOfCode \"" + ((ApplicationSoftware) usage).getLinesOfCode() + "\" .\n}");
						processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
						processor.execute();

						update = UpdateFactory.create(CmdbController.propertyPrefix + CmdbController.ontologyPrefix
								+ "INSERT DATA\n{\n<http://artmayr.com/resource/" + nameNew + "/" + p.getId() + ">"
								+ "prop:isUsing \"" + usage.getBezeichnung().replace(" ", "_") + "/" + usage.getId()
								+ "\" .\n}");
						processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
						processor.execute();
					}
					if (usage instanceof SystemSoftware) {
						update = UpdateFactory.create(CmdbController.propertyPrefix + CmdbController.ontologyPrefix
								+ "INSERT DATA\n{\n<http://artmayr.com/resource/" + usage.getBezeichnung().replace(" ", "_") + "/" + usage.getId() + "> " +
								"prop:type \"" + usage.getType() + "\" ;\n" +
								"prop:name \"" + usage.getBezeichnung() + "\" ;\n" +
								"prop:os \"" + ((SystemSoftware) usage).isOS() + "\" .\n}");
						processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
						processor.execute();

						update = UpdateFactory.create(CmdbController.propertyPrefix + CmdbController.ontologyPrefix
								+ "INSERT DATA\n{\n<http://artmayr.com/resource/" +nameNew + "_" + p.getId() + ">"
								+ "prop:isUsing \"" + usage.getBezeichnung().replace(" ", "_") + "/" + usage.getId()
								+ "\" .\n}");
						processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
						processor.execute();
					}
				}
			}
		}
	}

	private static void clearAllOnFuseki() {
		UpdateRequest delete = UpdateFactory.create("CLEAR ALL");
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(delete, updateEndPoint);
		processor.execute();
	}

	private static ArrayList<CI> getRAMFromFuseki() {
		ArrayList<CI> listRAM = null;
		ResultSet result;
		ParameterizedSparqlString componentQuery = new ParameterizedSparqlString("" + CmdbController.propertyPrefix
				+ CmdbController.ontologyPrefix + "SELECT\n" + " ?type ?bez ?groesse ?taktung \n" + "WHERE {\n" +
				//"?x prop:id ?id . \n" +
				"?x prop:type ?type . \n" + 
				"?x prop:name ?bez . \n" +
				"?x prop:groesse ?groesse . \n" + 
				"?x prop:taktung ?taktung . \n" + 
				"}\n");
		QueryExecution exec = QueryExecutionFactory.sparqlService(queryEndPoint, componentQuery.asQuery());
		try {
			result = exec.execSelect();
			listRAM = new ArrayList<CI>();
			while (result.hasNext()) {
				QuerySolution nextSolution = result.next();

				//RDFNode id = nextSolution.get("id");
				RDFNode type = nextSolution.get("type");
				RDFNode bezeichnung = nextSolution.get("bez");
				RDFNode groesse = nextSolution.get("groesse");
				RDFNode taktung = nextSolution.get("taktung");

				RAM tempRAM = new RAM();
				//tempRAM.setId(id.asLiteral().getInt());
				tempRAM.setType(type.toString());
				tempRAM.setBezeichnung(bezeichnung.toString());
				tempRAM.setGroesse(groesse == null ? "" : groesse.toString());
				tempRAM.setTaktung(taktung == null ? "" : taktung.toString());
				listRAM.add(tempRAM);
			}
		} finally {
			exec.close();
		}
		return listRAM;
	}

	private static ArrayList<CI> getSoftwareFromFuseki() {
		ArrayList<CI> listSoftware = null;
		ResultSet result;
		ParameterizedSparqlString componentQuery = new ParameterizedSparqlString("" + CmdbController.propertyPrefix
				+ CmdbController.ontologyPrefix +
				"SELECT\n" + "?type ?bez ?linesOfCode ?os \n" + 
				"WHERE {\n" + 
				//"?x prop:id ?id . \n" + 
				"?x prop:type ?type . \n" + 
				"?x prop:name ?bez . \n" +
				// "?x prop:linesOfCode ?linesOfCode . \n" +
				// "?x prop:os ?os . \n" +
				// "OPTIONAL {?x prop:os ?os . \n ?x prop:linesOfCode ?linesOfCode . } \n" +
				"FILTER (REGEX(?type, 'Software')) . \n" + "}\n");
		QueryExecution exec = QueryExecutionFactory.sparqlService(queryEndPoint, componentQuery.asQuery());
		try {
			result = exec.execSelect();
			listSoftware = new ArrayList<CI>();
			while (result.hasNext()) {
				QuerySolution nextSolution = result.next();

				//RDFNode id = nextSolution.get("id");
				RDFNode bezeichnung = nextSolution.get("bez");
				RDFNode type = nextSolution.get("type");
				if (type.toString().equals("SystemSoftware")) {
					SystemSoftware ss = new SystemSoftware();
					//ss.setId(id.asLiteral().getInt());
					ss.setBezeichnung(bezeichnung.toString());
					ss.setType(type.toString());
					// ss.setOS(nextSolution.get("isOS").asLiteral().getBoolean());

					listSoftware.add(ss);
				}
				if (type.toString().equals("ApplicationSoftware")) {
					ApplicationSoftware as = new ApplicationSoftware();
					//as.setId(id.asLiteral().getInt());
					as.setBezeichnung(bezeichnung.toString());
					as.setType(type.toString());
					// as.setLinesOfCode(nextSolution.get("linesOfCode").asLiteral().getInt());

					listSoftware.add(as);
				}
			}
		} finally {
			exec.close();
		}
		return listSoftware;
	}

}
