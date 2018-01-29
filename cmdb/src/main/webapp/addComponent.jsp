<%@page import="java.util.List"%>
<%@page import="javafx.util.Pair"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
				<li class="nav-item"><a class="nav-link" href="create.jsp">Create
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
						<legend>Add Component</legend>
						<form name="addComponentForm" action="AddComponentAction" method="Post">
							<div class="row">
								<div class="col-lg-2 col-md-2 col-sm-2">
									<label>Component: </label>
								</div>
								<div class="col-lg-3 col-md-3 col-sm-3">
									<div class="form-group">
										<select class="form-control" name="component">
										
										<%

										String type = request.getParameter("type");
										String pcServerComp = "ApplicationSoftware,Harddisk,RAM,SystemSoftware";
										String personComp = "ApplicationSoftware,Harddisk,RAM,SystemSoftware,Server,PC";
										
										
										if (type.equals("Server") || type.equals("PC")){
											for(CI ci : listOfCI){
												if (pcServerComp.indexOf(ci.getType()) >= 0){
													out.println("<option value=\""+ ci.getId()+","+ci.getType()+"\">"+ci.toString()+"</option>");
												}
											}
										}
										
										if (type.equals("Person")){
											for(CI ci : listOfCI){
												if (personComp.indexOf(ci.getType()) >= 0){
													out.println("<option value=\""+ ci.getId()+","+ci.getType()+"\">"+ci.toString()+"</option>");
												}
											}
										}
		
										%>
					
										</select>
									</div>

								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-md-5 col-sm-5">
									<input name="id" hidden="true" value="<%=request.getParameter("id")%>" />
									<input name="type" hidden="true" value="<%=request.getParameter("type")%>" />
									<button name="add" class="btn btn-primary" type="submit">Add</button>
								</div>
							</div>
						</form>
					</fieldset>
				</div>
				
			</div>
			
		</div>
	</div>
	
	<%-- <div class="content">
				<div class="container-fluid">
				<div class="row">
					<div class="col-md-8">
						<div class="card">
							<div class="header">
								<h4 class="title">Details</h4>
							</div>
							<div class="content">
							
						<form name="addComponentForm" action="AddComponentAction" method="Post">
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Component</label> 
								<select name="component">
								
								<% //Get aLL Available Components %>
								
									<option value="Server" selected="selected">Server</option>
									<option value="RAM">RAM</option>
									<option value="Harddisk">Harddisk</option>
									<option value="PC">PC</option>
									<option value="Person">Person</option>
									<option value="SystemSoftware">SystemSoftware</option>
									<option value="ApplicationSoftware">ApplicationSoftware</option>
								
								</select>
								
								</div>
							</div>
						</div>
						
						</div>				
							<button name="add" class="btn btn-sm btn-success" type="submit">Add</button>
						</form>

						</div>
					</div>
				</div>
			</div>
			</div>

		</div>
	</div> --%>

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