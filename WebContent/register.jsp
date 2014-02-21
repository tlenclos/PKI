<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Register</jsp:attribute>
    
    <jsp:body>
	<form role="form"method="post" action="api">
		<div class="form-group">
	        <label for="name">Name <span class="requis">*</span></label>
	        <input type="text" id="name" name="name" value="" size="20" maxlength="20" />
	    </div>
	       
        <div class="form-group">
	        <label for="firstname">Firstname</label>
	        <input type="text" id="firstname" name="firstname" value="" size="20" maxlength="20" />
	    </div>
	       
        <div class="form-group">
	        <label for="mail">Email</label>
	        <input type="email" id="email" name="email" value="" size="20" maxlength="60" />
	    </div>
	             
        <div class="form-group">
	        <label for="password">Password</label>
	        <input type="password" id="password" name="password" value="" size="20" maxlength="60" />
	    </div>
	                        
	    <input type="submit" value="Valider"  />
	</form> 
    </jsp:body>
</t:template>