<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Create certificate</jsp:attribute>
    
    <jsp:body>
    <h2>Create certificate</h2>
    
	<form method="post" action="/PKI/secure/certificates/edit" enctype="multipart/form-data">
        <div class="form-group">
	        <label for="commonName">Common name <span class="requis">*</span></label>
	        <input type=text id="commonName" name="commonName" value="${param.commonName}" size="20" maxlength="60" class="form-control" />
	    </div>
	    
        <div class="form-group">
	        <label for="country">Country</label>
	        <input type=text id="country" name="country" value="${param.country}" size="20" maxlength="60" class="form-control" />
	    </div>

        <div class="form-group">
	        <label for="stateprovince">State province</label>
	        <input type=text id="stateprovince" name="stateprovince" value="${param.stateprovince}" size="20" maxlength="60" class="form-control" />
	    </div>
	   
        <div class="form-group">
	        <label for="organization">Organization</label>
	        <input type=text id="organization" name="organization" value="${param.organization}" size="20" maxlength="60" class="form-control" />
	    </div>   
	    
	    <div class="form-group">
	        <label for="publicKey">Public Key</label>
	        <input type="file" name="file" />
	    </div>   
         
	    <input type="submit" value="Save" class="btn btn-default" />
	</form> 
    
    </jsp:body>
</t:template>