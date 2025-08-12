package com.library.dao;

import com.library.model.User;
import com.library.util.DBUtil;

import java.sql.*;

public class UserDAO {

    public User findByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT user_id, username, role, email FROM users WHERE username=? AND password=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, username);
            p.setString(2, password);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setUsername(rs.getString("username"));
                    u.setRole(rs.getString("role"));
                    u.setEmail(rs.getString("email"));
                    return u;
                }
            }
        }
        return null;
    }

    // Implement createUser if you want registration
}
