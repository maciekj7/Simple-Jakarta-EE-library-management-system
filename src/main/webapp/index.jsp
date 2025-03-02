<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Biblioteka - Logowanie</title>
  <style>
    head {
      font-family: sans-serif;
    }
    body {
      font-family: sans-serif;
    }
  </style>
</head>
<body>
<h2>Biblioteka</h2>
<h3>Zaloguj się</h3>
<% if (request.getParameter("error") != null) { %>
<p style="color: red;">Nieprawidłowe dane logowania</p>
<% } %>
<form action="${pageContext.request.contextPath}/login" method="post">
  <div>
    <label>Numer karty:</label>
    <input type="text" name="cardNumber" required>
  </div>
  <div>
    <label>Hasło:</label>
    <input type="password" name="password" required>
  </div>
  <button type="submit">Zaloguj</button>
</form>
</body>
</html>