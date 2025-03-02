<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Moje wypożyczenia</title>
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
<jsp:include page="../common/header.jsp"/>
<h2>Moje wypożyczenia</h2>
<h3>Prosimy o terminowe zwroty.</h3>
<table border="1">
  <tr>
    <th>Tytuł</th>
    <th>Autor</th>
    <th>Data wypożyczenia</th>
    <th>Data zwrotu</th>
  </tr>
  <c:forEach items="${loans}" var="loan">
    <tr>
      <td>${loan.book.title}</td>
      <td>${loan.book.author}</td>
      <td>${loan.loanDate}</td>
      <td>${loan.dueDate}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
