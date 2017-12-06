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
		
		getComponentsFromFuseki();

		writeStuffToFuseki();
	}

	private static void writeStuffToFuseki() {
		//Demo data
		ArrayList<CI> myCIs = new ArrayList<CI>();
		RAM myRam1 = new RAM(101, "Corsair MEGAAA RAM", "16 TB", "9001 MHz");
		RAM myRam2 = new RAM(102, "Corsair MEGAAA RAM", "16 TB", "9001 MHz");
		myCIs.add(myRam1);
		myCIs.add(myRam2);
		Server myServer = new Server(1, "myServer", true, false, myCIs);
		//End Demo data
		
		ArrayList<Server> myServers = new ArrayList<Server>();
		myServers.add(myServer);
		
		for(Server s : myServers)
		{
			String nameNew = "";
	        
			nameNew = s.getBezeichnung().replace(" ", "_");
			UpdateRequest update = UpdateFactory.create(
            		"PREFIX prop: <http://artmayr.com/property/>\n" +
                    "PREFIX ont: <http://artmayr.com/ontology/>\n" +
                    "INSERT DATA\n{\n<http://artmayr.com/resource/" + nameNew + "> " +
                    "prop:name \"" + myServer.getBezeichnung() + "\" ;\n" +
                    "prop:isVirtualized \"" + myServer.isVirtualized() + "\" ;\n" +
                    "prop:isSharedServer \"" + myServer.isSharedServer() + "\".\n}");
		    UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
		    processor.execute();

			if(s.getlComponents() != null)
			{			
				for(CI comp : s.getlComponents())
				{
					if(comp instanceof RAM)
					{
						update = UpdateFactory.create(
				        		"PREFIX prop: <http://artmayr.com/property/>\n" +
				                "PREFIX ont: <http://artmayr.com/ontology/>\n" +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + comp.getId() + "/" + comp.getBezeichnung().replace(" ", "_") + "> " +
				                "prop:name \"" + comp.getBezeichnung() + "\" ;\n" +
				                "prop:groesse \"" + ((RAM) comp).getGroesse() + "\" ;\n" +
				                "prop:taktung \"" + ((RAM) comp).getTaktung() + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					    
					    update = UpdateFactory.create(
				        		"PREFIX prop: <http://artmayr.com/property/>\n" +
				                "PREFIX ont: <http://artmayr.com/ontology/>\n" +
				                "INSERT DATA\n{\n<http://artmayr.com/resource/" + nameNew + ">" +
				                "prop:hasComponent \"" + comp.getId() + "/" + comp.getBezeichnung().replace(" ", "_") + "\" .\n}");
					    processor = UpdateExecutionFactory.createRemote(update, updateEndPoint);
					    processor.execute();
					}
				}
			}
		}
	}

	private static void getComponentsFromFuseki() {
		ResultSet result;
		ParameterizedSparqlString componentQuery = new ParameterizedSparqlString("" +
		        "PREFIX ont: <http://artmayr.com/ontology/>\n" +
		        "PREFIX prop: <http://artmayr.com/property/>\n" +
		        "SELECT\n" +
		        "?bez  ?groesse ?taktung \n" +
		        "WHERE {\n" +
		        "?name prop:name ?bez . \n" +
		        "}\n"
		);
		QueryExecution exec = QueryExecutionFactory.sparqlService(queryEndPoint, componentQuery.asQuery());
		try {
		    result = exec.execSelect();
		    ArrayList<CI> lCI = new ArrayList<CI>();
		    while (result.hasNext()) {
		        QuerySolution nextSolution = result.next();

		        RDFNode bezeichnung = nextSolution.get("bez");
		        RDFNode groesse = nextSolution.get("groesse");
		        RDFNode taktung = nextSolution.get("taktung");

		        RAM tempRAM = new RAM();
		        tempRAM.setBezeichnung(bezeichnung.toString());
		        tempRAM.setGroesse(groesse == null ? "" : groesse.toString());
		        tempRAM.setTaktung(taktung == null ? "" : taktung.toString());
		        lCI.add(tempRAM);
		        System.out.println(tempRAM.getBezeichnung().toString());
		    }
		} finally {
		    exec.close();
		}
	}
}
