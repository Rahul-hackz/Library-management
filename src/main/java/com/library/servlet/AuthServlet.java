package com.library.servlet;

import com.library.dao.UserDAO;
import com.library.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {

    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDao = new UserDAO();
        try {
            User u = userDao.findByUsernameAndPassword(username, password);
            if (u != null) {
                HttpSession ses = request.getSession();
                ses.setAttribute("userId", u.getUserId());
                ses.setAttribute("username", u.getUsername());
                ses.setAttribute("role", u.getRole());
                if ("ADMIN".equalsIgnoreCase(u.getRole())) {
                    response.sendRedirect("admin/adminDashboard.jsp");
                } else {
                    response.sendRedirect("member/memberDashboard.jsp");
                }
                return;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        response.sendRedirect("login.jsp?error=1");
    }
}
