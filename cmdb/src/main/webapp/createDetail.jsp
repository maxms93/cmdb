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
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<style>
<%@include file="/WEB-INF/style.css"%>
</style>

</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<a class="navbar-brand" href="#">Configuration Management Database</a>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#">CMDB
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">CI
						Management <span class="sr-only">(current)</span>
				</a></li>
			</ul>
		</div>
	</nav>
	<div class="content">
				<div class="container-fluid">
				<div class="row">
					<div class="col-md-8">
						<div class="card">
							<div class="header">
								<h4 class="title">Details</h4>
							</div>
							<div class="content">
							
						<form name="createForm" action="CreateAction" method="Post">
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Type</label> 
								<input type="text" class="form-control" 
								id="type" name="type" disabled="disabled"
								value='<%=request.getParameter("type")%>'>
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Bezeichnung</label> 
								<input type="text" class="form-control" 
								id="bezeichnung" name="bezeichnung" disabled="disabled"
								value="<%=request.getParameter("bezeichnung")%>">
								
								</div>
							</div>
						</div>
						
						<%				

						if (request.getParameter("type") != null && request.getParameter("type").equals("RAM")){
							
						%>
												
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Größe</label> 
								<input type="text" class="form-control" 
								id="groesse" name="groesse">
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Taktung</label> 
								<input type="text" class="form-control" 
								id="taktung" name="taktung">
								
								</div>
							</div>
						</div>
						
						<%} else if (request.getParameter("type") != null && request.getParameter("type").equals("Person")){
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Vorname</label> 
								<input type="text" class="form-control" 
								id="vorname" name="vorname">
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Nachname</label> 
								<input type="text" class="form-control" 
								id="nachname" name="nachname">
								
								</div>
							</div>
						</div>
						
						<%} else if (request.getParameter("type") != null && request.getParameter("type").equals("ApplicationSoftware")){
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Lines Of Code</label> 
								<input type="text" class="form-control" 
								id="linesOfCode" name="linesOfCode">
								
								</div>
							</div>
						</div>
						
						<%} else if (request.getParameter("type") != null && request.getParameter("type").equals("SystemSoftware")){
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>ISos</label> 
								<input type="checkbox" class="form-control" 
								id="isOS" name="isOS">
								
								</div>
							</div>
						</div>
						
						<%} else if (request.getParameter("type") != null && request.getParameter("type").equals("PC")){
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>isThinclient</label> 
								<input type="checkbox" class="form-control" 
								id="isThinclient" name="isThinclient">
								
								</div>
							</div>
						</div>
						
						<%} else if (request.getParameter("type") != null && request.getParameter("type").equals("Server")){
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>isVirtualized</label> 
								<input type="checkbox" class="form-control" 
								id="isVirtualized" name="isVirtualized">
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>isSharedServer</label> 
								<input type="checkbox" class="form-control" 
								id="isSharedServer" name="isSharedServer">
								
								</div>
							</div>
						</div>
						
						<%} else if (request.getParameter("type") != null && request.getParameter("type").equals("Harddisk")){
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Groesse</label> 
								<input type="text" class="form-control" 
								id="groesse" name="groesse">
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>schnittstelle</label> 
								<input type="text" class="form-control" 
								id="schnittstelle" name="schnittstelle">
								
								</div>
							</div>
						</div>
						
						<%} %>
						
							<input name="type" hidden="true" value="<%=request.getParameter("type")%>"/>
							<input name="bezeichnung" hidden="true" value="<%=request.getParameter("bezeichnung")%>"/>
							<button name="create" value="2" type="submit">Create</button>
						</form>

						
														
						</div>
					</div>
				</div>
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