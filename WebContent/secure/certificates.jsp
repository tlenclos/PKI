<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
   <jsp:attribute name="title">Certificates</jsp:attribute>
    
    <jsp:body>
    <h2>Certificates</h2>
    <p><a href="/PKI/secure/certificates/edit" class="btn btn-default">New certificate</a></p>
    
        <c:choose>
            <c:when test="${ empty certificates }">
                <p class="alert alert-info">No certificates to display.</p>
            </c:when>
            <c:otherwise>
			<table class="table table-bordered table-striped">
			      <thead>
			        <tr>
			          <th>#</th>
			          <th>Name</th>
			          <th>Date</th>
			          <th>Actions</th>
			        </tr>
			      </thead>
			      <tbody>
			      	<c:forEach items="${ certificates }" var="certificate">
			        <tr>
			          <td><c:out value="${ certificate.id }"/></td>
			          <td><c:out value="${ certificate.commonName }"/></td>
			          <td><c:out value="${ certificate.date }"/></td>
			          <td><a href="" class="btn btn-info">Edit</a> <a href="" class="btn btn-warning">Revoke</a></td>
			        </tr>
			        </c:forEach>
			      </tbody>
		    </table>
            </c:otherwise>
        </c:choose>
    
    </jsp:body>
</t:template>