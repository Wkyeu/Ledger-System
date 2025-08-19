/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fopassignment;
import java.util.*;
import java.sql.*;
import java.time.LocalDate;

/**
 *
 * /7
 * @author 2nd user
 */
public class savings {
    
    public boolean activate(Scanner s, int id){
        System.out.println("Are you sure you want to activate it? (Y/N) :");
        String yesOrNo = s.nextLine();
        boolean YN = false;
        if (yesOrNo.equals("Y")) {
            System.out.print("Please enter the percentage you wish to deduct from the next debit: ");
            int percentage = s.nextInt();
            s.nextLine(); 
            
            if (percentage < 0 || percentage > 100) {
                System.out.println("Invalid percentage! Enter a value between 0 and 100.");
                return false;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "UPDATE debit_credit SET is_savings_active = ?, savings_percentage = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, percentage);
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
                System.out.println("Savings settings added successfully!!!");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else if (yesOrNo.equals("N")) {
            System.out.println("Savings activation canceled.");
        } else {
            System.out.println("Invalid input. Please enter Y or N.");
        }
        return YN;
            
    }
    
    public void checkAndTransferSavings(int id) {

        LocalDate currentDate = LocalDate.now();
        
        // Check if it's the last day of the month
        if (currentDate.getDayOfMonth() == currentDate.lengthOfMonth()) {
            transferSavingsToBalance(id);
        }
    }

    public void transferSavingsToBalance(int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {

            String sql = "SELECT savings, balance FROM debit_credit WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double savingsAmount = resultSet.getDouble("savings");
                double currentBalance = resultSet.getDouble("balance");
                double newBalance = currentBalance + savingsAmount;

                String updateSql = "UPDATE debit_credit SET balance = ?, savings = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setDouble(1, newBalance);
                updateStatement.setDouble(2, 0.00);  // Reset savings to 0
                updateStatement.setInt(3, id);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
