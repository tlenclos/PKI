package pki.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import pki.Certificate;
import pki.Config;
import pki.Database;
import pki.User;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@WebServlet("/secure/certificates/edit")
public class CertificatesEdit extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/secure/certificates-edit.jsp";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		if (request.getMethod() == "GET" && request.getParameterValues("id") != null) { // Edit
			
		} else if (request.getMethod() == "GET" && request.getParameterValues("id") != null 
			&& request.getParameterValues("delete") != null) { // Delete
			
		} else if (request.getMethod() == "POST" && request.getParameterValues("id") != null) { // Update
			
		} else if (request.getMethod() == "POST") { // Create
			
			Certificate newCertificate = new Certificate();
			
			try {
		        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		        for (FileItem item : items)
		        {
		            if (item.isFormField())
		            {
		                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
		                String fieldname = item.getFieldName();
		                String fieldvalue = item.getString();
		                
		                newCertificate.setDataForFieldAndValue(fieldname, fieldvalue);
		            } else {
		                // Process form file field (input type="file").
		                //String fieldname = item.getFieldName();
		                //String filename = FilenameUtils.getName(item.getName());
		                InputStream filecontent = item.getInputStream();
		                
		                byte[] bytes = IOUtils.toByteArray(filecontent);
		                newCertificate.setPublicKeyWithBytes(bytes);
		            }
		        }
		    } catch (FileUploadException e) {
		        throw new ServletException("Cannot parse multipart request.", e);
		    }

		    // validate and insert into db
	        try {
	        	newCertificate.validate();
	        	
	    		Connection dbCon = Database.getConnection();
	    		PreparedStatement statement = (PreparedStatement) dbCon.prepareStatement(
    				"INSERT INTO certificate (common_name, country, stateprovince, organization, date, user_id) VALUES (?, ?, ?, ?, NOW(), ?);"
				);
	    		User connectedUser = (User) request.getSession().getAttribute(Config.ATT_SESSION_USER);
	    		
	    		statement.setString(1, newCertificate.commonName);
	    		statement.setString(2, newCertificate.country);
	    		statement.setString(3, newCertificate.stateprovince);
	    		statement.setString(4, newCertificate.organization);
	    		statement.setInt(5, connectedUser.id);
	    		
	    		if (statement.executeUpdate() != 0) {
	    			request.setAttribute( Config.ATT_SUCCESS, "Certificate created");
	    		}
			} catch (Exception e) {
				request.setAttribute( Config.ATT_ERRORS, e.getMessage() );
			}
		}
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
