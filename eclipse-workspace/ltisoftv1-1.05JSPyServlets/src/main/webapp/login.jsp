<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceso al Sistema</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        form {
            max-width: 400px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            background-color: #f9f9f9;
        }
        input[type="text"], input[type="email"], input[type="password"] {
            width: 95%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            cursor: pointer;
            width: 100%;
        }
        button:hover {
            background-color: #45a049;
        }
        .centered {
		    text-align: center;
		}
		        
    </style>
</head>
<body>
    <h1>Acceso al Sistema</h1>
    <form action="auth" method="post">
        <label for="email">Login:</label>
        <input type="text"   name="login" autocomplete="false"  required>

        <label for="password">Contraseña:</label>
        <input type="password" name="password" autocomplete="false" required>

        <button type="submit">Acceder</button>
    </form>
    <br/>
    <div class="centered">
  	<strong style="color: red">${errorMessage}</strong>
  	</div>
  	<br/>
  	<br>
    <strong>
    Version: ${initParam["appVersion"]}
    ${param.mensaje}
    </strong>
</body>
</html>