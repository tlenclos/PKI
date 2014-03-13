<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Edit certificate</jsp:attribute>
    
    <jsp:body>
    <h2>Edit: Title</h2>
    <p><a href="" class="btn btn-default">Save</a> <a href="" class="btn btn-warning">Revoke</a></p>
    
	<form role="form"method="post" action="/PKI/secure/certificates/edit">
        <div class="form-group">
	        <label for="email">Email <span class="requis">*</span></label>
	        <input type="email" id="email" name="email" value="${param.email}" size="20" maxlength="60" class="form-control" />
	    </div>
	                        
	    <input type="submit" value="Save" class="btn btn-default" />
	</form> 
    
    </jsp:body>
</t:template>