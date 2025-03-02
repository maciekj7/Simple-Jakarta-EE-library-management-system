package com.library.servlet;

import com.library.model.User;
import com.library.model.UserRole;
import com.library.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cardNumber = request.getParameter("cardNumber");
        String password = request.getParameter("password");

        System.out.println("Proba zalogowania uzytkownika " + cardNumber);

        User user = userService.findByCardNumber(cardNumber);

        if (user != null && password.equals(user.getPassword())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            System.out.println("Sukces, rola: " + user.getRole());

            if (UserRole.EMPLOYEE == user.getRole()) {
                System.out.println("Przekierowanie do czesci pracowniczej");
                response.sendRedirect(request.getContextPath() + "/employee/books");
            } else {
                System.out.println("Przekierowanie do czesci czytelniczej");
                response.sendRedirect(request.getContextPath() + "/reader/mybooks");
            }
        } else {
            System.out.println("Blad logowania");
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=true");
        }
    }
}