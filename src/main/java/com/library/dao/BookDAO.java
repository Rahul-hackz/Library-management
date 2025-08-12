package com.library.dao;

import com.library.model.Book;
import com.library.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAll() throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                Book b = new Book(rs.getInt("book_id"), rs.getString("title"),
                        rs.getString("author"), rs.getString("category"), rs.getInt("quantity"));
                list.add(b);
            }
        }
        return list;
    }

    public void add(Book b) throws SQLException {
        String sql = "INSERT INTO books(title, author, category, quantity) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, b.getTitle());
            p.setString(2, b.getAuthor());
            p.setString(3, b.getCategory());
            p.setInt(4, b.getQuantity());
            p.executeUpdate();
        }
    }

    public Book findById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE book_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return new Book(rs.getInt("book_id"), rs.getString("title"),
                            rs.getString("author"), rs.getString("category"), rs.getInt("quantity"));
                }
            }
        }
        return null;
    }

    public void updateQuantity(int bookId, int newQty) throws SQLException {
        String sql = "UPDATE books SET quantity=? WHERE book_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, newQty);
            p.setInt(2, bookId);
            p.executeUpdate();
        }
    }

    public void delete(int bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, bookId);
            p.executeUpdate();
        }
    }
}
