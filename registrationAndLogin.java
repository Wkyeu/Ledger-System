/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fopassignment;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.mycompany.fopassignment.DatabaseConnection;

/**
 *
 * /6
 * @author 2nd user
 */
public class registrationAndLogin {
    
        public void Registration(Scanner s){
            System.out.println("== Please fill in the form ==");
            System.out.println("Name : ");
            String name = s.nextLine();
            System.out.println("Email : ");
            String email = s.nextLine();
            if(email.contains("@")){
                String [] validateEmail = email.split("@");
                if(validateEmail[1].equals("gmail.com")){

                }
                else{
                    System.out.println("Please enter the email in the correct format!");
                    Registration(s);
                }    

            }
            else{
                System.out.println("Please enter the email in the correct format!");
                Registration(s);
            }
            String password ="";
            boolean validatePassword = true;
            while(validatePassword){
                int specialCharacterCount = 0;
                int whiteSpaceCount = 0;
                System.out.println("Password: ");
                password = s.nextLine();
                if(password.length() < 5){
                    System.out.println("Password must have at least 5 characters");
                    continue;
                }    

                for(int i = 0; i < password.length(); i++){
                    if(!Character.isLetterOrDigit(password.charAt(i)))
                        specialCharacterCount++;
                    if(Character.isWhitespace(password.charAt(i)))
                        whiteSpaceCount++;
                }    
                if(specialCharacterCount == 0){
                    System.out.println("Password must contain at least one special character");
                    continue;
                }
                if(whiteSpaceCount > 0){
                    System.out.println("Password cannot contain space");
                    continue;
                }    

                validatePassword = false;


            }

             try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO users_table(name, email, password) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                preparedStatement.executeUpdate();
                System.out.println("Registration successful!");
                System.out.println("\n\n\n\n\n\n\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            int id = 0;
            try(Connection connection = DatabaseConnection.getConnection()){
                String sql = "SELECT * FROM users_table WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);

                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    id = resultSet.getInt("id");
                }
                else {
                System.out.println("Error retrieving user ID after registration.");
                return;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO debit_credit (id, balance, savings, loan) VALUES (?, 0, 0, 0)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);  

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }    

            }
            public void Login(Scanner s){
                boolean successfulLogin = false;
                while(!successfulLogin){
                    System.out.println("== Please enter your email and password ==");
                    System.out.println("Email: ");
                    String email = s.nextLine();
                    System.out.println("Password: ");
                    String password = s.nextLine();
                    int id = 0;
                    int account_id = 0;
                    String name = "";
                
                    try(Connection connection = DatabaseConnection.getConnection()){
                        String sql = "SELECT * FROM users_table WHERE email = ? AND password = ? ";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1,email);
                        preparedStatement.setString(2,password);

                        ResultSet resultSet = preparedStatement.executeQuery();

                        if(resultSet.next()){
                            name = resultSet.getString("name");
                            System.out.println("Login successful!!!");
                            successfulLogin = true;
                            id = resultSet.getInt("id");
                        }
                        else{
                            System.out.println("Invalid email or password, please try again");
                            return;
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    
                    try(Connection connection = DatabaseConnection.getConnection()){
                        double balance = 0.00;
                        double savings = 0.00;
                        double loan = 0.00;
                        String sql = "SELECT * FROM debit_credit WHERE id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1,id);
                        
                        ResultSet resultSet = preparedStatement.executeQuery();
                        
                        if(resultSet.next()){
                            account_id = resultSet.getInt("account_id");
                            balance = resultSet.getDouble("balance");
                            savings = resultSet.getDouble("savings");
                            loan = resultSet.getDouble("loan");
                            System.out.printf("Balance: %.2f %nSavings: %.2f %nLoan: %.2f",balance,savings,loan);
                        }
                        else{
                            System.out.println("No financial data found for this user");
                        }
                        System.out.println();
                        boolean logout = false;
                        while(!logout){
                            System.out.println("\n\n\n\n\n\n\n");
                            System.out.println("== Welcome, " + name + " ==");
                            String mysql = "SELECT * FROM debit_credit WHERE id = ?";
                            PreparedStatement preparedStatement2 = connection.prepareStatement(sql);
                            preparedStatement2.setInt(1,id);

                            ResultSet resultSet2 = preparedStatement.executeQuery();

                            if(resultSet2.next()){
                                balance = resultSet2.getDouble("balance");
                                savings = resultSet2.getDouble("savings");
                                loan = resultSet2.getDouble("loan");
                                System.out.printf("Balance: %.2f %nSavings: %.2f %nLoan: %.2f",balance,savings,loan);
                                System.out.println();
                            }
                            else{
                                System.out.println("No financial data found for this user");
                            }
                            
                            System.out.println("== Transaction ==\n1.Debit\n" +"2.Credit\n" +"3.History\n" +"4.Savings\n" +"5.Credit Loan\n" +"6.Deposit Interest Predictor\n" +"7.Logout");
                            int option = s.nextInt();
                            s.nextLine();
                            switch(option){
                                case 1:
                                    recordDebitAndCredit debit = new recordDebitAndCredit();
                                    debit.debit(s,balance,id);

                                    break;
                                case 2:
                                    recordDebitAndCredit credit = new recordDebitAndCredit();
                                    credit.credit(s,balance,id);
                                    break;
                                case 3:
                                    TransactionHistory history = new TransactionHistory();
                                    history.viewHistory(account_id, s);
                                    break;
                                case 4:
                                    savings Savings = new savings();
                                    Savings.activate(s,id);
                                    break;
                                case 5:
                                    loan Loan = new loan();
                                    System.out.println("1. Apply for Loan");
                                    System.out.println("2. Repay Loan");
                                    int loanOption = s.nextInt();
                                    s.nextLine(); // Consume newline
                                    switch (loanOption) {
                                        case 1:
                                            Loan.applyLoan(s, id);
                                            break;
                                        case 2:
                                            Loan.repayLoan(s, id);
                                            break;
                                        default:
                                            System.out.println("Invalid option.");
                                    }
                                    break;
                                case 6:
                                    depositInterestPredictor dip = new depositInterestPredictor();
                                    dip.depositInterestPredictor(s, id);
                                    break;
                                case 7:
                                    System.out.println("Thank you for using Ledger");
                                    logout = true;
                                    break;
                                default:
                                    System.out.println("Please enter a valid option");

                            }
                        }

                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }

            }
}
