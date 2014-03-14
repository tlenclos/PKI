<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
   <jsp:attribute name="title">Register</jsp:attribute>
    
    <jsp:body>
	<form method="post" action="/PKI/register">
		<legend>Register</legend>
		
		<div class="form-group">
	        <label for="lastname">Lastname <span class="requis">*</span></label>
	        <input type="text" id="lastname" name="lastname" value="${param.lastname}" size="20" maxlength="20" class="form-control" />
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
	                        
	    <input type="submit" value="Valider" class="btn btn-default"  />
	</form> 
    </jsp:body>
</t:template>