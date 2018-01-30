<%@page import="javafx.util.Pair"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jena.query.ParameterizedSparqlString"%>
<%@page import="org.apache.jena.query.QueryExecution"%>
<%@page import="org.apache.jena.query.QueryExecutionFactory"%>
<%@page import="org.apache.jena.query.QuerySolution"%>
<%@page import="org.apache.jena.query.ResultSet"%>
<%@page import="org.apache.jena.rdf.model.RDFNode"%>
<%@page import="org.apache.jena.update.UpdateExecutionFactory"%>
<%@page import="org.apache.jena.update.UpdateFactory"%>
<%@page import="org.apache.jena.update.UpdateProcessor"%>
<%@page import="org.apache.jena.update.UpdateRequest"%>
<%@page import="model.*"%>
<%@page import="cmdb.*"%>

<!doctype html>
<html lang="en">
<head>
<title>Configuration Management Database</title>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootswatch/4.0.0-beta.3/cerulean/bootstrap.min.css">
<style>
<%@include file="/WEB-INF/style.css"%>
</style>

</head>
<body>

	<%
		ArrayList<CI> listOfCI = ReadController.getAllCiFromDB();
		CI currentCi = null;
		for (CI c : listOfCI) {
			if (c.getId() == Integer.parseInt(request.getParameter("id"))) {
				currentCi = c;
				break;
			}
		}
	%>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<div class="container">

			<a class="navbar-brand" href="home.jsp">Configuration Management
				Database</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarColor01" aria-controls="navbarColor01"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarColor01">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="index.jsp">Overview
						CI's</a></li>
				<li class="nav-item "><a class="nav-link" href="create.jsp">Create
						CI</a></li>
				<!--  <li class="nav-item">
			        <a class="nav-link" href="#">Pricing</a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link" href="#">About</a>
			      </li> -->
			</ul>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="page-header" id="banner">
			<div class=row>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<fieldset style="margin-top: 10px">
						<legend>CI's from Fuseki DB</legend>
						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Serial Number: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="serial"
										disabled="disabled" value='<%=currentCi.getId()%>'>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Type: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="type"
										disabled="disabled" value='<%=currentCi.getType()%>'>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Description: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="bezeichnung"
										disabled="disabled" value='<%=currentCi.getName()%>'>
								</div>
							</div>

						</div>
						<%
							if (currentCi.getClass().equals(RAM.class)) {

								RAM ram = (RAM) currentCi;
						%>
						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Size: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="groesse"
										disabled="disabled" value='<%=ram.getGroesse()%>'>
								</div>
							</div>

						</div>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Clock Speed: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="taktung"
										disabled="disabled" value='<%=ram.getTaktung()%>'>
								</div>
							</div>
						</div>
						

<%
							} else if (currentCi.getClass().equals(Harddisk.class)) {

								Harddisk disc = (Harddisk) currentCi;
						%>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Interface: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="schnittstelle"
										disabled="disabled" value='<%=disc.getSchnittstelle()%>'>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Size: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="groesse"
										disabled="disabled" value='<%=disc.getGroesse()%>'>
								</div>
							</div>

						</div>
						
						<%
							} else if (currentCi.getClass().equals(Person.class)) {

								Person person = (Person) currentCi;
						%>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Firstname: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="Vorname"
										disabled="disabled" value='<%=person.getVorname()%>'>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Lastname: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="nachname"
										disabled="disabled" value='<%=person.getNachname()%>'>
								</div>
							</div>

						</div>

						<%
							} else if (currentCi.getClass().equals(ApplicationSoftware.class)) {

								ApplicationSoftware app = (ApplicationSoftware) currentCi;
						%>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>Lines of code: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="text" class="form-control" id="loc"
										disabled="disabled" value='<%=app.getLinesOfCode()%>'>
								</div>
							</div>
						</div>

						<%
							} else if (currentCi.getClass().equals(SystemSoftware.class)) {

								SystemSoftware sys = (SystemSoftware) currentCi;
						%>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>isOS: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<input type="checkbox" class="form-control" id="isos"
										disabled="disabled" value='<%=sys.isOS()%>'>
								</div>
							</div>

						</div>
						
						<%
							} else if (currentCi.getClass().equals(PC.class)) {

								PC pc = (PC) currentCi;
						%>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>isThinclient: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
								   <%
								   		if(pc.isThinclient())
								   		{
								   	%>	
								   		<input type="checkbox" class="form-control" checked id="isThinclient"
										disabled="disabled" value='<%=pc.isThinclient()%>'>
								   	
								   	<%	
								   		}else{
								   %>
								  		<input type="checkbox" class="form-control" id="isThinclient"
										disabled="disabled" value='<%=pc.isThinclient()%>'>						   
								   	
								   <%
								   		}
								   %>
									
								</div>
							</div>

						</div>
						
						<%
							} else if (currentCi.getClass().equals(Server.class)) {

								Server server = (Server) currentCi;
						%>

						<div class="row">
							<div class="col-lg-2 col-md-2 col-sm-2">
								<label>isSharedServer: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									 <%
								   		if(server.isSharedServer())
								   		{
								   	%>	
								   		<input type="checkbox" class="form-control" checked id="isThinclient"
										disabled="disabled" value='<%=server.isSharedServer()%>'>
								   	
								   	<%	
								   		}else{
								   %>
								  		<input type="checkbox" class="form-control" id="isThinclient"
										disabled="disabled" value='<%=server.isSharedServer()%>'>						   
								   	
								   <%
								   		}
								   %>
								
								</div>
							</div>
							
						

						</div>
						<div class="row">
								<div class="col-lg-2 col-md-2 col-sm-2">
								<label>isVirtualized: </label>
							</div>
							<div class="col-lg-3 col-md-3 col-sm-3">
								<div class="form-group">
									<%
								   		if(server.isVirtualized())
								   		{
								   	%>	
								   		<input type="checkbox" class="form-control" checked id="isThinclient"
										disabled="disabled" value='<%=server.isVirtualized()%>'>
								   	
								   	<%	
								   		}else{
								   %>
								  		<input type="checkbox" class="form-control" id="isThinclient"
										disabled="disabled" value='<%=server.isVirtualized()%>'>						   
								   	
								   <%
								   		}
								   %>
								</div>
							</div>
						</div>

						<%
							}
						%>
						<%
							ArrayList<CI> listOfComp = ReadController.getHasCompFromCi(currentCi.getType(), String.valueOf(currentCi.getId()));
							if(listOfComp.size() > 0)
							{
						%>
						
						<br />
						<h5>CI is using the following components:</h5>
						<table class="table table-hover">
							<tr>
								<th scope="col">Serial Number</th>
								<th scope="col">CI Type</th>
								<th scope="col">Description</th>
								<th scope="col"></th>
							</tr>
							<%
								
								for(CI c : listOfComp){
								
							%>
							<tr>
								<td>
									<%=c.getId()%>
								</td>
								<td>
									<%=c.getType()%>
								</td>
								<td>
									<%=c.getBezeichnung()%>
								</td>
								<td>
									<a href="info.jsp?id=<%=c.getId()%>" class="btn btn-sm btn-primary">See Info</a>
								</td>
							</tr>
								<%
								}
								%>
							
						</table>
						<%} %>
						<form name="deleteForm" action="DeleteAction" method="Post">
							<input name="type" hidden="true" value="<%=currentCi.getType()%>" />
							<button class="btn btn-primary" name="delete" value="<%=currentCi.getId()%>"
								type="submit">Delete</button>
						</form>



					</fieldset>

				</div>
				
			</div>

		</div>
</div>
		
</body>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<!-- <script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script> -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
</body>
</html>