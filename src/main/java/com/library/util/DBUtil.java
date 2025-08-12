package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // Expects env vars: DB_URL, DB_USER, DB_PASS
    private static final String URL = System.getenv("DB_URL");   // e.g. jdbc:mysql://host:3306/library_db
    private static final String USER = System.getenv("DB_USER");
    private static final String PASS = System.getenv("DB_PASS");

    public static Connection getConnection() throws SQLException {
        if (URL == null || USER == null) {
            throw new SQLException("Database environment variables not set (DB_URL, DB_USER, DB_PASS).");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
