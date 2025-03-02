package com.library.servlet;

import com.library.model.Book;
import com.library.service.BookService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/employee/books")
public class BookServlet extends HttpServlet {
    @Inject
    private BookService bookService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookService.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/WEB-INF/views/employee/books.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    Book book = new Book();
                    book.setTitle(request.getParameter("title"));
                    book.setAuthor(request.getParameter("author"));
                    book.setIsbn(request.getParameter("isbn"));
                    book.setAvailable(true);
                    bookService.saveBook(book);
                    break;

                case "edit":
                    Long id = Long.parseLong(request.getParameter("id"));
                    Book existingBook = bookService.getBook(id);
                    existingBook.setTitle(request.getParameter("title"));
                    existingBook.setAuthor(request.getParameter("author"));
                    existingBook.setIsbn(request.getParameter("isbn"));
                    bookService.saveBook(existingBook);
                    break;

                case "delete":
                    try {
                        Long bookId = Long.parseLong(request.getParameter("id"));
                        bookService.deleteBook(bookId);
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                        doGet(request, response);
                        return;
                    }
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/employee/books");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}