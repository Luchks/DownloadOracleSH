package com.limatisoft.security.listener;

import com.limatisoft.security.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class SessionUserWrapper implements HttpSessionBindingListener {
    private User user;

    public SessionUserWrapper(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        ServletContext context = event.getSession().getServletContext();
        synchronized (context) {
            Integer usuariosLogados = (Integer) context.getAttribute("usuariosLogados");
            if (usuariosLogados == null) {
                usuariosLogados = 0;
            }
            context.setAttribute("usuariosLogados", usuariosLogados + 1);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        ServletContext context = event.getSession().getServletContext();
        synchronized (context) {
            Integer usuariosLogados = (Integer) context.getAttribute("usuariosLogados");
            if (usuariosLogados == null || usuariosLogados <= 0) {
                return;
            }
            context.setAttribute("usuariosLogados", usuariosLogados - 1);
        }
    }
    
    public static int getCantidadUsuariosLogeados(ServletContext context) {
        synchronized (context) {
            Integer usuariosLogados = (Integer) context.getAttribute("usuariosLogados");
            return usuariosLogados != null ? usuariosLogados : 0;
        }
    }

    public static int getLimiteUsuariosPermitidos() {
        return 1; // Puedes cambiar este valor si deseas otro límite
    }
}
