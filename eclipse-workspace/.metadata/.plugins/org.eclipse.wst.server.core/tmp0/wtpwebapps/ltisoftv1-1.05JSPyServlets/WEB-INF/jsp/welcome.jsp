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
	<div class="container">
		Bienvenido   ${sessionScope.userInSession.name}
		<br/>
		
		Menu de Opciones
		<br/><br/>
		<a href="${pageContext.request.contextPath}/products?action=list">Productos</a> <br/> <br/>
		<br>
		<a href="${pageContext.request.contextPath}/productTypes?action=list">Tipo de productos</a> <br/> <br/>
		<br>
	</div>
</body>
</html>