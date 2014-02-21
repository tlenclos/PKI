package pki;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Register extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String FIELD_FIRSTNAME = "firstname";
	private static final String FIELD_LASTNAME = "lastname";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getMethod() == "POST") {
			String firstname = request.getParameter( FIELD_FIRSTNAME );
	        String lastname = request.getParameter( FIELD_LASTNAME );
			String email = request.getParameter( FIELD_EMAIL );
	        String password = request.getParameter( FIELD_PASSWORD );
	        
	        User newUser = new User(firstname, lastname, email, password);
	        
	        try {
	        	newUser.validate();
	        	response.getWriter().print("You are now registered");
			} catch (Exception e) {
				response.getWriter().print(e.toString());
			}
		}
	}
}
