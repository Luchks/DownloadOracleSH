package com.limatisoft.security.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*") // Aplica el filtro a todos los recursos
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización si se necesita
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);

        boolean isLoginPage = uri.endsWith("login.jsp") || uri.endsWith("/auth") || uri.endsWith("/logout") || uri.endsWith("/welcome");

        boolean isPublicResource = uri.contains("/resources/") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/");

        if (isLoggedIn || isLoginPage || isPublicResource) {
            chain.doFilter(request, response); // Acceso permitido
        } else {
        	res.sendRedirect(req.getContextPath() + "/login.jsp?error=auth");// Redirigir si no está autenticado
        }
    }
  
    @Override
    public void destroy() {
        // Limpieza si se necesita
    }
}
