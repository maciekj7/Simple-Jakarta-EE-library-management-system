package com.library.servlet;

import com.library.model.Book;
import com.library.model.Reservation;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.ReservationService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/reader/reservations")
public class ReaderReservationsServlet extends HttpServlet {
    @Inject
    private ReservationService reservationService;

    @Inject
    private BookService bookService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        try {
            List<Reservation> reservations = reservationService.getUserReservations(user);
            List<Book> availableBooks = bookService.getAvailableBooks();

            request.setAttribute("reservations", reservations);
            request.setAttribute("availableBooks", availableBooks);

            request.getRequestDispatcher("/WEB-INF/views/reader/reservations.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/reader/reservations.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        User user = (User) request.getSession().getAttribute("user");

        try {
            if ("reserve".equals(action)) {
                Long bookId = Long.parseLong(request.getParameter("bookId"));
                reservationService.createReservation(user.getId(), bookId);
            } else if ("cancel".equals(action)) {
                Long reservationId = Long.parseLong(request.getParameter("reservationId"));
                reservationService.cancelReservation(reservationId);
            }
            response.sendRedirect(request.getContextPath() + "/reader/reservations");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}