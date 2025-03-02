<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Zarządzanie Rezerwacjami</title>
  <style>
    head {
      font-family: sans-serif;
    }
    body {
      font-family: sans-serif;
    }
    .error-message {
      color: red;
      margin: 10px 0;
    }
  </style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>

<h2>Zarządzanie Rezerwacjami</h2>

<c:if test="${not empty error}">
  <div class="error-message">${error}</div>
</c:if>

<table border="1">
  <tr>
    <th>Czytelnik</th>
    <th>Książka</th>
    <th>Data Rezerwacji</th>
    <th>Data Wygaśnięcia</th>
    <th>Akcje</th>
  </tr>
  <c:forEach items="${reservations}" var="reservation">
    <tr>
      <td>${reservation.user.fullName} (${reservation.user.cardNumber})</td>
      <td>${reservation.book.title}</td>
      <td>${reservation.reservationDate}</td>
      <td>${reservation.expirationDate}</td>
      <td>
        <form style="display: inline;"
              action="${pageContext.request.contextPath}/employee/reservations"
              method="post">
          <input type="hidden" name="action" value="loan">
          <input type="hidden" name="reservationId" value="${reservation.id}">
          <button type="submit">Wypożycz</button>
        </form>
        <form style="display: inline;"
              action="${pageContext.request.contextPath}/employee/reservations"
              method="post">
          <input type="hidden" name="action" value="cancel">
          <input type="hidden" name="reservationId" value="${reservation.id}">
          <button type="submit">Anuluj</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>