<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
   <jsp:attribute name="title">Register</jsp:attribute>
    
    <jsp:body>
	<form role="form"method="post" action="api/register">
		<legend>Register</legend>
		<c:if test="${not empty errors}">
			<div class="alert alert-dismissable alert-warning">${errors}</div>
		</c:if>
		
		<div class="form-group">
	        <label for="name">Name <span class="requis">*</span></label>
	        <input type="text" id="name" name="name" value="${param.name}" size="20" maxlength="20" class="form-control" />
	    </div>
	       
        <div class="form-group">
	        <label for="firstname">Firstname <span class="requis">*</span></label>
	        <input type="text" id="firstname" name="firstname" value="${param.firstname}" size="20" maxlength="20" class="form-control" />
	    </div>
	       
        <div class="form-group">
	        <label for="mail">Email <span class="requis">*</span></label>
	        <input type="email" id="email" name="email" value="${param.email}" size="20" maxlength="60" class="form-control" />
	    </div>
	             
        <div class="form-group">
	        <label for="password">Password <span class="requis">*</span></label>
	        <input type="password" id="password" name="password" value="${param.passwords}" size="20" maxlength="60" class="form-control" />
	    </div>
	                        
	    <input type="submit" value="Valider"  />
	</form> 
    </jsp:body>
</t:template>