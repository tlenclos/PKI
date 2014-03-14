package pki.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.File;

import pki.Config;
import pki.Database;
import pki.User;

@WebServlet("/login")
public class Login extends javax.servlet.http.HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/login.jsp";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_PASSWORD = "password";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String method = request.getMethod();
		
		if (method.equals("POST")) {
			User tryLoginUser = Database.loginUser(request.getParameter(FIELD_EMAIL), request.getParameter(FIELD_PASSWORD));

			if (tryLoginUser != null) {
				session.setAttribute( Config.ATT_SESSION_USER, tryLoginUser);
				response.sendRedirect( request.getContextPath() + "/secure/certificates" );
			} else {
				request.setAttribute( Config.ATT_ERRORS, "Bad credentials");
				this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
			}
		} else if (method.equals("GET") && request.getParameterValues("disconnect") != null) {
			session.setAttribute( Config.ATT_SESSION_USER, null);
			this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
		} else {
			this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
		}
	}
}
