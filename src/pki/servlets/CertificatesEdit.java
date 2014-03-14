package pki.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.X509Certificate;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import pki.Certificate;
import pki.Config;
import pki.Database;
import pki.User;
import pki.entities.CA;
import pki.entities.RA;
import pki.utilities.CertificateWriters;

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
		if (request.getMethod().equals("GET") && request.getParameterValues("id") != null 
				&& request.getParameterValues("revoke") != null) { // REVOKE
			
			Connection dbCon = null;
	        PreparedStatement preparedStatement = null;

	        try {
	        	dbCon = Database.getConnection();
	            preparedStatement = (PreparedStatement) dbCon.prepareStatement("UPDATE certificate SET revoked = 1 WHERE id = ? AND user_id = ?");

	            User loggedUser =  (User) request.getSession().getAttribute( Config.ATT_SESSION_USER );
	            preparedStatement.setInt(1, Integer.parseInt(request.getParameter("id")));
	            preparedStatement.setInt(2, loggedUser.id);
	            
	            int statut = preparedStatement.executeUpdate();
	            if ( statut == 0 ) {
	            	request.setAttribute( Config.ATT_ERRORS, "Error while updating certificate");
	            } else {
	            	request.setAttribute( Config.ATT_ERRORS, "Certificate deleted");
	            }
	        } catch (Exception e) {
	        	request.setAttribute( Config.ATT_ERRORS, "Error while updating certificate");
	        }
	        
	        CA.getInstance().generateCRL();
	        
			response.sendRedirect( request.getContextPath() + "/secure/certificates" );
			return;
			
		}
		else if (request.getMethod().equals("GET") && request.getParameterValues("id") != null 
				&& request.getParameterValues("download") != null) { // Download
			
			Connection dbCon = null;
	        PreparedStatement preparedStatement = null;

	        try {
	        	dbCon = Database.getConnection();
	            preparedStatement = (PreparedStatement) dbCon.prepareStatement("SELECT certificate FROM certificate WHERE id = ? AND user_id = ?");

	            User loggedUser =  (User) request.getSession().getAttribute( Config.ATT_SESSION_USER );
	            preparedStatement.setInt(1, Integer.parseInt(request.getParameter("id")));
	            preparedStatement.setInt(2, loggedUser.id);
	            
	            ResultSet rs = preparedStatement.executeQuery();
	            rs.next();
	            Blob blob = rs.getBlob("certificate");
	            byte[] certBytes = blob.getBytes(1, (int) blob.length());
	            
	            String filename = "certificate.cert";
	            response.setContentType("APPLICATION/OCTET-STREAM");
	            response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
	            
	            OutputStream o = response.getOutputStream();
	            o.write(certBytes);
	            
	            o.flush();
	            o.close();
	        } catch (Exception e) {
	        	request.setAttribute( Config.ATT_ERRORS, "Error while downloading certificate");
	        }
	        
			return;
			
		}
		else if (request.getMethod().equals("GET") && request.getParameterValues("id") != null) { // Edit
		} else if (request.getMethod().equals("POST") && request.getParameterValues("id") != null) { // Update
		} else if (request.getMethod().equals("POST")) { // Create
			
			Certificate newCertificate = new Certificate();
			byte[] publicKeyBytes = null;
			
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
		                
		                publicKeyBytes = IOUtils.toByteArray(filecontent);
		                newCertificate.setPublicKeyWithBytes(publicKeyBytes);
		            }
		        }
		    } catch (FileUploadException e) {
		        throw new ServletException("Cannot parse multipart request.", e);
		    }

		    // validate and insert into db
	        try {
	        	//create certificate
	        	X509Certificate cert = RA.getCertificateWithRequest(newCertificate);
	        	String certString = CertificateWriters.getPemString(cert);
	        	byte[] certBytes = certString.getBytes();
	        	
	    		Connection dbCon = Database.getConnection();
	    		PreparedStatement statement = (PreparedStatement) dbCon.prepareStatement(
    				"INSERT INTO certificate (common_name, country, stateprovince, organization, date, user_id, certificate, publickey) VALUES (?, ?, ?, ?, NOW(), ?, ?, ?);"
				);
	    		User connectedUser = (User) request.getSession().getAttribute(Config.ATT_SESSION_USER);
	    		
	    		statement.setString(1, newCertificate.getCommonName());
	    		statement.setString(2, newCertificate.getCountry());
	    		statement.setString(3, newCertificate.getStateprovince());
	    		statement.setString(4, newCertificate.getOrganization());
	    		statement.setInt(5, connectedUser.id);
	    		statement.setBytes(6, certBytes);
	    		statement.setBytes(7, publicKeyBytes);
	    		
	    		if (statement.executeUpdate() != 0)
	    		{
	    			request.setAttribute( Config.ATT_SUCCESS, "Certificate created");
	    			response.sendRedirect( request.getContextPath() + "/secure/certificates" );
	    			return;
	    		}
			} catch (Exception e) {
				request.setAttribute( Config.ATT_ERRORS, e.getMessage() );
			}
		}
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
