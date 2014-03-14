<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Verify certificate</jsp:attribute>
    
    <jsp:body>
    <h2>Create certificate</h2>
    
	<form method="post" action="/PKI/secure/verify" enctype="multipart/form-data">
        <div class="form-group">
	        <label for="certificate">Certificate</label>
	        <input type="file" name="file" />
	    </div>
         
	    <input type="submit" value="Verify" class="btn btn-default" />
	</form> 
    
    </jsp:body>
</t:template>