<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Register</jsp:attribute>
    
    <jsp:body>
	<form role="form"method="post" action="api/login">
		<legend>Login</legend>
		
        <div class="form-group">
	        <label for="mail">Email <span class="requis">*</span></label>
	        <input type="email" id="email" name="email" value="" size="20" maxlength="60" class="form-control" />
	    </div>
	             
        <div class="form-group">
	        <label for="password">Password <span class="requis">*</span></label>
	        <input type="password" id="password" name="password" value="" size="20" maxlength="60" class="form-control" />
	    </div>
	                        
	    <input type="submit" value="Valider"  />
	</form> 
    </jsp:body>
</t:template>	