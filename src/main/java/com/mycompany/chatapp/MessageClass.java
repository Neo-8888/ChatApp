/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author peter
 */
public class MessageClass {
    
    // ========== VARIABLES ==========
    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status; // "sent", "stored", "disregarded"
    
    private static int totalMessagesSent = 0;
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<MessageClass> allMessages = new ArrayList<>();
    
    // ========== CONSTRUCTORS ==========
    
    // No-argument constructor (needed for JSON)
    public MessageClass() {
    }
    
    // Constructor to create a new message
    public MessageClass(int messageNumber, String recipient, String messageText) {
        this.messageId = generateMessageId();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash(this.messageId, this.messageNumber, this.messageText);
        this.status = "pending";
        allMessages.add(this); // Add to list when created
    }
    
    // ========== METHOD 1: Generate Message ID ==========
    public String generateMessageId() {
        Random rand = new Random();
        long randomId = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(randomId);
    }
    
    // ========== METHOD 2: Check Message Length ==========
    public String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
    // ========== METHOD 3: Check Recipient Cell Number ==========
    public String checkRecipientCell(String phone) {
        if (phone != null && phone.matches("^\\+27[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    
    // ========== METHOD 4: Create Message Hash ==========
    public String createMessageHash(String messageId, int messageNumber, String messageText) {
        String firstTwoDigits = messageId.substring(0, 2);
        
        String[] words = messageText.split(" ");
        String firstWord = "";
        String lastWord = "";
        
        if (words.length > 0) {
            firstWord = words[0].toUpperCase();
            lastWord = words[words.length - 1].toUpperCase();
            lastWord = lastWord.replaceAll("[^A-Z]", "");
        }
        
        return firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord;
    }
    
    // ========== METHOD 5: Send Message Options Menu ==========
    public String sendMessageOptions() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter choice: ");
        
        int choice = input.nextInt();
        input.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                this.status = "sent";
                totalMessagesSent++;
                return "Message successfully sent";
            case 2:
                this.status = "disregarded";
                return "Press 0 to delete the message";
            case 3:
                this.status = "stored";
                return "Message successfully stored";
            default:
                return "Invalid option";
        }
    }
    
    // ========== METHOD 6: Display Message Details ==========
    public void displayMessage() {
        System.out.println("\n--- MESSAGE DETAILS ---");
        System.out.println("Message ID: " + messageId);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
        System.out.println("Status: " + status);
        System.out.println("------------------------");
    }
    
    // ========== METHOD 7: Get Total Messages Sent ==========
    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }
    
    // ========== METHOD 8: Save messages to JSON file ==========
        public static void storeMessage() {
        try {
            FileWriter writer = new FileWriter("messages.json");
            writer.write("[\n");
            
            for (int i = 0; i < allMessages.size(); i++) {
                MessageClass msg = allMessages.get(i);
                writer.write("  {\n");
                writer.write("    \"messageId\": \"" + msg.getMessageId() + "\",\n");
                writer.write("    \"messageNumber\": " + msg.getMessageNumber() + ",\n");
                writer.write("    \"recipient\": \"" + msg.getRecipient() + "\",\n");
                writer.write("    \"messageText\": \"" + msg.getMessageText() + "\",\n");
                writer.write("    \"messageHash\": \"" + msg.getMessageHash() + "\",\n");
                writer.write("    \"status\": \"" + msg.getStatus() + "\"\n");
                writer.write("  }");
                if (i < allMessages.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            
            writer.write("]\n");
            writer.close();
            System.out.println("Messages saved to messages.json");
            
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }
    
    // ========== METHOD 9: Load messages from JSON file ==========
    public static void loadMessagesFromFile() {
        // Clear current list
        allMessages.clear();
        
        // For now, start with empty list
        // Full JSON parsing would require external library like Jackson
        // Reference: https://www.w3schools.com/js/js_json.asp
        
        System.out.println("Loaded " + allMessages.size() + " previous messages.");
    }
    
    // ========== GETTERS AND SETTERS ==========
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
    
    public int getMessageNumber() { return messageNumber; }
    public void setMessageNumber(int messageNumber) { this.messageNumber = messageNumber; }
    
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    
    public String getMessageHash() { return messageHash; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}