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
    <title>Listado de Tipos de Producto</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="page-container">
        
        <h2>Listado de Tipos de Producto</h2>

        <div class="session-info" style="text-align: right;">
            Usuario en sesión: <strong>${sessionScope.usuarioLogado.user.name}</strong>
            <br/>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">Cerrar Sesión</a>
        </div>

        <div class="add-link">
            <a href="${pageContext.request.contextPath}/product-types?action=new" class="btn">Agregar</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Acción</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="type" items="${typeList}">
                    <tr>
                        <td>${type.code}</td>
                        <td>${fn:toUpperCase(type.name)}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/product-types?action=edit&code=${type.code}" class="btn">Editar</a>
                            <a href="${pageContext.request.contextPath}/product-types?action=delete&code=${type.code}" 
                               class="btn btn-danger"
                               onclick="return confirm('¿Estás seguro de eliminar este tipo de producto?');">
                               Eliminar
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="add-link">
            <a href="${pageContext.request.contextPath}/welcome" class="back-button">
               Regresar al Menú de Productos
            </a>
        </div>

    </div>

</body>
</html>
