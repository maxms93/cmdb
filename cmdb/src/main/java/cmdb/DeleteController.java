package cmdb;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;

@SuppressWarnings("serial")
@WebServlet(name = "deleteController", urlPatterns = { "/delete" })
public class DeleteController extends HttpServlet{

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	 String id = request.getParameter("id");
	 
	 // TODO DELTE SPARQL query für ID 	
	 
	 
	 ParameterizedSparqlString componentQuery = new ParameterizedSparqlString("" +
		        "DELETE WHERE { "
		        + "	     ?id rdf:id ex:example;"
		        + "	    ex:name ?name ."
		        + "	     FILTER(?id = 1) "
		);
		//QueryExecution exec = QueryExecutionFactory.sparqlService(queryEndPoint, componentQuery.asQuery());
		

		
	 String nextJSP = "/index.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(nextJSP);
		dispatcher.forward(request, response);
	 
	}
}
