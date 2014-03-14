<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Login</jsp:attribute>
    
    <jsp:body>
	<form method="post" action="/PKI/login">
		<legend>Login</legend>
		
        <div class="form-group">
	        <label for="email">Email <span class="requis">*</span></label>
	        <input type="email" id="email" name="email" value="${param.email}" size="20" maxlength="60" class="form-control" />
	    </div>
	             
        <div class="form-group">
	        <label for="password">Password <span class="requis">*</span></label>
	        <input type="password" id="password" name="password" value="${param.password}" size="20" maxlength="60" class="form-control" />
	    </div>
	                        
	    <input type="submit" value="Valider" class="btn btn-default" />
	</form> 
    </jsp:body>
</t:template>	