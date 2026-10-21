package com.coffeeshop.ui;

import com.coffeeshop.dao.OrderDao;
import com.coffeeshop.model.Order;

import java.util.List;
import java.util.Scanner;

public class OrderMenu {
    private final OrderDao orderDao = new OrderDao();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        int choice;
        do {
            System.out.println("==================================================");
            System.out.println("            COFFEE SHOP ORDERS SYSTEM             ");
            System.out.println("==================================================");
            System.out.println("[1] Add New Order");
            System.out.println("[2] View Active Orders");
            System.out.println("[3] Exit System");
            System.out.println("==================================================");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter choice (1-3): ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline buffer

            switch (choice) {
                case 1:
                    addNewOrderFlow();
                    break;
                case 2:
                    viewOrdersFlow();
                    break;
                case 3:
                    System.out.println("\nExiting system... Thank you!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please select between 1 and 3.\n");
            }
        } while (choice != 3);
    }

    private void addNewOrderFlow() {
        System.out.println("\n--- ADD NEW COFFEE ORDER ---");
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter Drink Item: ");
        String drinkItem = scanner.nextLine();

        System.out.print("Enter Price (e.g., 165.00): ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid price. Enter valid price: ");
            scanner.next();
        }
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Status (PENDING, PREPARING, COMPLETED): ");
        String status = scanner.nextLine().toUpperCase();

        Order newOrder = new Order(customerName, drinkItem, price, status);
        orderDao.addOrder(newOrder);

        System.out.println("\nSUCCESS: Order added to queue!\n");
    }

    private void viewOrdersFlow() {
        List<Order> orders = orderDao.getAllOrders();

        System.out.println("\n==================================================");
        System.out.println("               ACTIVE COFFEE ORDERS               ");
        System.out.println("==================================================");
        System.out.println("+----+----------------------+---------------------+------------+------------+");
        System.out.println("| ID | CUSTOMER NAME        | DRINK ITEM          | PRICE      | STATUS     |");
        System.out.println("+----+----------------------+---------------------+------------+------------+");

        for (Order o : orders) {
            System.out.printf("| %-2d | %-20s | %-19s | %10.2f | %-10s |\n",
                    o.getId(),
                    o.getCustomerName(),
                    o.getDrinkItem(),
                    o.getPrice(),
                    o.getStatus());
        }

        System.out.println("+----+----------------------+---------------------+------------+------------+");
        System.out.println("Total Records Displayed: " + orders.size());
        System.out.println("==================================================\n");
    }
}