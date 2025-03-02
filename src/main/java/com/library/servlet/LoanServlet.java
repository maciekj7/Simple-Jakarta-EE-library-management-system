package com.library.servlet;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/employee/loans")
public class LoanServlet extends HttpServlet {
    @Inject
    private LoanService loanService;

    @Inject
    private UserService userService;

    @Inject
    private BookService bookService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<User> readers = userService.getAllReaders();
            List<Book> availableBooks = bookService.getAvailableBooks();
            List<Loan> activeLoans = loanService.getAllActiveLoans();

            request.setAttribute("readers", readers);
            request.setAttribute("availableBooks", availableBooks);
            request.setAttribute("activeLoans", activeLoans);

            request.getRequestDispatcher("/WEB-INF/views/employee/loans.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/employee/loans.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                Long userId = Long.parseLong(request.getParameter("userId"));
                Long bookId = Long.parseLong(request.getParameter("bookId"));
                loanService.createLoan(userId, bookId);
            } else if ("return".equals(action)) {
                Long loanId = Long.parseLong(request.getParameter("loanId"));
                loanService.returnBook(loanId);
            }
            response.sendRedirect(request.getContextPath() + "/employee/loans");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}