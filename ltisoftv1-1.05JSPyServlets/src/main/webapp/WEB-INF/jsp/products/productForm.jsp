<%@page import="com.limatisoft.security.model.User"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Página de Bienvenida</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="page-container">
        <h2>Bienvenido, ${sessionScope.usuarioLogado.user.name}</h2>

        <h3>Menú de Opciones</h3>
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/products?action=list" class="btn">Gestión de Productos</a>
        </div>
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/product-types?action=list" class="btn">Gestión de Tipos de Producto</a>
        </div>
    </div>
</body>
</html>
