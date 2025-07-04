<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.limatisoft.security.listener.SessionUserWrapper" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <title>Acceso al Sistema</title>
</head>

<body>
<%
    int usuariosConectados = SessionUserWrapper.getCantidadUsuariosLogeados(application);
    int limiteUsuarios = SessionUserWrapper.getLimiteUsuariosPermitidos();
    String error = (String) request.getAttribute("error");
    String paramError = request.getParameter("error");
%>

<div class="container">
    <div class="login-box">
        <div class="alert alert-info">
            Usuarios conectados: <strong><%= usuariosConectados %></strong> de <strong><%= limiteUsuarios %></strong> permitidos.
        </div>

        <h2>Acceso al Sistema</h2>

        <% if (error != null) { %>
            <div class="alert alert-danger">
                <%= error %>
            </div>
        <% } else if ("auth".equals(paramError)) { %>
            <div class="alert alert-danger">
                Debes iniciar sesión para acceder a la aplicación.
            </div>
        <% } %>

        <form action="auth" method="post">
            <div class="form-group">
                <label for="login">Login:</label>
                <input type="text" name="login" id="login" autocomplete="off" required>
            </div>

            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" name="password" id="password" autocomplete="off" required>
            </div>

            <button type="submit" class="btn">Acceder</button>
        </form>

        <div class="alert alert-warning">
            <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
        </div>

        <div class="centered" style="margin-top: 20px;">
            <strong>
                Versión: <%= application.getInitParameter("appVersion") %>
                <%= request.getParameter("mensaje") != null ? request.getParameter("mensaje") : "" %>
            </strong>
        </div>
    </div>
</div>

</body>
</html>
