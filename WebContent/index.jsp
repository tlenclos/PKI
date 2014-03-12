<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Home</jsp:attribute>
    
    <jsp:body>
    <h1>PKI</h1>
    
    <ul>
    	<li><a href="/PKI/register">Register</a></li>
    	<li><a href="/PKI/login">Login</a></li>
    </ul>
    </jsp:body>
</t:template>