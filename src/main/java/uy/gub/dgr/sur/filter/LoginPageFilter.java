package uy.gub.dgr.sur.filter;

/**
 * User: rmartony
 * Date: 18/12/13
 * Time: 02:02 PM
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * To prevent user from going back to Login page if the user already logged in
 *
 * @author rmartony
 */
//@WebFilter("/login.xhtml")
public class LoginPageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getUserPrincipal() != null) { //If user is already authenticated
            String navigateString = "";
            if (request.isUserInRole("Administrator")) {
                navigateString = "app/admin/adminHome.xhtml";
            } else if (request.isUserInRole("Calificacion")) {
                navigateString = "app/cliente/clienteHome.xhtml";
            } else if (request.isUserInRole("Completado")) {
                navigateString = "app/consola/consolaHome.xhtml";
            }
            response.sendRedirect(request.getContextPath() + navigateString);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}