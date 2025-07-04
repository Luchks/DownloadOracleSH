package com.limatisoft.security.web;

import java.io.IOException;

import com.limatisoft.security.model.User;
import com.limatisoft.security.service.AuthService;
import com.limatisoft.security.listener.SessionUserWrapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(value = "/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AuthService authService = new AuthService();

    private static final int LIMITE_USUARIOS_LOGUEADOS = 1; // Puedes cambiarlo luego a 2 o más

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogado") != null) {
            // Ya está logueado, redirigir directamente
            response.sendRedirect(request.getContextPath() + "/welcome");
            return;
        }

        // Obtener credenciales
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = authService.authenticate(login, password);

        if (user == null) {
            request.setAttribute("error", "Credenciales incorrectas.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Validar límite de usuarios conectados
        ServletContext context = getServletContext();
        synchronized (context) {
            Integer usuariosLogados = (Integer) context.getAttribute("usuariosLogados");
            if (usuariosLogados == null) usuariosLogados = 0;

            if (usuariosLogados >= LIMITE_USUARIOS_LOGUEADOS) {
                request.setAttribute("error", "El sistema ya tiene el máximo de " + LIMITE_USUARIOS_LOGUEADOS + " usuarios conectados. Intente más tarde.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
        }

        // Crear sesión nueva si no existe
        session = request.getSession(true);

        // Registrar el wrapper para incrementar contador por listener
        SessionUserWrapper userWrapper = new SessionUserWrapper(user);
        session.setAttribute("usuarioLogado", userWrapper);

        response.sendRedirect(request.getContextPath() + "/welcome");
    }
}
