package pki.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import pki.Config;
import pki.entities.CA;
import pki.utilities.CertificateReaders;

@WebServlet("/secure/verify")
public class Verify extends javax.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/secure/verify.jsp";
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		if (request.getMethod().equals("POST")) { // Verify
						
			try {
		        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		        for (FileItem item : items)
		        {
		            if (item.isFormField() == false)
		            {
		            	InputStream filecontent = item.getInputStream();
		                X509Certificate cert = CertificateReaders.ReadCertificateFromInputStream(filecontent);
		                
		                boolean isValid = CA.getInstance().validateCertificate(cert);	
		                
		                if( isValid )
		                {
		                	request.setAttribute( Config.ATT_ERRORS, "Certificate is valid");
		                }
		                else
		                {
		                	request.setAttribute( Config.ATT_ERRORS, "Certificate is invalid");
		                }
		                
		            }
		        }
		    } catch (FileUploadException e) {
		        throw new ServletException("Cannot parse multipart request.", e);
		    }
		}
		this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
	}
}
