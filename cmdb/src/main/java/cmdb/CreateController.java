package cmdb;

import java.io.IOException;
import java.util.ArrayList;

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
import model.Person;
import model.RAM;
import model.Server;
import model.SystemSoftware;

public class CreateController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String type = request.getParameter("type");
		String bezeichnung = request.getParameter("bezeichnung");
		String create = request.getParameter("create");
		
		if (create.equals("1")){
		
			response.sendRedirect("createDetail.jsp?type="+type+"&bezeichnung="+bezeichnung);
		
		}
		else if (create.equals("2")) {
			
			CI ci = null;
			
			if (type.equals("Server")) {
				
				boolean isVirtualized = request.getParameter("isVirtualized") != null && request.getParameter("isVirtualized").equals("on");
				boolean isSharedServer = request.getParameter("isSharedServer") != null && request.getParameter("isSharedServer").equals("on");
				ci = new Server(bezeichnung, isVirtualized, isSharedServer);
				
			}else if (type.equals("ApplicationSoftware")) {
				
				String linesOfCode = request.getParameter("linesOfCode");
				ci = new ApplicationSoftware(bezeichnung, Integer.parseInt(linesOfCode));
				
			}else if (type.equals("Harddisk")) {
				
				String groesse = request.getParameter("groesse");
				String schnittstelle = request.getParameter("schnittstelle");
				ci = new Harddisk(bezeichnung, groesse, schnittstelle);
				
			}else if (type.equals("PC")) {
				
				boolean isThinclient = request.getParameter("isThinclient") != null && request.getParameter("isThinclient").equals("on");
				ci = new PC(bezeichnung, isThinclient);
				
			}else if (type.equals("Person")) {
				
				String vorname = request.getParameter("vorname");
				String nachname = request.getParameter("nachname");
				ci = new Person(bezeichnung, vorname, nachname);
				
			}else if (type.equals("SystemSoftware")) {
				
				boolean isOS = request.getParameter("isOS") != null && request.getParameter("isOS").equals("on");
				ci = new SystemSoftware(bezeichnung, isOS);
				
			}else if (type.equals("RAM")) {
				String groesse = request.getParameter("groesse");
				String taktung = request.getParameter("taktung");
				ci = new RAM(bezeichnung, groesse, taktung);			
			}
			
			UpdateRequest update = UpdateFactory.create(ci.insertCI());
		    UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, CmdbController.updateEndPoint);
		    processor.execute();
			
			response.sendRedirect("index.jsp");
		}
	}

}
