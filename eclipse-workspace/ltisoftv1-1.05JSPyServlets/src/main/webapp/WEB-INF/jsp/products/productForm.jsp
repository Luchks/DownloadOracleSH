<%@page import="com.limatisoft.base.model.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Formulario de Producto</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container">
		<form action="products?action=save" method="post">
		<input type="hidden" name="id" value="${product.id}"/>
		Codigo: <input type="text" name="code" value="${product.code}" /><br>
		Nombre: <input type="text" name="name" value="${product.name}" /><br>
		Precio: <input type="text" name="salesPrice" value="${product.salesPrice}" /><br>
		
		<input type="submit" value="Guardar" />
		  <br/><br/>
		  	<strong style="color: red">${errorMessage}</strong>	
		</form>
	</div>
</body>
</html>