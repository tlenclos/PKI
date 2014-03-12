<%@tag description="Layout" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<header>
	<link rel="stylesheet" href="/PKI/bootstrap.min.css">
	<title><jsp:invoke fragment="title"/></title>
</header>
  <body>
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
	    		<li><a href="/PKI/login?disconnect">Logout</a></li>
			</c:if>
			<c:if test="${empty userSession}">
		        <li><a href="/PKI/register">Register</a></li>
		        <li><a href="/PKI/login">Login</a></li>
			</c:if>
	      </ul>
	      
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>

    <div class="container">
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