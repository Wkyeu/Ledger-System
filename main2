/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.fopassignment;
import java.util.Scanner;
import java.sql.*;

/**
 *
 * //1
 * @author 2nd user
 * 
 */
public class FOPAssignment {

    public static void main(String[] args) {
        
        System.out.println("⠀⠀⠀⠀⠀⢀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                      ".-/|                  \\ /                  |\\-. \n"+
                       "||||                   |                   |||| \n"+
                       "||||                   |       ~~*~~       |||| \n"+
                       "||||    --==*==--      |                   |||| \n"+
                       "||||                   |                   |||| \n"+
                       "||||                   |                   |||| \n"+
                       "||||                   |     --==*==--     |||| \n"+
                       "||||                   |                   |||| \n"+
                       "||||                   |                   |||| \n"+
                       "||||                   |                   |||| \n"+
                       "||||                   |                   |||| \n"+
                       "||||__________________ | __________________|||| \n"+
                      "||/===================\\|/===================\\|| \n"+
                       "`--------------------~___~-------------------'' \n"+
                           "                                 __                       __                               \n" +
                "|  \\                     |  \\                              \n" +
                "| $$       ______    ____| $$  ______    ______    ______  \n" +
                "| $$      /      \\  /      $$ /      \\  /      \\  /      \\ \n" +
                "| $$     |  $$$$$$\\|  $$$$$$$|  $$$$$$\\|  $$$$$$\\|  $$$$$$\\\n" +
                "| $$     | $$    $$| $$  | $$| $$  | $$| $$    $$| $$   \\$$\n" +
                "| $$_____| $$$$$$$$| $$__| $$| $$__| $$| $$$$$$$$| $$      \n" +
                "| $$     \\\\$$     \\ \\$$    $$ \\$$    $$ \\$$     \\| $$      \n" +
                " \\$$$$$$$$ \\$$$$$$$  \\$$$$$$$ _\\$$$$$$$  \\$$$$$$$ \\$$      \n" +
                "                             |  \\__| $$                    \n" +
                "                              \\$$    $$                    \n" +
                "                               \\$$$$$$                     \n\n\n");
        
            
            String url = "jdbc:mysql://localhost:3306/fop";
            String user = "root"; // Change to your MySQL username
            String password = "deadshoT0226"; // Change to your MySQL password

            // Establishing a connection
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connection to MySQL server established successfully!");
                
                while (true){
                    Scanner s = new Scanner(System.in);
                    System.out.println("Login or Register\n 1.Login \n 2.Register");
                    int LorR = s.nextInt();
                    s.nextLine();
                    
                    if(LorR == 1){
                        registrationAndLogin login = new registrationAndLogin();
                        login.Login(s);
                    }
                    else if(LorR == 2){
                        registrationAndLogin register = new registrationAndLogin();
                        register.Registration(s);
                    }
                    else 
                        System.out.println("Invalid");
                }

            } catch (SQLException e) {
                System.out.println("Failed to connect to MySQL server!");
                e.printStackTrace();
            }
        
    }
}
