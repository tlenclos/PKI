package pki.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pki.Certificate;
import pki.Config;
import pki.Database;
import pki.User;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

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
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        List<Certificate> certificates = new ArrayList<Certificate>();

	        try {
	        	connection = Database.getConnection();
	            preparedStatement = (PreparedStatement) connection.prepareStatement(
            		"SELECT * FROM certificate WHERE (revoked IS NULL OR revoked != 1) AND user_id = ?"
        		);

	            User loggedUser =  (User) request.getSession().getAttribute( Config.ATT_SESSION_USER );
	            preparedStatement.setInt(1, loggedUser.id);
	            
	            resultSet = preparedStatement.executeQuery();
	            while ( resultSet.next() ) {
	            	certificates.add(Certificate.mapWithDatabase(resultSet));
	            }
	        } catch ( Exception e ) {
				System.out.println(e);
	        }
	        
	        session.setAttribute( "certificates", certificates);
	        
			this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
		}
	}
}
