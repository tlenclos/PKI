<%@tag description="Layout" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<link rel="stylesheet" href="/PKI/bootstrap.min.css">
	<link rel="stylesheet" href="/PKI/styles.css">
	<title><jsp:invoke fragment="title"/></title>
</head>
  <body class="container">
	<nav class="navbar navbar-default" role="navigation">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <a class="navbar-brand" href="/PKI/index.jsp">PKI</a>
	    </div>
	
	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	      	<c:if test="${not empty userSession}">
	      		<li><a href="/PKI/secure/certificates">Certificates</a></li>
	      		<li><a href="/PKI/secure/verify">Verify</a></li>
			</c:if>
			<c:if test="${empty userSession}">
		        <li><a href="/PKI/register">Register</a></li>
		        <li><a href="/PKI/login">Login</a></li>
			</c:if>
	      </ul>
	      
	      <c:if test="${not empty userSession}">
		      <ul class="nav navbar-nav navbar-right">
		      	<li><a href="">Hello, <strong><c:out value="${ userSession.firstname }"/> <c:out value="${ userSession.lastname }"/></strong></a></li>
		        <li><a href="/PKI/login?disconnect">Logout</a></li>
		      </ul>
	      </c:if>
	      
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>

    <div class="container-fluid">
		<c:if test="${not empty errors}">
			<div class="alert alert-dismissable alert-warning">${errors}</div>
		</c:if>
		
		<c:if test="${not empty success}">
			<div class="alert alert-dismissable alert-success">${success}</div>
		</c:if>
		<jsp:doBody/>
    </div>
  </body>
</html>