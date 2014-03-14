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
			      	
			      	  <c:choose>
						  <c:when test="${ certificate.revoked == 1}">
						  	<tr class="revoked">
						  </c:when>
						  <c:otherwise>
						  	<tr>
					      </c:otherwise>
		              </c:choose>
		              
			          <td>
			          
			      	  <c:choose>
						  <c:when test="${ certificate.revoked == 1}">
						  	<span class="label label-warning">[<c:out value="${ certificate.id }"/>] Revoked</span>
						  </c:when>
						  <c:otherwise>
						  	<span class="label label-success">[<c:out value="${ certificate.id }"/>] Active</span>
					      </c:otherwise>
		              </c:choose>
			          
			          </td>
			          <td><c:out value="${ certificate.commonName }"/></td>
			          <td><c:out value="${ certificate.date }"/></td>
			          <td>
			          <a href="/PKI/secure/certificates/edit?id=<c:out value="${ certificate.id }"/>&download=true" class="btn btn-info">Download</a> 
					  <c:if test="${ certificate.revoked != 1}">
			          	<a href="/PKI/secure/certificates/edit?id=<c:out value="${ certificate.id }"/>&revoke=true" class="btn btn-warning">Revoke</a></td>
			          </c:if>
			        </tr>
			        </c:forEach>
			      </tbody>
		    </table>
            </c:otherwise>
        </c:choose>
    
    </jsp:body>
</t:template>