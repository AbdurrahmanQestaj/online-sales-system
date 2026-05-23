package org.example.onlinesalessystem.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/online_sales_system";
    private static final String USER = "root";
    private static final String PASSWORD = "2238772466";

    private static Connection initConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = initConnection();
            }
        } catch (SQLException e) {
            connection = initConnection();
        }

        return connection;
    }

    public static void beginTransaction() throws SQLException {
        getConnection().setAutoCommit(false);
    }

    public static void commitTransaction() throws SQLException {
        getConnection().commit();
        getConnection().setAutoCommit(true);
    }

    public static void rollbackTransaction() {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Rollback failed: " + e.getMessage());
        }
    }
}