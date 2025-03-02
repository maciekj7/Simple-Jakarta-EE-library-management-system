package com.library.filter;

import com.library.model.User;
import com.library.model.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();

        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
            return;
        }

        boolean isEmployeePath = path.contains("/employee/");
        boolean isReaderPath = path.contains("/reader/");

        if (isEmployeePath && user.getRole() != UserRole.EMPLOYEE) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/reader/mybooks");
            return;
        }

        if (isReaderPath && user.getRole() != UserRole.READER) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/employee/books");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.endsWith("index.jsp") ||
                path.endsWith("/login") ||
                path.endsWith(".css") ||
                path.endsWith(".js");
    }
}