package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.dao.TransactionDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {

    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        TransactionDAO tdao = new TransactionDAO();
        BookDAO bdao = new BookDAO();

        try {
            if ("issue".equals(action)) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                LocalDate issueDate = LocalDate.now();
                LocalDate dueDate = issueDate.plusDays(14); // 2-week loan
                tdao.issueBook(userId, bookId, issueDate, dueDate);

                // decrease quantity
                Book b = bdao.findById(bookId);
                if (b != null && b.getQuantity() > 0) {
                    bdao.updateQuantity(bookId, b.getQuantity() - 1);
                }
                response.sendRedirect("admin/adminDashboard.jsp");
                return;
            } else if ("return".equals(action)) {
                int transactionId = Integer.parseInt(request.getParameter("transactionId"));
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                LocalDate returnDate = LocalDate.now();

                // fetch transaction due date to compute fine
                // For simplicity, we will compute fine by reading due_date from DB via TransactionDAO->findBy... maybe add method
                // Here assume fine is 0 or compute external; we'll compute basic example:
                // (In production, implement method to fetch dueDate.)
                // For demo, calculate fine using provided 'dueDate' param if available:
                String dueDateStr = request.getParameter("dueDate"); // yyyy-mm-dd
                double fine = 0;
                if (dueDateStr != null && !dueDateStr.isEmpty()) {
                    LocalDate dueDate = LocalDate.parse(dueDateStr);
                    long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                    if (daysLate > 0) fine = daysLate * 5.0; // Rs.5 per day
                }

                tdao.returnBook(transactionId, returnDate, fine);

                // increase quantity
                Book b = bdao.findById(bookId);
                if (b != null) {
                    bdao.updateQuantity(bookId, b.getQuantity() + 1);
                }

                response.sendRedirect("member/my_books.jsp");
                return;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        response.sendRedirect("member/my_books.jsp");
    }
}
