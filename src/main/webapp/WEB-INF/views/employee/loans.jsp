<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Zarządzanie Wypożyczeniami</title>
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
    .form-section {
      display: none;
    }
  </style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>

<h2>Zarządzanie Wypożyczeniami</h2>

<c:if test="${not empty error}">
  <div class="error-message">${error}</div>
</c:if>

<button onclick="toggleLoanForm()">Nowe Wypożyczenie</button>

<div id="addLoanForm" class="form-section">
  <h3>Nowe Wypożyczenie</h3>
  <form action="${pageContext.request.contextPath}/employee/loans" method="post">
    <input type="hidden" name="action" value="create">
    <div>
      <label>Czytelnik:</label>
      <select name="userId" required>
        <option value="">Wybierz czytelnika</option>
        <c:forEach items="${readers}" var="reader">
          <option value="${reader.id}">${reader.fullName} (${reader.cardNumber})</option>
        </c:forEach>
      </select>
    </div>
    <div>
      <label>Książka:</label>
      <select name="bookId" required>
        <option value="">Wybierz książkę</option>
        <c:forEach items="${availableBooks}" var="book">
          <option value="${book.id}">${book.title} - ${book.author} (ISBN: ${book.isbn})</option>
        </c:forEach>
      </select>
    </div>
    <button type="submit">Wypożycz</button>
    <button type="button" onclick="toggleLoanForm()">Anuluj</button>
  </form>
</div>

<h3>Aktywne Wypożyczenia</h3>
<table border="1">
  <tr>
    <th>Czytelnik</th>
    <th>Książka</th>
    <th>Data Wypożyczenia</th>
    <th>Termin Zwrotu</th>
    <th>Akcje</th>
  </tr>
  <c:forEach items="${activeLoans}" var="loan">
    <tr>
      <td>${loan.user.fullName} (${loan.user.cardNumber})</td>
      <td>${loan.book.title} - ${loan.book.author}</td>
      <td>${loan.loanDate}</td>
      <td>${loan.dueDate}</td>
      <td>
        <form action="${pageContext.request.contextPath}/employee/loans" method="post">
          <input type="hidden" name="action" value="return">
          <input type="hidden" name="loanId" value="${loan.id}">
          <button type="submit">Zwróć</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

<script>
  function toggleLoanForm() {
    const form = document.getElementById('addLoanForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
  }
</script>
</body>
</html>