package com.library.servlet;

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

@WebServlet("/employee/reservations")
public class EmployeeReservationServlet extends HttpServlet {
    @Inject
    private ReservationService reservationService;

    @Inject
    private LoanService loanService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Reservation> reservations = reservationService.getAllActiveReservations();
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/WEB-INF/views/employee/reservations.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        Long reservationId = Long.parseLong(request.getParameter("reservationId"));

        try {
            if ("loan".equals(action)) {
                loanService.createLoanFromReservation(reservationId);
            } else if ("cancel".equals(action)) {
                reservationService.cancelReservation(reservationId);
            }
            response.sendRedirect(request.getContextPath() + "/employee/reservations");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}