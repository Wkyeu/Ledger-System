/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fopassignment;
import java.util.Scanner;
import java.sql.*;
/**
 *
 * /5
 * @author 2nd user
 */
public class recordDebitAndCredit {
    public void debit(Scanner s, double balance, int id){
        savings Savings = new savings();
        Savings.transferSavingsToBalance(id);
        loan Loan = new loan();
        if (Loan.hasOverdueLoans(id)) {
            System.out.println("Debit or credit operations are not allowed due to loan overdue");
            return;
        }

        System.out.println("== Debit ==\nEnter amount: ");
        double amount = s.nextDouble();
        s.nextLine();
        System.out.println("Enter description: ");
        String description = s.nextLine();
        int account_id = 0;
        double savings = 0.00;
        int savingsPercentage = 0;
        boolean isSavingsActive = false;
        
        try(Connection connection = DatabaseConnection.getConnection()){

            String sql = "SELECT is_savings_active, savings_percentage, savings FROM debit_credit WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isSavingsActive = resultSet.getBoolean("is_savings_active");
                savingsPercentage = resultSet.getInt("savings_percentage");
                savings = resultSet.getDouble("savings");
            }

            double savingsAmount = 0.00;
            if (isSavingsActive) {
                savingsAmount = (amount * savingsPercentage) / 100;
                savings += savingsAmount;
                amount -= savingsAmount;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        balance += amount;
        
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql ="UPDATE debit_credit SET balance = ?, savings = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, balance);
            preparedStatement.setDouble(2, savings);
            preparedStatement.setInt(3, id);
            
            preparedStatement.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql ="SELECT * FROM debit_credit WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                account_id = resultSet.getInt("account_id");
            }
            else{
                System.out.println("No account found");
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }   
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = "INSERT INTO transaction_history(account_id, description, debit, balance, date) VALUES (?, ?, ?, ?, NOW())";//ensure no timezone issue instead of using CURDATE()
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            preparedStatement.setString(2,description);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setDouble(4, balance);
                       
            preparedStatement.executeUpdate();
            System.out.println("Successfully Recorded!!!");
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Debit Successfully Recorded!!!");
    }
    public void credit(Scanner s, double balance, int id){
        loan Loan = new loan();
        if (Loan.hasOverdueLoans(id)) {
            System.out.println("Debit or credit operations are not allowed due to loan overdue");
            return;
        }
        System.out.println("== Credit ==\nEnter amount: ");
        double amount = s.nextDouble();
        balance -= amount;
        s.nextLine();
        System.out.println("Enter description: ");
        String description = s.nextLine();
        int account_id = 0;
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql ="UPDATE debit_credit SET balance = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, id);
            
            preparedStatement.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql ="SELECT * FROM debit_credit WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                account_id = resultSet.getInt("account_id");
            }
            else{
                System.out.println("No account found");
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }   
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = "INSERT INTO transaction_history(account_id, description, debit, balance, date) VALUES (?, ?, ?, ?, NOW())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            preparedStatement.setString(2,description);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setDouble(4, balance);
            
            preparedStatement.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Credit Successfully Recorded!!!");
        
    }
}
