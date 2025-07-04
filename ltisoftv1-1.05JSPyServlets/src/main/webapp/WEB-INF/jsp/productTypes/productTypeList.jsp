<%@page import="com.limatisoft.security.model.User"%>
<%@page import="com.limatisoft.base.model.ProductType"%>
<%@page import="java.util.List"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %> 
<%@taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html> 
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container">
		Usuario en sesion: ${sessionScope.userInSession.name}
		<br/>
		<a href="${pageContext.request.contextPath}/logout">
		Cerrar Sesión
		</a>
		<br/>
		<br/>
		
		<a href="productTypes?action=new">Agregar</a>					
		<table border="1">
			<thead>
				<tr>
					<td>
						Código
					</td>
					<td>
						Nombre
					</td>
					<td>
						Acción
					</td>
				</tr>
			</thead>
			<tbody> 
				<c:forEach var="productType" items="${productTypeList}">
					<tr>
						<td>
							${productType.code}
						</td>
						<td>
							${fn:toUpperCase(productType.name)}
						</td>
						<td>
							<a href="productTypes?action=edit&code=${productType.code}">Editar</a>
							<a href="productTypes?action=delete&code=${productType.code}">Eliminar</a>

						</td>	
					</tr>
				
				</c:forEach>
			</tbody>
		</table>

	</div>
</body>
</html>