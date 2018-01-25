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

		UpdateRequest update = UpdateFactory.create(
				CmdbController.propertyPrefix +
                CmdbController.ontologyPrefix +
				"DELETE " + 
                "{ ?serial ?p ?o } " +
                "WHERE " +
				"{ ?serial prop:id ?id . " +
				"FILTER ( ?id = \"" + id + "\") " +
				"?serial ?p ?o }");
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
				CmdbController.updateEndPoint);
		processor.execute();

		response.sendRedirect("index.jsp");

	}
}