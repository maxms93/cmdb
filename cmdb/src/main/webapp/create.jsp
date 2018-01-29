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

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
		<div class="container">

			<a class="navbar-brand" href="#">Configuration Management
				Database</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
    		<span class="navbar-toggler-icon"></span>
 			 </button>
			
			<ul class="navbar-nav mr-auto">
			      <li class="nav-item">
			        <a class="nav-link" href="index.jsp">Overview CI's</a>
			      </li>
			      <li class="nav-item active">
			        <a class="nav-link" href="create.jsp">Create CI</a>
			      </li>
			     <!--  <li class="nav-item">
			        <a class="nav-link" href="#">Pricing</a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link" href="#">About</a>
			      </li> -->
			    </ul>
		</div>
	</nav>

	<div class="container">
		<div class="page-header" id="banner">
			<div class=row>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<fieldset style="margin-top: 10px">
						<legend>Create new CI</legend>
						<form name="createForm" action="CreateAction" method="Post">
							<div class="row">
								<div class="col-lg-2 col-md-2 col-sm-2">
									<label>Type: </label>
								</div>
								<div class="col-lg-3 col-md-3 col-sm-3">
									<div class="form-group">
										<select class="form-control" name="type">
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
							<div class="row">
								<div class="col-lg-2 col-md-2 col-sm-2">
									<label>Description: </label>
								</div>
								<div class="col-lg-3 col-md-3 col-sm-3">
									 <input type="text"	class="form-control" id="bezeichnung" name="bezeichnung">
								</div>
							</div>
							<div class="row">
								<div class="col-lg-5 col-md-5 col-sm-5">
									<button class="btn btn-primary" name="create" value="1" type="submit">Create</button>
								</div>
							</div>
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