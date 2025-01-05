/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fopassignment;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

/**
 *  /4
 * @author 2nd user
 */
public class loan {
    public void applyLoan(Scanner s, int userId) {
    System.out.println("Enter the principal amount: ");
    double principal = s.nextDouble();
    System.out.println("Enter the annual interest rate (as a percentage): ");
    double interestRate = s.nextDouble() / 100;
    System.out.println("Enter the repayment period (in months): ");
    int repaymentPeriod = s.nextInt();
    s.nextLine(); 

    // Calculate total repayment and monthly installment
    double totalRepayment = principal + (principal * interestRate * repaymentPeriod / 12);
    double monthlyInstallment = totalRepayment / repaymentPeriod;

    LocalDate startDate = LocalDate.now();
    LocalDate dueDate = startDate.plusMonths(repaymentPeriod);

    try (Connection connection = DatabaseConnection.getConnection()) {
        String sql = "INSERT INTO loans (user_id, principal_amount, interest_rate, repayment_period, total_repayment, monthly_installment, outstanding_balance, start_date, due_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setDouble(2, principal);
        preparedStatement.setDouble(3, interestRate);
        preparedStatement.setInt(4, repaymentPeriod);
        preparedStatement.setDouble(5, totalRepayment);
        preparedStatement.setDouble(6, monthlyInstallment);
        preparedStatement.setDouble(7, totalRepayment);
        preparedStatement.setDate(8, Date.valueOf(startDate));
        preparedStatement.setDate(9, Date.valueOf(dueDate));
        preparedStatement.executeUpdate();

        System.out.println("Loan applied successfully!");
        System.out.printf("Total repayment: %.2f, Monthly installment: %.2f, Due date: %s%n", totalRepayment, monthlyInstallment, dueDate);
        
        String updateSql = "UPDATE debit_credit SET loan = (SELECT SUM(outstanding_balance) FROM loans WHERE user_id = ?) WHERE id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);
        updateStatement.setInt(1, userId);
        updateStatement.setInt(2, userId);
        updateStatement.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public void repayLoan(Scanner s, int userId) {
        double outstandingBalance = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM loans WHERE user_id = ? AND paid = FALSE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int loanId = resultSet.getInt("loan_id");
                outstandingBalance = resultSet.getDouble("outstanding_balance");

                System.out.printf("Outstanding loan balance: %.2f%n", outstandingBalance);
                System.out.println("Enter the amount to repay: ");
                double repayment = s.nextDouble();
                s.nextLine(); 

                if (repayment > outstandingBalance) {
                    System.out.println("Repayment exceeds outstanding balance!");
                    return;
                }

                outstandingBalance -= repayment;
                boolean isPaid = outstandingBalance == 0;

                String updateSql = "UPDATE loans SET outstanding_balance = ?, paid = ? WHERE loan_id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setDouble(1, outstandingBalance);
                updateStatement.setBoolean(2, isPaid);
                updateStatement.setInt(3, loanId);
                updateStatement.executeUpdate();

                System.out.println("Repayment successful!");
                if (isPaid) {
                    System.out.println("Loan fully repaid!");
                } else {
                    System.out.printf("Remaining loan balance: %.2f%n", outstandingBalance);
                }
            } else {
                System.out.println("No active loans found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         try(Connection connection = DatabaseConnection.getConnection()){
                String sql ="UPDATE debit_credit SET loan = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, outstandingBalance);
                preparedStatement.setInt(2, userId);

                preparedStatement.executeUpdate();

            }catch(SQLException e){
                e.printStackTrace();
            }
    }
        public boolean hasOverdueLoans(int userId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT COUNT(*) AS overdue FROM loans WHERE user_id = ? AND paid = FALSE AND due_date < CURRENT_DATE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt("overdue") > 0) {
                return true; // User has overdue loans
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
