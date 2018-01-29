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

import model.ApplicationSoftware;
import model.CI;
import model.Harddisk;
import model.PC;
import model.RAM;
import model.Server;
import model.SystemSoftware;

public class AddComponentController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String component = request.getParameter("component");
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String[] split = component.split(",");
		String compId = split[0];
		String compType = split[1];
		CI comp = null;
		
		if (compType.equals("Server")) {
			
			comp = new Server(Integer.parseInt(compId));
			
		}else if (compType.equals("ApplicationSoftware")) {
			
			comp = new ApplicationSoftware(Integer.parseInt(compId));
			
		}else if (compType.equals("Harddisk")) {
			
			comp = new Harddisk(Integer.parseInt(compId));
			
		}else if (compType.equals("PC")) {
			
			comp = new PC(Integer.parseInt(compId));
			
		}else if (compType.equals("SystemSoftware")) {
			
			comp = new SystemSoftware(Integer.parseInt(compId));
			
		}else if (compType.equals("RAM")) {

			comp = new RAM(Integer.parseInt(compId));
			
		}
		
		UpdateRequest update = UpdateFactory.create(comp.appendCItoCI(type, Integer.parseInt(id)));
	    UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, CmdbController.updateEndPoint);
	    processor.execute();

		response.sendRedirect("index.jsp");

	}

}
