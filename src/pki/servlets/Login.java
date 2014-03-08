package pki.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pki.User;


public class Login extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/login.jsp";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	private static final String ATT_ERRORS  = "errors";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
