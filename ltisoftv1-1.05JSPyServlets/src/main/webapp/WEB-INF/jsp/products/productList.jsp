<%@page import="com.limatisoft.security.model.User"%>
<%@page import="com.limatisoft.base.model.Product"%>
<%@page import="java.util.List"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %> 
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>   
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista de Productos</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

	<div class="page-container">
		<h2>Lista de Productos</h2>
		<p>Usuario en sesión: <strong>${sessionScope.usuarioLogado.user.name}</strong></p>

		<a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">Cerrar Sesión</a>
		<a href="products?action=new" class="btn">Agregar Producto</a>

		<c:if test="${param.success == 'true'}">
		    <div class="alert alert-info">
		        ¡Producto guardado correctamente!
		    </div>
		</c:if>

		<table>
			<thead>
				<tr>
					<th>Código</th>
					<th>Nombre</th>
					<th>Tipo de Producto</th>
					<th>Precio</th>
					<th>Acción</th>
				</tr>
			</thead>
			<tbody> 
				<c:forEach var="product" items="${productList}">
					<tr>
						<td>${product.code}</td>
						<td>${fn:toUpperCase(product.name)}</td>
						<td>${product.productType.name}</td>
						<td>${product.salesPrice}</td>
						<td>
							<a href="products?action=edit&code=${product.code}" class="btn">Editar</a>
							<a href="products?action=delete&code=${product.code}" class="btn btn-danger" onclick="return confirm('¿Estás seguro de eliminar este producto?');">Eliminar</a>
						</td>	
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="add-link">
			<a href="${pageContext.request.contextPath}/welcome" class="back-button">Regresar al Menú de Productos</a>
		</div>
	</div>

</body>
</html>
