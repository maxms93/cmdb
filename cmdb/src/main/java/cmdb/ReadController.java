package cmdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.graph.*;
import org.apache.jena.query.*;
import org.apache.jena.sparql.core.*;
import org.apache.jena.sparql.engine.http.HttpQuery;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.arq.querybuilder.*;

import java.lang.ref.Reference;
import java.lang.reflect.Field;

import model.*;

//https://jena.apache.org/documentation/extras/querybuilder/index.html
//https://jena.apache.org/documentation/query/parameterized-sparql-strings.html

//https://jena.apache.org/download/maven.html
//https://mvnrepository.com/artifact/org.apache.jena/jena-extras/3.5.0

// https://github.com/ncbo/sparql-code-examples/blob/master/java/src/org/ncbo/stanford/sparql/examples/GetAllTerms.java

// http://sparql.bioontology.org/examples

public class ReadController extends HttpServlet {
	
	// <http://artmayr.com/resource/"TYPE von CI"/
	
	
	private static String PREFIXES = 
			"PREFIX res: <http://artmayr.com/resource/>\n"+
			"PREFIX prop: <http://artmayr.com/property/>\n"+
			"PREFIX ont: <http://artmayr.com/ontology/>\n";
	
	public static String rescourceUri = "http://artmayr.com/resource/";
	public static String propertyUri = "http://artmayr.com/property/";
	public static String ontologyUri = "http://artmayr.com/ontology/";
	
	public static ArrayList<CI> allFoundCIs = null;
	
	private static QueryExecution exec = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//String id = request.getParameter("id");
		
