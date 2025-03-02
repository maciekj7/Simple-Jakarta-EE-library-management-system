<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Zarządzanie Książkami</title>
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
<body font-family="sans-serif">
<jsp:include page="../common/header.jsp"/>

<h2>Zarządzanie Książkami</h2>

<c:if test="${not empty error}">
    <div class="error-message">${error}</div>
</c:if>

<button onclick="toggleAddForm()">Dodaj Nową Książkę</button>

<div id="addBookForm" class="form-section">
    <h3>Dodaj Nową Książkę</h3>
    <form action="${pageContext.request.contextPath}/employee/books" method="post">
        <input type="hidden" name="action" value="add">
        <div>
            <label>Tytuł:</label>
            <input type="text" name="title" required>
        </div>
        <div>
            <label>Autor:</label>
            <input type="text" name="author" required>
        </div>
        <div>
            <label>ISBN:</label>
            <input type="text" name="isbn" required>
        </div>
        <button type="submit">Dodaj</button>
        <button type="button" onclick="toggleAddForm()">Anuluj</button>
    </form>
</div>

<div id="editBookForm" class="form-section">
    <h3>Edytuj Książkę</h3>
    <form action="${pageContext.request.contextPath}/employee/books" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" id="editBookId">
        <div>
            <label>Tytuł:</label>
            <input type="text" name="title" id="editTitle" required>
        </div>
        <div>
            <label>Autor:</label>
            <input type="text" name="author" id="editAuthor" required>
        </div>
        <div>
            <label>ISBN:</label>
            <input type="text" name="isbn" id="editIsbn" required>
        </div>
        <button type="submit">Zapisz</button>
        <button type="button" onclick="toggleEditForm()">Anuluj</button>
    </form>
</div>

<table border="1">
    <tr>
        <th>Tytuł</th>
        <th>Autor</th>
        <th>ISBN</th>
        <th>Status</th>
        <th>Akcje</th>
    </tr>
    <c:forEach items="${books}" var="book">
        <tr>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.isbn}</td>
            <td>${book.available ? 'Dostępna' : 'Niedostępna'}</td>
            <td>
                <button onclick="showEditBookForm('${book.id}', '${book.title}', '${book.author}', '${book.isbn}')">
                    Edytuj
                </button>
                <form style="display: inline;" action="${pageContext.request.contextPath}/employee/books" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${book.id}">
                    <button type="submit">Usuń</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<script>
    function toggleAddForm() {
        const form = document.getElementById('addBookForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }

    function showEditBookForm(id, title, author, isbn) {
        document.getElementById('editBookId').value = id;
        document.getElementById('editTitle').value = title;
        document.getElementById('editAuthor').value = author;
        document.getElementById('editIsbn').value = isbn;
        document.getElementById('editBookForm').style.display = 'block';
    }

    function toggleEditForm() {
        document.getElementById('editBookForm').style.display = 'none';
    }
</script>
</body>
</html>