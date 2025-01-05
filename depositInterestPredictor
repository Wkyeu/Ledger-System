/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fopassignment;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * 
 * //3
 * @author 2nd user
 */
public class depositInterestPredictor {
    
    private static final String[][] BANK_INTEREST_RATES = {
    {"RHB", "2.6"},
    {"Maybank", "2.5"},
    {"Hong Leong", "2.3"},
    {"Alliance", "2.85"},
    {"AmBank", "2.55"},
    {"Standard Chartered", "2.65"}
    };
    
    public void displayBankInterestRates() {
        System.out.println("=== Available Banks and Interest Rates ===");
        for (String[] bank : BANK_INTEREST_RATES) {
            System.out.printf("%s: %.2f%%\n", bank[0], Double.parseDouble(bank[1]));
        }
    }
    
    public double getInterestRate(String selectedBank) {
        for (String[] bank : BANK_INTEREST_RATES) {
            if (bank[0].equalsIgnoreCase(selectedBank)) {
                return Double.parseDouble(bank[1]);
            }
        }
        return -1; 
    }
    public void depositInterestPredictor(Scanner s, int id) {
        double balance = 0.0;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT balance FROM debit_credit WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getDouble("balance");
            } else {
                System.out.println("No balance information found for the user.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        displayBankInterestRates();

        System.out.println("Select a bank from the list: ");
        String selectedBank = s.nextLine();
        double interestRate = getInterestRate(selectedBank);

        if (interestRate == -1) {
            System.out.println("Invalid bank selection. Please try again.");
            return;
        }

        System.out.println("Enter the duration in months: ");
        int duration = s.nextInt();
        s.nextLine(); 

        // Calculate monthly and total interest based on balance
        double monthlyInterest = (balance * interestRate) / 100 / 12;
        double totalInterest = monthlyInterest * duration;

        System.out.printf("Bank: %s\n", selectedBank);
        System.out.printf("Current Balance: %.2f\n", balance);
        System.out.printf("Monthly Interest: %.2f\n", monthlyInterest);
        System.out.printf("Total Interest for %d months: %.2f\n", duration, totalInterest);
    }
}
