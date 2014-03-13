<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
   <jsp:attribute name="title">Certificates</jsp:attribute>
    
    <jsp:body>
    <h2>Certificates</h2>
    <p><a href="/PKI/secure/certificates/edit" class="btn btn-default">New certificate</a></p>
    
	<table class="table table-bordered table-striped">
	      <thead>
	        <tr>
	          <th>#</th>
	          <th>Name</th>
	          <th>Email</th>
	          <th>Actions</th>
	        </tr>
	      </thead>
	      <tbody>
	        <tr>
	          <td>1</td>
	          <td>Mark</td>
	          <td>thibzy@gmail.com</td>
	          <td><a href="" class="btn btn-info">Edit</a> <a href="" class="btn btn-warning">Revoke</a></td>
	        </tr>
	        <tr>
	          <td>2</td>
	          <td>Mark</td>
	          <td>thibzy@gmail.com</td>
	          <td><a href="" class="btn btn-info">Edit</a> <a href="" class="btn btn-warning">Revoke</a></td>
	        </tr>
	      </tbody>
    </table>
    
    </jsp:body>
</t:template>