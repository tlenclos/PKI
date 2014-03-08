package pki.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pki.Config;
import pki.User;

@WebServlet("/api/register")
public class Register extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = Config.subdir+"/register.jsp";
	private static final String FIELD_FIRSTNAME = "firstname";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	private static final String ATT_ERRORS  = "errors";
	
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
	        	newUser.validate();
	        	response.getWriter().print("You are now registered");
	        	
	        	// SQL Register
	        	try {
	        	    Class.forName( "com.mysql.jdbc.Driver" );
	        	} catch ( ClassNotFoundException e ) {
	        		System.out.println(e);
	        	}
			} catch (Exception e) {
				System.out.println("Erreur validation");
				request.setAttribute( ATT_ERRORS, e.getMessage() );
			}
		}
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