		response.sendRedirect("index.jsp");

	}
	
	public static ArrayList<CI> GetAllServerFromDB()
	{
		ArrayList<CI> listOfCI = null;
		String className = Server.class.getSimpleName();
		String serverUri = rescourceUri + className;
		
		QueryExecution exec = null;
		try {
			Node graphName1 = NodeFactory.createURI(serverUri); 
			
			
			SelectBuilder sb = new SelectBuilder();
			sb.addPrefix("prop", "http://artmayr.com/property/");
			sb.addPrefix("server", graphName1.getURI());
			sb.addVar( "*" );
			//sb.addWhere( "?x", "prop:id", "?id" );
			//sb.addWhere( "?x", "prop:type", "?type" );
			sb.addWhere( "?x", "prop:name", "?bez");
			sb.addFilter("REGEX(str(?x), '"+ className +"')");
			//sb.addFilter("REGEX(?type, '"+ className +"')");
			
			
			// sb.setVar( Var.alloc( "?o" ), graphName1.getURI() ) ;
			Query q = sb.build();
			
			exec = QueryExecutionFactory.sparqlService(CmdbController.queryEndPoint, q);
			
			ResultSet result = exec.execSelect();
			
			listOfCI = new ArrayList<CI>();
		    while (result.hasNext()) {
		        QuerySolution nextSolution = result.next();

		        RDFNode uri = nextSolution.get("x");
		        //RDFNode id = nextSolution.get("id");
		        //RDFNode type = nextSolution.get("type");
		        RDFNode bezeichnung = nextSolution.get("bez");
		        
		        
		        String searchSubStr = className + "/";
		        int indexFromId = uri.toString().indexOf(searchSubStr);
		        
		        if (indexFromId > 0) {
			        String strId = uri.toString().substring(indexFromId + searchSubStr.length());
	
			        //CI tempCI = new Server();
			        
			        Class cls = Class.forName("model."+className);	
			        model.CI tempCI = (CI) cls.newInstance();
			    
			        //tempCI.setId(id.asLiteral().getInt());
			        tempCI.setId(Integer.parseInt(strId));
			        tempCI.setType(className);
			        tempCI.setBezeichnung(bezeichnung.toString());
		        
			        listOfCI.add(tempCI);
		        }
		    }
		    
		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		finally {
			if (exec != null)
				exec.close();
		}
		return listOfCI;
	}

	public static ArrayList<CI> getAllCiFromDB() {
		
		ArrayList<CI> listOfCI = new ArrayList<CI>();
				
		try
		{
			//ResultSet result;
			
			List<String> listOfModelsResource = new ArrayList<String>();
			listOfModelsResource.add("Server");
			listOfModelsResource.add("ApplicationSoftware");
			listOfModelsResource.add("Harddisk");
			listOfModelsResource.add("PC");
			listOfModelsResource.add("Person");
			listOfModelsResource.add("RAM");
			listOfModelsResource.add("SystemSoftware");
			
			for (String className : listOfModelsResource) {
				
				listOfCI.addAll(getAllCiFromDB(className));
			}
			
			allFoundCIs = new ArrayList<CI>();
			allFoundCIs.addAll(listOfCI);
		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		finally {
			if (exec != null)
				exec.close();
		}
		
		return listOfCI;
	}
	
	public static ArrayList<CI> getAllCiFromDB(String className) {
		ArrayList<CI> listOfCI = new ArrayList<CI>();
		
		//QueryExecution exec = null;
		
		try
		{
			
			if (className != null && className != "") {
				
				Class cls = Class.forName("model."+ className);
				String[] properties = GetPropertiesFromClass(cls);
				String queryBatch = getQueryTermBatch(Arrays.asList(className), properties);
				ResultSet rs = executeQuery(queryBatch);
				
				GetCiFromResultSet(className, listOfCI, cls, rs);
				
				//if (exec != null)
				//	exec.close();
			}
		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		finally {
			if (exec != null)
				exec.close();
		}
		
		return listOfCI;
	}
	
	public static ArrayList<CI> getCiFromDB(String className, String id) {
		ArrayList<CI> listOfCI = new ArrayList<CI>();
		
		//QueryExecution exec = null;
		
		try
		{
			
			if (className != null && className != "") {
				
				Class cls = Class.forName("model."+ className);
				String[] properties = GetPropertiesFromClass(cls);
				String queryTerm = getQueryTerm(className, id, properties);
				ResultSet rs = executeQuery(queryTerm);
				
				GetCiFromResultSet(className, listOfCI, cls, rs);
				
				//if (exec != null)
				//	exec.close();
			}
		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		finally {
			if (exec != null)
				exec.close();
		}
		
		return listOfCI;
	}
	
	public static ArrayList<CI> getHasCompFromCi(String className, String id) {
		ArrayList<CI> listOfCI = new ArrayList<CI>();
		
		ArrayList<CI> tempListOfCi = new ArrayList<CI>();
		
		try
		{
			
			if (className != null && className != "") {
				
				//Class cls = Class.forName("model."+ className);
				//String[] properties = GetPropertiesFromClass(cls);
				//String[] properties = { "prop:hasComponent" };
				
				// Mit hasComponent
				//String queryTerm = getQueryHasComp(className, id);
				//ResultSet rs = executeQuery(queryTerm);
				//GetCiFromResultSet(tempListOfCi, rs, "hasComponent");
				
				// oder mit isUsing
				String queryTerm = getQueryIsUsing(className, id);
				ResultSet rs = executeQuery(queryTerm);				
				GetCiFromResultSet(tempListOfCi, rs, "isUsing");
			}
		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		finally {
			if (exec != null)
				exec.close();
		}
		
		try
		{
			for (CI comp : tempListOfCi) {
				
				listOfCI.addAll(getCiFromDB(comp.getType(), String.valueOf(comp.getId()) ));
			}
		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		finally {
			if (exec != null)
				exec.close();
		}
		
		return listOfCI;
	}


	private static void GetCiFromResultSet(String className, ArrayList<CI> listOfCI, Class cls, ResultSet rs)
			throws InstantiationException, IllegalAccessException {
		while (rs.hasNext()) {
			QuerySolution sol = rs.next();
			
			model.CI tempCI = (CI) cls.newInstance();
			
			RDFNode uri = sol.get("uri");
			String searchSubStr = className + "/";
		    int indexFromId = uri.toString().indexOf(searchSubStr);
		    
		    String strId = null;
		    if (indexFromId > 0) {
		        strId = uri.toString().substring(indexFromId + searchSubStr.length());
		        
		        //tempCI.setId(id.asLiteral().getInt());
		        tempCI.setId(Integer.parseInt(strId));
		        tempCI.setType(className);
		    }

			// Remove comments to print resultset
			Iterator<String> varnames = sol.varNames();
			System.out.print("Term Data : ");
			while (varnames.hasNext()) {
				String var = varnames.next();
				if (!sol.get(var).isLiteral()) {
					System.out.print(var+"="+sol.get(var).toString()+"   ");
				}
				else {
					System.out.print(var+"="+sol.get(var).asLiteral().getValue().toString()+"   ");
					ReadController.set(tempCI, var, sol.get(var).asLiteral().getValue().toString());
				}
				
			} 
			System.out.println();
			
			if (strId != null)
				listOfCI.add(tempCI);
		}
	}
	
	private static void GetCiFromResultSet(ArrayList<CI> listOfCI, ResultSet rs, String compProp)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		while (rs.hasNext()) {
			QuerySolution sol = rs.next();
			
			RDFNode uri = sol.get("uri");
			RDFNode comp = sol.get(compProp);
			
			//String searchSubStr = "resource/";
	        //int index = uri.toString().indexOf(searchSubStr);
	        //String subString = uri.toString().substring(index + searchSubStr.length());
			String subString = comp.toString();
	        int index2 =  subString.indexOf("/");		        
	        String className = "";
	        className = subString.substring(0, index2);
			
	        if (className == null || className == "")
	        	continue;
	        
	        Class cls = Class.forName("model."+ className);

			model.CI tempCI = (CI) cls.newInstance();
						
			String searchSubStr2 = className + "/";
		    //int indexFromId = uri.toString().indexOf(searchSubStr);
			int indexFromId = subString.indexOf("/");
		    
		    String strId = null;
		    if (indexFromId > 0) {
		        //strId = hasComponent.toString().substring(indexFromId + searchSubStr2.length());
		    	strId = comp.toString().substring(indexFromId +1);
		        
		        //tempCI.setId(id.asLiteral().getInt());
		        tempCI.setId(Integer.parseInt(strId));
		        tempCI.setType(className);
		    }

			// Remove comments to print resultset
			Iterator<String> varnames = sol.varNames();
			System.out.print("Term Data : ");
			while (varnames.hasNext()) {
				String var = varnames.next();
				if (!sol.get(var).isLiteral()) {
					System.out.print(var+"="+sol.get(var).toString()+"   ");
				}
				else {
					System.out.print(var+"="+sol.get(var).asLiteral().getValue().toString()+"   ");
					ReadController.set(tempCI, var, sol.get(var).asLiteral().getValue().toString());
				}
				
			} 
			System.out.println();
			
			if (strId != null)
				listOfCI.add(tempCI);
		}
	}
	
	
	public static String[] GetPropertiesFromClass(Class cls) throws Exception
	{
		String[] properties = null;
		
		List<String> lstProperties = new ArrayList<String>();
		// model.Server
		
		java.lang.reflect.Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (!java.util.ArrayList.class.isAssignableFrom(field.getType())){
				lstProperties.add("prop:" + field.getName());
			}
		}
		
		properties = lstProperties.toArray(new String[lstProperties.size()]);
		
		return properties;
	}
	
	
	public static ResultSet executeQueryHTTP(String queryString) throws Exception {
		 Query query = QueryFactory.create(queryString) ;
		 HttpQuery.urlLimit = 100000;
		 QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(CmdbController.queryEndPoint, query);
		 //qexec.addParam("apikey", KEY);
		 ResultSet results = qexec.execSelect() ;
		 return results;
	}
	
	public static ResultSet executeQuery(String queryString) throws Exception {
		 Query query = QueryFactory.create(queryString) ;
		 //HttpQuery.urlLimit = 100000;
		 exec = QueryExecutionFactory.sparqlService(CmdbController.queryEndPoint, query);
		 exec.setTimeout(500);
		 ResultSet results = exec.execSelect();
		 return results;
	}
	
	public static ResultSet executeQuery(QueryExecution exec, String queryString) throws Exception {
		 Query query = QueryFactory.create(queryString) ;
		 //HttpQuery.urlLimit = 100000;
		 exec = QueryExecutionFactory.sparqlService(CmdbController.queryEndPoint, query);
		 exec.setTimeout(500);
		 ResultSet results = exec.execSelect();
		 return results;
	}
	
	
	public static String getQueryTermBatch(List<String> resources, String[] properties) {
		String retVal = "";
		
		try {
				
			SelectBuilder selectQuery = new SelectBuilder();
			selectQuery.addPrefix("prop", propertyUri);
			selectQuery.addPrefix("ont", ontologyUri);
			selectQuery.addPrefix("res", rescourceUri);
			selectQuery.addVar( "*" );

			selectQuery.addWhere("?uri", "prop:name", "?name");
			
			if (properties != null && properties.length > 0)
			{
				/* property binding for the query */
				for (String prop : properties) {
					String varName = prop.split(":")[1]; /* lets use the fragment after ':' as the var name */ 
					selectQuery.addWhere("?uri", prop, "?"+varName);
				}
			}
			//else {
			//	selectQuery.addWhere("?uri", "prop:name", "?name");
			//}
			
			/* we limit the query to the resources in the batch. For that we use FILTER and || */
			StringBuilder filterTerm = null;
			for (int i = 0; i < resources.size(); i++) {
				if (filterTerm == null)
					filterTerm = new StringBuilder();
				filterTerm.append("REGEX(str(?uri), '"+  resources.get(i).toString() +"')" );
				if (i+1 < resources.size())
					filterTerm.append(" ||\n");
			}
			if (filterTerm != null)
				selectQuery.addFilter(filterTerm.toString());
			
			retVal = selectQuery.toString();
		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		
		return retVal;
	}
	
	public static String getQueryTerm(String resources, String id, String[] properties) {
		String retVal = "";
		
		try {
				
			SelectBuilder selectQuery = new SelectBuilder();
			selectQuery.addPrefix("prop", propertyUri);
			selectQuery.addPrefix("ont", ontologyUri);
			selectQuery.addPrefix("res", rescourceUri);
			selectQuery.addVar( "*" );

			selectQuery.addWhere("?uri", "prop:name", "?name");
			
			if (properties != null && properties.length > 0)
			{
				/* property binding for the query */
				for (String prop : properties) {
					String varName = prop.split(":")[1]; /* lets use the fragment after ':' as the var name */ 
					selectQuery.addWhere("?uri", prop, "?"+varName);
				}
			}
			//else {
			//	selectQuery.addWhere("?uri", "prop:name", "?name");
			//}
			
			/* we limit the query to the resources in the batch. For that we use FILTER and || */
			StringBuilder filterTerm = null;
			if (resources != null && resources != "") {
				if (filterTerm == null)	filterTerm = new StringBuilder();
				
				if (id != null && id != "")
					filterTerm.append("?uri = <" + ReadController.rescourceUri+ "" + resources + "/" + id + ">");
				else
					filterTerm.append("REGEX(str(?uri), '" + resources + "')");

			}
			if (filterTerm != null)
				selectQuery.addFilter(filterTerm.toString());
			
			retVal = selectQuery.toString();
		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		
		return retVal;
	}
	
	public static String getQueryHasComp(String resources, String id) {
		String retVal = "";
		
		try {
				
			SelectBuilder selectQuery = new SelectBuilder();
			selectQuery.addPrefix("prop", propertyUri);
			selectQuery.addPrefix("ont", ontologyUri);
			selectQuery.addPrefix("res", rescourceUri);
			selectQuery.addVar( "*" );

			//selectQuery.addWhere("?uri", "prop:name", "?name");
			
			selectQuery.addWhere("?uri", "prop:hasComponent", "?hasComponent");
			
			/* we limit the query to the resources in the batch. For that we use FILTER and || */
			StringBuilder filterTerm = null;
			if (resources != null && resources != "") {
				if (filterTerm == null)	filterTerm = new StringBuilder();
				
				if (id != null && id != "")
					filterTerm.append("?uri = <" + ReadController.rescourceUri+ "" + resources + "/" + id + ">");
				else
					filterTerm.append("REGEX(str(?uri), '" + resources + "')");

			}
			if (filterTerm != null)
				selectQuery.addFilter(filterTerm.toString());
			
			retVal = selectQuery.toString();

		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		
		return retVal;
	}
	
	public static String getQueryIsUsing(String resources, String id) {
		String retVal = "";
		
		try {
				
			SelectBuilder selectQuery = new SelectBuilder();
			selectQuery.addPrefix("prop", propertyUri);
			selectQuery.addPrefix("ont", ontologyUri);
			selectQuery.addPrefix("res", rescourceUri);
			selectQuery.addVar( "*" );

			//selectQuery.addWhere("?uri", "prop:name", "?name");
			
			selectQuery.addWhere("?uri", "prop:isUsing", "?isUsing");
			
			/* we limit the query to the resources in the batch. For that we use FILTER and || */
			StringBuilder filterTerm = null;
			if (resources != null && resources != "") {
				if (filterTerm == null)	filterTerm = new StringBuilder();
				
				if (id != null && id != "")
					filterTerm.append("?uri = <" + ReadController.rescourceUri+ "" + resources + "/" + id + ">");
				else
					filterTerm.append("REGEX(str(?uri), '" + resources + "')");

			}
			if (filterTerm != null)
				selectQuery.addFilter(filterTerm.toString());
			
			retVal = selectQuery.toString();

		}
		catch (Exception ex) 
		{
			System.out.println(ex.toString() + ex.getMessage());
		}
		
		return retVal;
	}
	
	
/* ---------------------- */
	// https://stackoverflow.com/questions/14374878/using-reflection-to-set-an-object-property
	
	public static boolean set(Object object, String fieldName, String fieldValue) {
	    Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            Object objectFieldValue = toObject(field.getType(), fieldValue);
	            field.set(object, objectFieldValue);
	            return true;
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}
	
	public static boolean set(Object object, String fieldName, Object fieldValue) {
	    Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            field.set(object, fieldValue);
	            return true;
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}
	
	@SuppressWarnings("unchecked")
	public static <V> V get(Object object, String fieldName) {
	    Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	        	java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            return (V) field.get(object);
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return null;
	}
	
	public static Object toObject( Class clazz, String value ) {
		if (Boolean.class == clazz || Boolean.TYPE == clazz) return Boolean.parseBoolean( value );
	    if( Byte.class == clazz ) return Byte.parseByte( value );
	    if( Short.class == clazz ) return Short.parseShort( value );
	    if( Integer.class == clazz || Integer.TYPE == clazz) return Integer.parseInt( value );
	    if( Long.class == clazz ) return Long.parseLong( value );
	    if( Float.class == clazz ) return Float.parseFloat( value );
	    if( Double.class == clazz ) return Double.parseDouble( value );
	    return value;
	}
}
