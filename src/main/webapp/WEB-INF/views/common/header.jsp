<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="header" style="padding: 5px; margin-bottom: 5px;">
    <h1>Biblioteka</h1>
    <c:if test="${sessionScope.user.role == 'EMPLOYEE'}">
        <a href="${pageContext.request.contextPath}/employee/books" class="button">Zarządzanie Książkami</a>
        <a href="${pageContext.request.contextPath}/employee/users" class="button">Zarządzanie Czytelnikami</a>
        <a href="${pageContext.request.contextPath}/employee/loans" class="button">Wypożyczenia</a>
        <a href="${pageContext.request.contextPath}/employee/reservations" class="button">Rezerwacje</a>
    </c:if>

    <c:if test="${sessionScope.user.role == 'READER'}">
        <a href="${pageContext.request.contextPath}/reader/mybooks" class="button">Moje Wypożyczenia</a>
        <a href="${pageContext.request.contextPath}/reader/reservations" class="button">Moje Rezerwacje</a>
    </c:if>

    <a href="${pageContext.request.contextPath}/logout" class="button" >Wyloguj</a>

    <style>
        .header {
            font-family: sans-serif;
        }
       .button {
           padding: 5px 10px;
           margin: 0 5px;
       }
    </style>
</div>