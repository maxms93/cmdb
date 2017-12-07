package cmdb;

import java.util.ArrayList;

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

import model.*;

public class Test {

	private static String remoteIP = "xxx.xx.xx.xx";
	private static String localIP = "localhost";

	private static String currentIP = localIP;

	private static String updateEndPoint = "http://" + currentIP + ":3030/cmdb/update";
	private static String queryEndPoint = "http://" + currentIP + ":3030/cmdb/query";

	public static void main(String[] args) {

		/*
		 * Model m = ModelFactory.createDefaultModel();
		 * 
		 * Resource r = m.createResource("http://max.sand.com/test/r"); Property p =
		 * m.createProperty("http://max.sand.com/test/p");
		 * 
		 * r.addProperty(p, "Max" , XSDDatatype.XSDstring);
		 * 
		 * m.write(System.out , "Turtle");
		 * 
		 * String serviceURI = "http://localhost:3030/cmdb/data"; DatasetAccessorFactory
		 * factory = null; DatasetAccessor accessor; accessor =
		 * factory.createHTTP(serviceURI);
		 * 
		 * accessor.putModel(m);
		 */
		
		getDataFromFuseki();

		clearAllOnFuseki();
		
		writeStuffToFuseki();
	}

	private static void getDataFromFuseki() {
		ArrayList<CI> listOfCI = getRAMFromFuseki();
		listOfCI.addAll(getSoftwareFromFuseki());
		
		for(CI ci : listOfCI)
		{
			System.out.println(ci.toString());
		}
	}

