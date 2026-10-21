package com.coffeeshop.dao;

import com.coffeeshop.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (customer_name, drink_item, price, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getCustomerName());
            pstmt.setString(2, order.getDrinkItem());
            pstmt.setDouble(3, order.getPrice());
            pstmt.setString(4, order.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting order: " + e.getMessage());
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, customer_name, drink_item, price, status FROM orders";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("drink_item"),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }
        return orders;
    }
}
