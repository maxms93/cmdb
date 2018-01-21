package cmdb;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

//import org.apache.jena.arq.querybuilder.*;

import model.*;

//https://jena.apache.org/documentation/extras/querybuilder/index.html
//https://jena.apache.org/documentation/query/parameterized-sparql-strings.html

//https://jena.apache.org/download/maven.html
//https://mvnrepository.com/artifact/org.apache.jena/jena-extras/3.5.0

public class ReadController extends HttpServlet {
	
	//private static SelectBuilder selectBuild = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//String id = request.getParameter("id");
		
		response.sendRedirect("index.jsp");

	}

	protected static ArrayList<CI> getAllCiFromDB() {
		ArrayList<CI> listOfCI = null;
		ResultSet result;
		ParameterizedSparqlString componentQuery = new ParameterizedSparqlString("" +
				CmdbController.propertyPrefix +
                CmdbController.ontologyPrefix +
		        "SELECT\n" +
		        "?id ?type ?bez \n" +
		        "WHERE {\n" +
		        "?x prop:id ?id . \n" +
		        "?x prop:type ?type . \n" +
		        "?x prop:name ?bez . \n" +
		        "}\n"
		);
		
		/*
		QueryExecution exec = QueryExecutionFactory.sparqlService(CmdbController.queryEndPoint, componentQuery.asQuery());
		try {
		    result = exec.execSelect();
		    listOfCI = new ArrayList<CI>();
		    while (result.hasNext()) {
		        QuerySolution nextSolution = result.next();

		        RDFNode id = nextSolution.get("id");
		        RDFNode type = nextSolution.get("type");
		        RDFNode bezeichnung = nextSolution.get("bez");

		        CI tempCI = new CI();
		        tempCI.setId(id.asLiteral().getInt());
		        tempCI.setType(type.toString());
		        tempCI.setBezeichnung(bezeichnung.toString());
		        listOfCI.add(tempCI);
		    }
		} finally {
		    exec.close();
		}
		 */
		
		return listOfCI;
	}
}
