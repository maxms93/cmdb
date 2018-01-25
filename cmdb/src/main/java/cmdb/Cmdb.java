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

public class Cmdb {

	private static String remoteIP = "xxx.xx.xx.xx";
	private static String localIP = "localhost";

	private static String currentIP = localIP;

	private static String updateEndPoint = "http://" + currentIP + ":3030/cmdb/update";
	private static String queryEndPoint = "http://" + currentIP + ":3030/cmdb/query";
	

	public static void main(String[] args) {		
		getDataFromFuseki();

		clearAllOnFuseki();
		
		writeStuffToFuseki();
	}

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
		
		ArrayList<CI> myServerComponents = new ArrayList<CI>();
		RAM myRam1 = new RAM("Corsair MEGAAA RAM", "16 TB", "9001 MHz");
		RAM myRam2 = new RAM("Corsair MEGAAA RAM", "16 TB", "9001 MHz");
		Harddisk hd1 = new Harddisk("WD Red Pro", "10 TB", "SATA3");
		myServerComponents.add(myRam1);
		myServerComponents.add(myRam2);
		myServerComponents.add(hd1);
		Server myServer = new Server("myServer", true, false, myServerComponents);

		ArrayList<CI> mySoftware1 = new ArrayList<CI>();
		ArrayList<CI> mySoftware2 = new ArrayList<CI>();
		ApplicationSoftware myAS = new ApplicationSoftware("SAP", 9001);
		SystemSoftware mySS = new SystemSoftware("Windows 10", true);
		mySoftware1.add(myAS);
		mySoftware1.add(mySS);
		mySoftware2.add(mySS);
		Person myPerson1 = new Person("1234-010190", "Hans", "Huber", mySoftware1);
		Person myPerson2 = new Person("4321-311295", "Thomas", "Maier", mySoftware2);
		
		ArrayList<CI> myPcComponents = new ArrayList<CI>();
		RAM myRam3 = new RAM("Samsung Low-Volt 760XE", "32 GB", "2333 MHz");
		Harddisk hd2 = new Harddisk("WD Blue", "4 TB", "SATA3");
		myPcComponents.add(myRam3);
		myPcComponents.add(hd2);
		PC myPc1 = new PC("Custom PC 007", false, myPcComponents);
		PC myPc2 = new PC("Custom PC 008", false, myPcComponents);
		
		ArrayList<Server> myServers = new ArrayList<Server>();
		ArrayList<Person> myPeople = new ArrayList<Person>();
		ArrayList<PC> myPcs = new ArrayList<PC>();
		myServers.add(myServer);
		myPeople.add(myPerson1);
		myPeople.add(myPerson2);
		myPcs.add(myPc1);
		myPcs.add(myPc2);

		// End Demo data
		
		for(Server s : myServers)
		{
			writeCIToFuseki(s);
		}
		for(Person p : myPeople)
		{
			writeCIToFuseki(p);
		}
		for(PC pc : myPcs)
		{
			writeCIToFuseki(pc);
		}
		
		//Test, ob einzelne CI's ohne spezifische Zugehörigkeit dem Triple-Store hinzugefügt werden können
		RAM myRamSingle = new RAM("LONELY RAM", "8 MB", "100 MHz");
		writeCIToFuseki(myRamSingle);
	}

	private static void writeCIToFuseki(CI myCI) {
		//String nameNew = myCI.getBezeichnung().replace(" ", "_");
		UpdateRequest update = UpdateFactory.create(myCI.insertCI());
	    UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
	    processor.execute();

	    if(myCI instanceof Server)
	    {
			if(((Server) myCI).getListComponents() != null)
			{
				for(CI comp : ((Server) myCI).getListComponents())
				{
						//Insert RAM in Store
						update = UpdateFactory.create(comp.insertCI());
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					    
					    //Insert RAM in Server Triple
					    update = UpdateFactory.create(comp.appendCItoCI(myCI.getType(), myCI.getId()));
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
				}
			}
		}
	    else if(myCI instanceof Person)
	    {
	    	if (((Person) myCI).getListUsage() != null)
	    	{
				for (CI usage : ((Person) myCI).getListUsage())
				{
					update = UpdateFactory.create(usage.insertCI());
				    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
				    processor.execute();
				    
				    update = UpdateFactory.create(usage.appendCItoCI(myCI.getType(), myCI.getId()));
				    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
				    processor.execute();
				}
			}
	    }
	    else if(myCI instanceof PC)
	    {
	    	if (((PC) myCI).getListComponents() != null)
	    	{
				for (CI comp : ((PC) myCI).getListComponents())
				{
					update = UpdateFactory.create(comp.insertCI());
				    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
				    processor.execute();
				    
				    update = UpdateFactory.create(comp.appendCItoCI(myCI.getType(), myCI.getId()));
				    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
				    processor.execute();
				}
			}
	    }
	    else //Alles andere, also beispielweise einzelne RAM-Riegel, ohne spezifische Zugehörigkeit
	    {
	    	update = UpdateFactory.create(myCI.insertCI());
		    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
		    processor.execute();
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
				//"?x prop:type ?type . \n" + 
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
				//tempRAM.setType(type.toString());
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
				//"?x prop:type ?type . \n" + 
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
