package pki.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pki.Config;
import pki.Database;
import pki.User;

@WebServlet("/secure/certificates")
public class CertificatesList extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/secure/certificates.jsp";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		if (request.getMethod() == "GET") {
			this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
		} else if (request.getMethod() == "POST") {
			
		} else {
			this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
		}
	}
}
