package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {

    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        BookDAO dao = new BookDAO();

        try {
            if ("add".equals(action)) {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String category = request.getParameter("category");
                int qty = Integer.parseInt(request.getParameter("quantity"));
                Book b = new Book(0, title, author, category, qty);
                dao.add(b);
                response.sendRedirect("admin/book_list.jsp");
                return;
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("admin/book_list.jsp");
                return;
            }
            // other actions: update etc.
        } catch (Exception e) {
            throw new ServletException(e);
        }
        response.sendRedirect("admin/book_list.jsp");
    }

    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        // For simplicity, just forward to list page (JSP will call DAO)
        response.sendRedirect("admin/book_list.jsp");
    }
}
