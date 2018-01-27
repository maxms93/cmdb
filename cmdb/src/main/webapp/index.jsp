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
		//ArrayList<CI> listOfCI = ReadController.getAllCiFromDB();
		ArrayList<CI> listOfCI = ReadController.GetAllServerFromDB();
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

	<p>Eintraege aus Fuseki DB</p>
	<div class="padder-top">
		<div class="container-fluid text-center">
			<div class="row content">
				<div class="col-sm-1 sidenav"></div>
				<div class="col-sm-10 text-left">
					<table class="table table-hover table-sm table-dark">
						<thead>
							<tr>
								<th scope="col">Serial Number</th>
								<th scope="col">CI Type</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (CI ci : listOfCI) {
							%>
							<tr>
								<td><%=ci.getId()%></td>
								<td><%=ci.getType()%></td>
								<td><a href="info.jsp?id=<%=ci.getId()%>" class="btn btn-sm btn-success">See Info</a></td>
								
								<% if (ci.getType().equals("Server") || ci.getType().equals("Person") || ci.getType().equals("PC")){
									
									out.println("<td><a href=\"addComponent.jsp?id="+ ci.getId() +"\" class=\"btn btn-sm btn-success\">Add Component</a></td>");
									
								} %>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
					<a href="create.jsp" class="btn btn-sm btn-success">Create</a>
				</div>
				<div class="col-sm-1 sidenav"></div>
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