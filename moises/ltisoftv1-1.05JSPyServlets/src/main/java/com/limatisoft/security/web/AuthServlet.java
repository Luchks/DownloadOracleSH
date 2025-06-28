package com.limatisoft.security.web;

import java.io.IOException;

import com.limatisoft.security.model.User;
import com.limatisoft.security.service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value= "/auth")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthService authService = new AuthService();
    public AuthServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		User user = authService.authenticate(login, password);
		
		 Integer usuariosLogados = (Integer)getServletContext().getAttribute("usuariosLogados");
	     if (usuariosLogados != null && usuariosLogados >= 2) {
	    		response.sendRedirect(request.getContextPath() + "/login.sjp"); 	
	    		return;
	     }
			
		request.setAttribute("user", user);
		request.getSession().setAttribute("userInSession", user);
		request.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(request, response);
	}

}
