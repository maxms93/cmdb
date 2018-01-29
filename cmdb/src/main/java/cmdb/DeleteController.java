package cmdb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class DeleteController extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("delete");
		String type = request.getParameter("type");

		String resource = "<http://artmayr.com/resource/" + type + "/" + id
				+ ">";

		UpdateRequest update = UpdateFactory
				.create(CmdbController.propertyPrefix
						+ CmdbController.ontologyPrefix + "DELETE "
						+ "{ ?serial ?p ?o } " + "WHERE "
						+ "{ ?serial ?p ?o . " + "FILTER ( ?serial = "
						+ resource + ") " + "?serial ?p ?o }");
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
				CmdbController.updateEndPoint);
		processor.execute();

		update = UpdateFactory.create(CmdbController.propertyPrefix
				+ CmdbController.ontologyPrefix + "DELETE "
				+ "{ ?s ?p ?serial } " + "WHERE "
				+ "{ ?s prop:hasComponent ?serial . " + "FILTER ( ?serial= \""
				+ type + "/" + id +"\") " + "?s ?p ?serial }");
		processor = UpdateExecutionFactory.createRemote(update,
				CmdbController.updateEndPoint);
		processor.execute();
		
		update = UpdateFactory.create(CmdbController.propertyPrefix
				+ CmdbController.ontologyPrefix + "DELETE "
				+ "{ ?s ?p ?serial } " + "WHERE "
				+ "{ ?s prop:isUsing ?serial . " + "FILTER ( ?serial= \""
				+ type + "/" + id +"\") " + "?s ?p ?serial }");
		processor = UpdateExecutionFactory.createRemote(update,
				CmdbController.updateEndPoint);
		processor.execute();

		response.sendRedirect("index.jsp");

	}

	/*
	 * PREFIX prop: <http://artmayr.com/property/> PREFIX ont:
	 * <http://artmayr.com/ontology/>
	 * 
	 * SELECT DISTINCT ?subject ?object WHERE { ?subject prop:name ?object.
	 * FILTER (regex(str(?subject), "RAM", "i") || regex(str(?subject),
	 * "SERVER", "i")) }
	 */

}
