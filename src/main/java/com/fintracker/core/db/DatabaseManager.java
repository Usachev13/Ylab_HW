package com.fintracker.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5433/fintracker";
    private static final String USER = "finuser";
    private static final String PASSWORD = "finpass";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USER,URL);
    }
}
