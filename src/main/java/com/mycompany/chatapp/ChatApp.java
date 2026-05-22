/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.util.Scanner;

/**
 *
 * @author peter
 */
public class ChatApp {
    
    private static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        // Loading file
        MessageClass.loadMessagesFromFile();

        System.out.println("--Registration Part---");
        
        System.out.print("Enter your First Name: ");
        String fName = input.nextLine();
        
        System.out.print("Enteryour Last Name: ");
        String lName = input.nextLine();
        
        // Username Validation using the while loop
        String user;
        while (true) {
            System.out.print("Enter Username: ");
            user = input.nextLine();
            Login temp = new Login(user, "", "", ""); 
            if (temp.checkUserName()) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        // Password Validation
        String pass;
        while (true) {
            System.out.print("Enter Password: ");
            pass = input.nextLine();
            Login temp = new Login("", pass, "", "");
            if (temp.checkPasswordComplexity()) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // --- Phone Validation ---
        String phone;
        while (true) {
            System.out.print("Enter Cell Number: ");
            phone = input.nextLine();
            Login temp = new Login("", "", "", "");
            if (temp.checkCellPhoneNumber(phone)) {
                System.out.println("Cell phone number successfully added.");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }
        }

        // Create user object
        Login auth = new Login(user, pass, fName, lName);

        // --- Login Section ---
        System.out.println("\n--Login---");
        System.out.print("Enter Username: ");
        String loginUser = input.nextLine();
        System.out.print("Enter Password: ");
        String loginPass = input.nextLine();

        boolean loginSuccess = auth.loginUser(loginUser, loginPass);
        System.out.println(auth.returnLoginStatus(loginSuccess));
        
        // Part 2 work starts here
        if (loginSuccess) {
            System.out.println("\nWelcome to QuickChat");
            showMainMenu();
        } else {
            System.out.println("Login failed. Exiting application.");
        }
        
        input.close();
    }
    
    //  While Loop until Quit
    public static void showMainMenu() {
        int choice;
        do {
            System.out.println("\n MAIN MENU ");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Choose option (1-3): ");
            choice = input.nextInt();
            input.nextLine();
            
            if (choice == 1) {
                sendMessages();
            } else if (choice == 2) {
                System.out.println("Coming Soon");
            } else if (choice == 3) {
                System.out.println("Goodbye! Thanks for using QuickChat.");
                // CHANGE THIS LINE:
                MessageClass.storeMessage(); 
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        } while (choice != 3);
    }
    
    // send message using the for loop
    public static void sendMessages() {
        System.out.print("\n How many messages do you want to send? ");
        int numMessages = input.nextInt();
        input.nextLine();
        
        // i used the incrementing runs exact number of times user specified
        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n=== MESSAGE " + i + " ===");
            
           
            String recipient;
            while (true) {
                System.out.print("Enter recipient cell number (+27): ");
                recipient = input.nextLine();
                
                MessageClass tempMsg = new MessageClass();
                String result = tempMsg.checkRecipientCell(recipient);
                System.out.println(result);
                if (result.equals("Cell phone number successfully captured.")) {
                    break;
                }
            }
            
            // Get message with validation
            String messageText;
            while (true) {
                System.out.print("Enter your message (max 250 characters): ");
                messageText = input.nextLine();
                
                MessageClass tempMsg = new MessageClass();
                String result = tempMsg.checkMessageLength(messageText);
                System.out.println(result);
                if (result.equals("Message ready to send.")) {
                    break;
                }
            }
            
            // Create the message object
            MessageClass newMessage = new MessageClass(i, recipient, messageText);
            
            // Ask user what to do with the message (Send/Disregard/Store)
            String actionResult = newMessage.sendMessageOptions();
            System.out.println(actionResult);
            
            // Display the message details
            newMessage.displayMessage();
            
            System.out.println("-".repeat(40));
        }
        
       // Show total messages sent
        System.out.println("\nTotal messages sent: " + MessageClass.getTotalMessagesSent());
        
        
        MessageClass.storeMessage();
    }
}
    