	private static void writeStuffToFuseki() {
		//Demo data
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
		//End Demo data
		
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
            		"PREFIX prop: <http://artmayr.com/property/>\n" +
                    "PREFIX ont: <http://artmayr.com/ontology/>\n" +
                    "INSERT DATA\n{\n<http://artmayr.com/resource/" + nameNew + "> " +
                    "prop:id \"" + s.getId() + "\" ;\n" +
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
				        		"PREFIX prop: <http://artmayr.com/property/>\n" +
				                "PREFIX ont: <http://artmayr.com/ontology/>\n" +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + comp.getId() + "_" + comp.getBezeichnung().replace(" ", "_") + "> " +
				                "prop:id \"" + comp.getId() + "\" ;\n" +
				                "prop:type \"" + comp.getType() + "\" ;\n" +
				                "prop:name \"" + comp.getBezeichnung() + "\" ;\n" +
				                "prop:groesse \"" + ((RAM) comp).getGroesse() + "\" ;\n" +
				                "prop:taktung \"" + ((RAM) comp).getTaktung() + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					    
					    update = UpdateFactory.create(
				        		"PREFIX prop: <http://artmayr.com/property/>\n" +
				                "PREFIX ont: <http://artmayr.com/ontology/>\n" +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + nameNew + ">" +
				                "prop:hasComponent \"" + comp.getId() + "_" + comp.getBezeichnung().replace(" ", "_") + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					}
				}
			}
		}
	}
	
	private static void writePeopleToFuseki(ArrayList<Person> myPeople) {
		for(Person p : myPeople)
		{
			String nameNew = p.getBezeichnung().replace(" ", "_");
			UpdateRequest update = UpdateFactory.create(
            		"PREFIX prop: <http://artmayr.com/property/>\n" +
                    "PREFIX ont: <http://artmayr.com/ontology/>\n" +
                    "INSERT DATA\n{\n<http://artmayr.com/resource/" + p.getId() + "_" + nameNew + "> " +
                    "prop:id \"" + p.getId() + "\" ;\n" +
                    "prop:type \"" + p.getType() + "\" ;\n" +
                    "prop:name \"" + p.getBezeichnung() + "\" ;\n" +
                    "prop:vorname \"" + p.getVorname() + "\" ;\n" +
                    "prop:nachname \"" + p.getNachname() + "\".\n}");
		    UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
		    processor.execute();

			if(p.getListUsage() != null)
			{
				for(CI usage : p.getListUsage())
				{
					if(usage instanceof ApplicationSoftware)
					{
						update = UpdateFactory.create(
				        		"PREFIX prop: <http://artmayr.com/property/>\n" +
				                "PREFIX ont: <http://artmayr.com/ontology/>\n" +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + usage.getId() + "_" + usage.getBezeichnung().replace(" ", "_") + "> " +
				                "prop:id \"" + usage.getId() + "\" ;\n" +
				                "prop:type \"" + usage.getType() + "\" ;\n" +
				                "prop:name \"" + usage.getBezeichnung() + "\" ;\n" +
				                "prop:linesOfCode \"" + ((ApplicationSoftware) usage).getLinesOfCode() + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					    
					    update = UpdateFactory.create(
				        		"PREFIX prop: <http://artmayr.com/property/>\n" +
				                "PREFIX ont: <http://artmayr.com/ontology/>\n" +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + p.getId() + "_" + nameNew + ">" +
				                "prop:isUsing \"" + usage.getId() + "_" + usage.getBezeichnung().replace(" ", "_") + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					}
					if(usage instanceof SystemSoftware)
					{
						update = UpdateFactory.create(
				        		"PREFIX prop: <http://artmayr.com/property/>\n" +
				                "PREFIX ont: <http://artmayr.com/ontology/>\n" +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + usage.getId() + "_" + usage.getBezeichnung().replace(" ", "_") + "> " +
				                "prop:id \"" + usage.getId() + "\" ;\n" +
				                "prop:type \"" + usage.getType() + "\" ;\n" +
				                "prop:name \"" + usage.getBezeichnung() + "\" ;\n" +
				                "prop:os \"" + ((SystemSoftware) usage).isOS() + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					    
					    update = UpdateFactory.create(
				        		"PREFIX prop: <http://artmayr.com/property/>\n" +
				                "PREFIX ont: <http://artmayr.com/ontology/>\n" +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + p.getId() + "_" + nameNew + ">" +
				                "prop:isUsing \"" + usage.getId() + "_" + usage.getBezeichnung().replace(" ", "_") + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					}
				}
			}
		}
	}

	private static void clearAllOnFuseki() {
		UpdateRequest delete = UpdateFactory.create(
                "CLEAR ALL");
	    UpdateProcessor processor = UpdateExecutionFactory.createRemote(delete, updateEndPoint);
	    processor.execute();
	}

	private static ArrayList<CI> getRAMFromFuseki() {
		ArrayList<CI> listRAM = null;
		ResultSet result;
		ParameterizedSparqlString componentQuery = new ParameterizedSparqlString("" +
		        "PREFIX ont: <http://artmayr.com/ontology/>\n" +
		        "PREFIX prop: <http://artmayr.com/property/>\n" +
		        "SELECT\n" +
		        "?id ?type ?bez ?groesse ?taktung \n" +
		        "WHERE {\n" +
		        "?x prop:id ?id . \n" +
		        "?x prop:type ?type . \n" +
		        "?x prop:name ?bez . \n" +
		        "?x prop:groesse ?groesse . \n" +
		        "?x prop:taktung ?taktung . \n" +
		        "}\n"
		);
		QueryExecution exec = QueryExecutionFactory.sparqlService(queryEndPoint, componentQuery.asQuery());
		try {
		    result = exec.execSelect();
		    listRAM = new ArrayList<CI>();
		    while (result.hasNext()) {
		        QuerySolution nextSolution = result.next();

		        RDFNode id = nextSolution.get("id");
		        RDFNode type = nextSolution.get("type");
		        RDFNode bezeichnung = nextSolution.get("bez");
		        RDFNode groesse = nextSolution.get("groesse");
		        RDFNode taktung = nextSolution.get("taktung");

		        RAM tempRAM = new RAM();
		        tempRAM.setId(id.asLiteral().getInt());
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
		ParameterizedSparqlString componentQuery = new ParameterizedSparqlString("" +
		        "PREFIX ont: <http://artmayr.com/ontology/>\n" +
		        "PREFIX prop: <http://artmayr.com/property/>\n" +
		        "SELECT\n" +
		        "?id ?type ?bez ?linesOfCode ?os \n" +
		        "WHERE {\n" +
		        "?x prop:id ?id . \n" +
		        "?x prop:type ?type . \n" +
		        "?x prop:name ?bez . \n" +
		        //"?x prop:linesOfCode ?linesOfCode . \n" +
		        //"?x prop:os ?os . \n" +
		        //"OPTIONAL {?x prop:os ?os . \n ?x prop:linesOfCode ?linesOfCode . } \n" +
		        "FILTER (REGEX(?type, 'Software')) . \n" +
		        "}\n"
		);
		QueryExecution exec = QueryExecutionFactory.sparqlService(queryEndPoint, componentQuery.asQuery());
		try {
		    result = exec.execSelect();
		    listSoftware = new ArrayList<CI>();
		    while (result.hasNext()) {
		        QuerySolution nextSolution = result.next();
		        
		        RDFNode id = nextSolution.get("id");
		        RDFNode bezeichnung = nextSolution.get("bez");
		        RDFNode type = nextSolution.get("type");
		        if(type.toString().equals("SystemSoftware"))
		        {
		        	SystemSoftware ss = new SystemSoftware();
		        	ss.setId(id.asLiteral().getInt());
		        	ss.setBezeichnung(bezeichnung.toString());
		        	ss.setType(type.toString());
		        	//ss.setOS(nextSolution.get("isOS").asLiteral().getBoolean());

			        listSoftware.add(ss);
		        }
		        if(type.toString().equals("ApplicationSoftware"))
		        {
		        	ApplicationSoftware as = new ApplicationSoftware();
		        	as.setId(id.asLiteral().getInt());
		        	as.setBezeichnung(bezeichnung.toString());
		        	as.setType(type.toString());
		        	//as.setLinesOfCode(nextSolution.get("linesOfCode").asLiteral().getInt());
		        	
			        listSoftware.add(as);
		        }
		    }
		} finally {
		    exec.close();
		}
		return listSoftware;
	}
}
