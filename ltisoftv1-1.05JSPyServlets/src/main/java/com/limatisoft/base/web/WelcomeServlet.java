package com.limatisoft.base.web;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Validar si el usuario ha iniciado sesión
        Object user = request.getSession().getAttribute("usuarioLogado");

        if (user == null) {
            // Si no hay sesión activa, redirige al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Si hay sesión, muestra el menú principal
        request.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(request, response);
    }
}
