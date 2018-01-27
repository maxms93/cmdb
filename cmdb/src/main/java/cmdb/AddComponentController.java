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

public class AddComponentController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String component = request.getParameter("component");

		response.sendRedirect("index.jsp");

	}

}
