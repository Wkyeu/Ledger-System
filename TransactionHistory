/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fopassignment;
import java.io.*;
import java.util.*;
import java.sql.*;

/**
 *
 * @author 2nd user
 * 
 * //2
 */
public class TransactionHistory {
    public void viewHistory(int accountId, Scanner scanner) {
        System.out.println("== History ==");
        System.out.printf("%-12s %-20s %-10s %-10s %-10s%n", "Date", "Description", "Debit", "Credit", "Balance");

        String fetchHistorySql = "SELECT date, description, debit, credit, balance " +
                                 "FROM transaction_history " +
                                 "WHERE account_id = ? AND MONTH(date) = MONTH(NOW()) " +
                                 "AND YEAR(date) = YEAR(NOW()) " +
                                 "ORDER BY date";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(fetchHistorySql)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder csvContent = new StringBuilder("Date,Description,Debit,Credit,Balance\n");
            boolean hasRecords = false;

            while (resultSet.next()) {
                hasRecords = true;
                String date = resultSet.getDate("date").toString();
                String description = resultSet.getString("description");
                double debit = resultSet.getDouble("debit");
                double credit = resultSet.getDouble("credit");
                double balance = resultSet.getDouble("balance");

                System.out.printf("%-12s %-20s %-10.2f %-10.2f %-10.2f%n", date, description, debit, credit, balance);
                csvContent.append(String.format("%s,%s,%.2f,%.2f,%.2f%n", date, description, debit, credit, balance));
            }

            if (!hasRecords) {
                System.out.println("No transactions found for the current month.");
            } else {
                exportToCsv(csvContent.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void exportToCsv(String csvContent) {
        System.out.println("Exporting transactions to CSV...");

        try (FileWriter writer = new FileWriter("transaction_history.csv")) {
            writer.write(csvContent);
            System.out.println("File Exported!");
        } catch (IOException e) {
            System.out.println("An error occurred while exporting the file.");
            e.printStackTrace();
        }
    }
}
