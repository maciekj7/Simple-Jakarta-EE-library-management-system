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

import java.io.IOException;
import java.util.List;

@WebServlet("/employee/users")
public class UserServlet extends HttpServlet {
    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> readers = userService.getAllReaders();
        request.setAttribute("readers", readers);
        request.getRequestDispatcher("/WEB-INF/views/employee/users.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    User newUser = new User();
                    newUser.setCardNumber(request.getParameter("cardNumber"));
                    newUser.setName(request.getParameter("fullName"));
                    newUser.setPassword(request.getParameter("password"));
                    newUser.setRole(UserRole.READER);
                    userService.createUser(newUser);
                    break;

                case "edit":
                    Long id = Long.parseLong(request.getParameter("id"));
                    String cardNumber = request.getParameter("cardNumber");
                    String fullName = request.getParameter("fullName");
                    String password = request.getParameter("password");
                    userService.updateReader(id, cardNumber, fullName, password);
                    break;

                case "delete":
                    Long userId = Long.parseLong(request.getParameter("id"));
                    userService.deleteUser(userId);
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/employee/users");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}