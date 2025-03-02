package com.library.servlet;

import com.library.model.User;
import com.library.model.Loan;
import com.library.model.Reservation;
import com.library.service.LoanService;
import com.library.service.ReservationService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/reader/mybooks")
public class ReaderBooksServlet extends HttpServlet {
    @Inject
    private LoanService loanService;

    @Inject
    private ReservationService reservationService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        List<Loan> loans = loanService.getUserActiveLoans(user);
        List<Reservation> reservations = reservationService.getUserReservations(user);

        request.setAttribute("loans", loans);
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/WEB-INF/views/reader/mybooks.jsp")
                .forward(request, response);
    }
}