package pki.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pki.Config;
import pki.Database;
import pki.Model;
import pki.User;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@WebServlet("/register")
public class Register extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/register.jsp";
	private static final String FIELD_FIRSTNAME = "firstname";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		
		if (request.getMethod().equals("POST")) {
			String firstname = request.getParameter( FIELD_FIRSTNAME );
			String lastname = request.getParameter( FIELD_LASTNAME );
			String email = request.getParameter( FIELD_EMAIL );
			String password = request.getParameter( FIELD_PASSWORD );
	        
	        User newUser = new User(firstname, lastname, email, password);
	        
	        try {
	        	// Validate
	        	newUser.validate(new Model());
	        	
	    		Connection dbCon = Database.getConnection();
	    		PreparedStatement statement = (PreparedStatement) dbCon.prepareStatement(
    				"INSERT INTO users (email, password, firstname, lastname) VALUES (?, MD5(?), ?, ?);"
				);
	    		
	    		statement.setString(1, email);
	    		statement.setString(2, password);
	    		statement.setString(3, firstname);
	    		statement.setString(4, lastname);
	    		
	    		if (statement.executeUpdate() != 0) {
	    			request.setAttribute( Config.ATT_SUCCESS, "You are now registered");
	    		}
			} catch (Exception e) {
				request.setAttribute( Config.ATT_ERRORS, e.getMessage() );
			}
		}
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
