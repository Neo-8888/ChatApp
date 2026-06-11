
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
        
        // Loading saved messages
        MessageClass.loadMessagesFromFile();

        System.out.println("-- Registration --");
        
        System.out.print("Enter your First Name: ");
        String fName = input.nextLine();
        
        System.out.print("Enter your Last Name: ");
        String lName = input.nextLine();
        
        // Username Validation
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

        // Phone Validation
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

        // Login Section
        System.out.println("\n-- Login --");
        System.out.print("Enter Username: ");
        String loginUser = input.nextLine();
        System.out.print("Enter Password: ");
        String loginPass = input.nextLine();

        boolean loginSuccess = auth.loginUser(loginUser, loginPass);
        System.out.println(auth.returnLoginStatus(loginSuccess));
        
        // Part 2 & 3 start here
        if (loginSuccess) {
            System.out.println("\nWelcome to QuickChat");
            showMainMenu();
        } else {
            System.out.println("Login failed. Exiting application.");
        }
        
        input.close();
    }
    
    // Main Menu with 4 options
    public static void showMainMenu() {
        int choice;
        do {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("           MAIN MENU");
            System.out.println("=".repeat(40));
            System.out.println("1. Send Messages");
            System.out.println("2. Show Recently Sent Messages");
            System.out.println("3. Stored Messages Dashboard");
            System.out.println("4. Quit");
            System.out.print("Choose option (1-4): ");
            choice = input.nextInt();
            input.nextLine();
            
            if (choice == 1) {
                sendMessages();
            } else if (choice == 2) {
                showRecentlySentMessages();
            } else if (choice == 3) {
                showStoredMessagesMenu();
            } else if (choice == 4) {
                System.out.println("Goodbye! Thanks for using QuickChat.");
                MessageClass.storeMessage();
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        } while (choice != 4);
    }
    
    // Send Messages using For Loop
    public static void sendMessages() {
        System.out.print("\nHow many messages do you want to send? ");
        int numMessages = input.nextInt();
        input.nextLine();
        
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
            
            MessageClass newMessage = new MessageClass(i, recipient, messageText);
            String actionResult = newMessage.sendMessageOptions();
            System.out.println(actionResult);
            newMessage.displayMessage();
            System.out.println("-".repeat(40));
        }
        
        System.out.println("\nTotal messages sent: " + MessageClass.getTotalMessagesSent());
        MessageClass.storeMessage();
    }
    
    // Show Recently Sent Messages (Option 2)
    public static void showRecentlySentMessages() {
        System.out.println("\n--- RECENTLY SENT MESSAGES ---");
        MessageClass.displaySentMessages();
    }
    
    // Stored Messages Dashboard (Option 3 - All Part 3 requirements)
    public static void showStoredMessagesMenu() {
        int storedChoice;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("        STORED MESSAGES DASHBOARD");
            System.out.println("=".repeat(50));
            System.out.println("1. Display sender and recipient of all stored messages");
            System.out.println("2. Display the longest stored message");
            System.out.println("3. Search for a message by Message ID");
            System.out.println("4. Search all messages for a particular recipient");
            System.out.println("5. Delete a message using Message Hash");
            System.out.println("6. Display full report of all stored messages");
            System.out.println("7. Return to Main Menu");
            System.out.print("Select an option (1-7): ");
            storedChoice = input.nextInt();
            input.nextLine();
            
            switch (storedChoice) {
                case 1:
                    MessageClass.displayStoredSendersRecipients();
                    break;
                case 2:
                    MessageClass.printLongestStoredMessage();
                    break;
                case 3:
                    System.out.print("Enter Message ID to search: ");
                    String searchId = input.nextLine();
                    MessageClass.searchByMessageId(searchId);
                    break;
                case 4:
                    System.out.print("Enter recipient phone number (+27...): ");
                    String recipient = input.nextLine();
                    MessageClass.searchByRecipient(recipient);
                    break;
                case 5:
                    System.out.print("Enter Message Hash to delete: ");
                    String hash = input.nextLine();
                    MessageClass.deleteByMessageHash(hash);
                    break;
                case 6:
                    MessageClass.displayFullReport();
                    break;
                case 7:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-7.");
            }
        } while (storedChoice != 7);
    }
}

