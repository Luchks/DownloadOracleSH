<%@page import="com.limatisoft.base.model.ProductType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Formulario de ProductType</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container">
		<form action="productTypes?action=save" method="post">
		<input type="hidden" name="id" value="${productType.id}"/>
		Codigo: <input type="text" name="code" value="${productType.code}" /><br>
		Nombre: <input type="text" name="name" value="${productType.name}" /><br>
		
		<input type="submit" value="Guardar" />
		  <br/><br/>
		  	<strong style="color: red">${errorMessage}</strong>	
		</form>
	</div>
</body>
</html>