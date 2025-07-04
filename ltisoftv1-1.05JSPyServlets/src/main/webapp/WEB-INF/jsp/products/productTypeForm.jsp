<%@page import="com.limatisoft.base.model.ProductType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Formulario Tipo de Producto</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="page-container">
        <h2>Formulario Tipo de Producto</h2>

        <form action="product-types?action=save" method="post">
            <input type="hidden" name="id" value="${productType.id}" />

            <div class="form-group">
                <label for="code">Código:</label>
                <input type="text" id="code" name="code" value="${productType.code}" required />
            </div>

            <div class="form-group">
                <label for="name">Nombre:</label>
                <input type="text" id="name" name="name" value="${productType.name}" required />
            </div>

            <div class="form-group">
                <input type="submit" value="Guardar" class="btn" />
            </div>

            <div class="form-group">
                <a href="${pageContext.request.contextPath}/product-types?action=list" class="back-button">
                    Volver al listado
                </a>
            </div>

            <c:if test="${not empty errorMessage}">
                <div class="error-message">
                    <strong>${errorMessage}</strong>
                </div>
            </c:if>
        </form>
    </div>
</body>
</html>
