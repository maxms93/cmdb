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

	<%
	
		ArrayList<CI> listOfCI = CmdbController.getDataFromFusekiAll();
		CI currentCi = null;
		for(CI c : listOfCI){
			if (c.getId() == Integer.parseInt(request.getParameter("id"))){
				currentCi = c;
				break;
			}
		}	


	%>

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
									
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Serial Nummer</label> 
								<input type="text" class="form-control"
								id="serial" disabled="disabled"
								value='<%=currentCi.getId()%>'>
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Type</label> 
								<input type="text" class="form-control" 
								id="type" disabled="disabled"
								value='<%=currentCi.getType()%>'>
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Bezeichnung</label> 
								<input type="text" class="form-control" 
								id="bezeichnung" disabled="disabled"
								value='<%=currentCi.getBezeichnung()%>'>
								
								</div>
							</div>
						</div>
						
						<%if (currentCi.getClass().equals(RAM.class)){
						
							RAM ram = (RAM) currentCi;
							
						%>
												
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Größe</label> 
								<input type="text" class="form-control" 
								id="groesse" disabled="disabled"
								value='<%=ram.getGroesse()%>'>
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Taktung</label> 
								<input type="text" class="form-control" 
								id="taktung" disabled="disabled"
								value='<%=ram.getTaktung()%>'>
								
								</div>
							</div>
						</div>
						
						<%} else if (currentCi.getClass().equals(Person.class)){
						
							Person person = (Person) currentCi;
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Vorname</label> 
								<input type="text" class="form-control" 
								id="Vorname" disabled="disabled"
								value='<%=person.getVorname()%>'>
								
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Nachname</label> 
								<input type="text" class="form-control" 
								id="nachname" disabled="disabled"
								value='<%=person.getNachname()%>'>
								
								</div>
							</div>
						</div>
						
						<%} else if (currentCi.getClass().equals(ApplicationSoftware.class)){
						
							ApplicationSoftware app = (ApplicationSoftware) currentCi;
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>Lines Of Code</label> 
								<input type="text" class="form-control" 
								id="loc" disabled="disabled"
								value='<%=app.getLinesOfCode()%>'>
								
								</div>
							</div>
						</div>
						
						<%} else if (currentCi.getClass().equals(SystemSoftware.class)){
						
							SystemSoftware sys = (SystemSoftware) currentCi;
						
						%>
						
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
								
								<label>ISos</label> 
								<input type="radio" class="form-control" 
								id="isos" disabled="disabled"
								value='<%=sys.isOS()%>'>
								
								</div>
							</div>
						</div>
						
						<%} %>
						
						<form action="delete?id=<%=currentCi.getId()%>" method="post">
 							<input type="submit" name="delete" value="Delete" class="btn btn-sm btn-success"/>
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