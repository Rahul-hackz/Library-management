package com.library.dao;

import com.library.model.Transaction;
import com.library.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void issueBook(int userId, int bookId, LocalDate issueDate, LocalDate dueDate) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, book_id, issue_date, due_date, status) VALUES (?,?,?,?, 'ISSUED')";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, userId);
            p.setInt(2, bookId);
            p.setDate(3, Date.valueOf(issueDate));
            p.setDate(4, Date.valueOf(dueDate));
            p.executeUpdate();
        }
    }

    public void returnBook(int transactionId, LocalDate returnDate, double fine) throws SQLException {
        String sql = "UPDATE transactions SET return_date=?, fine=?, status='RETURNED' WHERE transaction_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setDate(1, Date.valueOf(returnDate));
            p.setDouble(2, fine);
            p.setInt(3, transactionId);
            p.executeUpdate();
        }
    }

    public List<Transaction> findByUser(int userId) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, userId);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setTransactionId(rs.getInt("transaction_id"));
                    t.setUserId(rs.getInt("user_id"));
                    t.setBookId(rs.getInt("book_id"));
                    Date d1 = rs.getDate("issue_date");
                    if (d1 != null) t.setIssueDate(d1.toLocalDate());
                    Date d2 = rs.getDate("due_date");
                    if (d2 != null) t.setDueDate(d2.toLocalDate());
                    Date d3 = rs.getDate("return_date");
                    if (d3 != null) t.setReturnDate(d3.toLocalDate());
                    t.setFine(rs.getDouble("fine"));
                    t.setStatus(rs.getString("status"));
                    list.add(t);
                }
            }
        }
        return list;
    }
}
