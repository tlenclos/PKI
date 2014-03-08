package pki.servlets;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pki.Database;
import pki.User;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@WebServlet("/api/register")
public class Register extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/register.jsp";
	private static final String FIELD_FIRSTNAME = "firstname";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	private static final String ATT_ERRORS  = "errors";
	private static final String ATT_SUCCESS  = "success";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException 
	{
		
		if (request.getMethod() == "POST") {
			String firstname = request.getParameter( FIELD_FIRSTNAME );
			String lastname = request.getParameter( FIELD_LASTNAME );
			String email = request.getParameter( FIELD_EMAIL );
			String password = request.getParameter( FIELD_PASSWORD );
	        
	        User newUser = new User(firstname, lastname, email, password);
	        
	        try {
	        	// Validate
	        	newUser.validate();
	        	
	    		Connection dbCon = Database.getConnection();
	    		Statement statement = (Statement) dbCon.createStatement();
	    		String sql = String.format(
    				"INSERT INTO users (email, password, firstname, lastname) VALUES ('%s', MD5('%s'), '%s', '%s');",
    				email,
    				password,
    				firstname,
    				lastname
				);
	    		
	    		if (statement.executeUpdate(sql) != 0) {
	    			request.setAttribute( ATT_SUCCESS, "You are now registered");
	    		}
			} catch (Exception e) {
				request.setAttribute( ATT_ERRORS, e.getMessage() );
			}
		}
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
