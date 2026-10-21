package com.coffeeshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:coffee_shop.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            try (Statement stmt = conn.createStatement()) {
                // Enable foreign keys and create the table automatically if it doesn't exist
                stmt.execute("PRAGMA foreign_keys = ON;");
                String createTableSQL = "CREATE TABLE IF NOT EXISTS orders (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "customer_name TEXT NOT NULL, " +
                        "drink_item TEXT NOT NULL, " +
                        "price REAL NOT NULL, " +
                        "status TEXT NOT NULL);";
                stmt.execute(createTableSQL);
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
        return conn;
    }
}