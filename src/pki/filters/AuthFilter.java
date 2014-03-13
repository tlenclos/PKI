package pki.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pki.Config;

public class AuthFilter implements Filter {
	public static final String LOGIN_PAGE  = "/login";
	    
    public void init( FilterConfig config ) throws ServletException {
    }

    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException, ServletException 
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        if ( session.getAttribute( Config.ATT_SESSION_USER ) == null ) {
            response.sendRedirect( request.getContextPath() + LOGIN_PAGE );
        } else {
            chain.doFilter( request, response );
        }
    }

    public void destroy() {}
}
