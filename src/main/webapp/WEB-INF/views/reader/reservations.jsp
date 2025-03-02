<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Moje Rezerwacje</title>
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

<h2>Moje Rezerwacje</h2>

<h3>Aktywne Rezerwacje</h3>
<table border="1">
    <tr>
        <th>Książka</th>
        <th>Data Rezerwacji</th>
        <th>Data Wygaśnięcia</th>
        <th>Akcje</th>
    </tr>
    <c:forEach items="${reservations}" var="reservation">
        <tr>
            <td>${reservation.book.title}</td>
            <td>${reservation.reservationDate}</td>
            <td>${reservation.expirationDate}</td>
            <td>
                <form action="${pageContext.request.contextPath}/reader/reservations" method="post">
                    <input type="hidden" name="action" value="cancel">
                    <input type="hidden" name="reservationId" value="${reservation.id}">
                    <button type="submit">Anuluj</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<h3>Dostępne Książki</h3>
<table border="1">
    <tr>
        <th>Tytuł</th>
        <th>Autor</th>
        <th>ISBN</th>
        <th>Akcje</th>
    </tr>
    <c:forEach items="${availableBooks}" var="book">
        <tr>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.isbn}</td>
            <td>
                <form action="${pageContext.request.contextPath}/reader/reservations" method="post">
                    <input type="hidden" name="action" value="reserve">
                    <input type="hidden" name="bookId" value="${book.id}">
                    <button type="submit">Zarezerwuj</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>