<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><html>
<head>
  <title>Zarządzanie Czytelnikami</title>
  <style>
    head {
      font-family: sans-serif;
    }
    body {
      font-family: sans-serif;
    }
    .form-section {
      display: none;
    }
    .error-message {
      color: red;
      margin: 10px 0;
    }
  </style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>

<h2>Zarządzanie Czytelnikami</h2>

<c:if test="${not empty error}">
  <div class="error-message">${error}</div>
</c:if>

<button onclick="toggleAddForm()">Dodaj Nowego Czytelnika</button>

<div id="addReaderForm" class="form-section">
  <h3>Dodaj Nowego Czytelnika</h3>
  <form action="${pageContext.request.contextPath}/employee/users" method="post">
    <input type="hidden" name="action" value="add">
    <div>
      <label>Numer Karty:</label>
      <input type="text" name="cardNumber" required>
    </div>
    <div>
      <label>Imię i Nazwisko:</label>
      <input type="text" name="fullName" required>
    </div>
    <div>
      <label>Hasło:</label>
      <input type="password" name="password" required>
    </div>
    <button type="submit">Dodaj</button>
    <button type="button" onclick="toggleAddForm()">Anuluj</button>
  </form>
</div>

<div id="editReaderForm" class="form-section">
  <h3>Edytuj Czytelnika</h3>
  <form action="${pageContext.request.contextPath}/employee/users" method="post">
    <input type="hidden" name="action" value="edit">
    <input type="hidden" name="id" id="editId">
    <div>
      <label>Numer Karty:</label>
      <input type="text" name="cardNumber" id="editCardNumber" required>
    </div>
    <div>
      <label>Imię i Nazwisko:</label>
      <input type="text" name="fullName" id="editFullName" required>
    </div>
    <div>
      <label>Nowe Hasło:</label>
      <input type="password" name="password">
      <small>(zostaw puste jeśli nie chcesz zmieniać)</small>
    </div>
    <button type="submit">Zapisz</button>
    <button type="button" onclick="toggleEditForm()">Anuluj</button>
  </form>
</div>

<table border="1">
  <tr>
    <th>Numer Karty</th>
    <th>Imię i Nazwisko</th>
    <th>Akcje</th>
  </tr>
  <c:forEach items="${readers}" var="reader">
    <tr>
      <td>${reader.cardNumber}</td>
      <td>${reader.fullName}</td>
      <td>
        <button onclick="showEditForm('${reader.id}', '${reader.cardNumber}', '${reader.fullName}')">
          Edytuj
        </button>
        <form style="display: inline;" action="${pageContext.request.contextPath}/employee/users" method="post">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="id" value="${reader.id}">
          <button type="submit">Usuń</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

<script>
  function toggleAddForm() {
    const form = document.getElementById('addReaderForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
  }

  function showEditForm(id, cardNumber, fullName) {
    document.getElementById('editId').value = id;
    document.getElementById('editCardNumber').value = cardNumber;
    document.getElementById('editFullName').value = fullName;
    document.getElementById('editReaderForm').style.display = 'block';
  }

  function toggleEditForm() {
    document.getElementById('editReaderForm').style.display = 'none';
  }
</script>
</body>
</html>