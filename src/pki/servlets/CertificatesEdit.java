package pki.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import pki.Certificate;
import pki.Config;
import pki.Database;
import pki.User;

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
	        	newCertificate.validate();
	        	
	    		Connection dbCon = Database.getConnection();
	    		PreparedStatement statement = (PreparedStatement) dbCon.prepareStatement(
    				"INSERT INTO certificate (common_name, country, stateprovince, organization) VALUES (?, ?, ?, ?);"
				);
	    		
	    		statement.setString(1, newCertificate.commonName);
	    		statement.setString(2, newCertificate.country);
	    		statement.setString(3, newCertificate.stateprovince);
	    		statement.setString(4, newCertificate.organization);
	    		
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
