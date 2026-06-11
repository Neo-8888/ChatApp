/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author peter
 */
public class MessageClass {
    
    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status; // "sent", "stored", "disregarded"
    
    private static int totalMessagesSent = 0;
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<MessageClass> allMessages = new ArrayList<>();
    
    // Arrays for Part 3
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIds = new ArrayList<>();
    
    // CONSTRUCTORS 
    public MessageClass() {
    }
    
    public MessageClass(int messageNumber, String recipient, String messageText) {
        this.messageId = generateMessageId();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash(this.messageId, this.messageNumber, this.messageText);
        this.status = "pending";
        allMessages.add(this); 
    }
    
    // METHOD 1: Generate Message ID 
    public String generateMessageId() {
        Random rand = new Random();
        long randomId = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(randomId);
    }
    
    // METHOD 2: Check Message Length 
    public String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
    // METHOD 3: Check Recipient Cell Number
    public String checkRecipientCell(String phone) {
        if (phone != null && phone.matches("^\\+27[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    
    // METHOD 4: Create Message Hash
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
    
    // METHOD 5: Send Message Options Menu 
    public String sendMessageOptions() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter choice: ");
        
        int choice = input.nextInt();
        input.nextLine();
        
        switch (choice) {
            case 1:
                this.status = "sent";
                totalMessagesSent++;
                updateArrays();
                return "Message successfully sent";
            case 2:
                this.status = "disregarded";
                updateArrays();
                return "Press 0 to delete the message";
            case 3:
                this.status = "stored";
                updateArrays();
                return "Message successfully stored";
            default:
                return "Invalid option";
        }
    }
    
    // Update all arrays based on message status
    private void updateArrays() {
        messageIds.add(this.messageId);
        messageHashes.add(this.messageHash);
        
        if (this.status.equals("sent")) {
            sentMessages.add(this.messageText);
        } else if (this.status.equals("disregarded")) {
            disregardedMessages.add(this.messageText);
        } else if (this.status.equals("stored")) {
            storedMessages.add(this.messageText);
        }
    }
    
    // METHOD 6: Display Message Details 
    public void displayMessage() {
        System.out.println("\n--- MESSAGE DETAILS ---");
        System.out.println("Message ID: " + messageId);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
        System.out.println("Status: " + status);
        System.out.println("------------------------");
    }
    
    // METHOD 7: Get Total Messages Sent 
    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }
    
    // METHOD 8: Save messages to JSON file 
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
    
    // METHOD 9: Load messages from JSON file
    public static void loadMessagesFromFile() {
        allMessages.clear();
        java.io.File file = new java.io.File("messages.json");
        
        if (!file.exists()) {
            System.out.println("No previous messages found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            
            String content = sb.toString();
            if (content.startsWith("[") && content.endsWith("]")) {
                content = content.substring(1, content.length() - 1);
                if (!content.isEmpty()) {
                    String[] records = content.split("\\},\\{");
                    
                    for (String record : records) {
                        record = record.replace("{", "").replace("}", "").replace("\"", "");
                        String[] pairs = record.split(",");
                        
                        MessageClass loadedMsg = new MessageClass();
                        for (String pair : pairs) {
                            String[] keyValue = pair.split(":");
                            if (keyValue.length >= 2) {
                                String key = keyValue[0].trim();
                                String value = keyValue[1].trim();
                                
                                switch (key) {
                                    case "messageId": loadedMsg.setMessageId(value); break;
                                    case "messageNumber": loadedMsg.setMessageNumber(Integer.parseInt(value)); break;
                                    case "recipient": loadedMsg.setRecipient(value); break;
                                    case "messageText": loadedMsg.setMessageText(value); break;
                                    case "messageHash": loadedMsg.setMessageHash(value); break;
                                    case "status": loadedMsg.setStatus(value); break;
                                }
                            }
                        }
                        allMessages.add(loadedMsg);
                        loadedMsg.updateArrays();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading messages: " + e.getMessage());
        }
        System.out.println("Loaded " + allMessages.size() + " messages.");
    }
    
    // ========== PART 3 METHODS ==========
    
    // Display sender and recipient of all stored messages
    public static void displayStoredSendersRecipients() {
        if (allMessages.isEmpty()) {
            System.out.println("No messages found.");
            return;
        }
        
        System.out.println("\n--- ALL STORED MESSAGES (Sender & Recipient) ---");
        for (MessageClass msg : allMessages) {
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message: " + msg.getMessageText());
            System.out.println("Status: " + msg.getStatus());
            System.out.println("------------------------");
        }
    }
    
    // Display the longest stored message
    public static void printLongestStoredMessage() {
        if (allMessages.isEmpty()) {
            System.out.println("No messages to analyze.");
            return;
        }
        
        MessageClass longest = allMessages.get(0);
        for (MessageClass msg : allMessages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        
        System.out.println("\n>>> LONGEST STORED MESSAGE <<<");
        System.out.println("Length: " + longest.getMessageText().length() + " characters");
        System.out.println("Message: " + longest.getMessageText());
        System.out.println("Recipient: " + longest.getRecipient());
        System.out.println("Message ID: " + longest.getMessageId());
    }
    
    // Search for a message by Message ID
    public static void searchByMessageId(String targetId) {
        boolean found = false;
        for (MessageClass msg : allMessages) {
            if (msg.getMessageId().equals(targetId)) {
                System.out.println("\n✓ Message Found!");
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getMessageText());
                System.out.println("Status: " + msg.getStatus());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("\n✗ No message found with ID: " + targetId);
        }
    }
    
    // Search all messages for a particular recipient
    public static void searchByRecipient(String phoneNumber) {
        boolean found = false;
        System.out.println("\n--- MESSAGES FOR RECIPIENT: " + phoneNumber + " ---");
        
        for (MessageClass msg : allMessages) {
            if (msg.getRecipient().equals(phoneNumber)) {
                System.out.println("Message: " + msg.getMessageText());
                System.out.println("Message Hash: " + msg.getMessageHash());
                System.out.println("Status: " + msg.getStatus());
                System.out.println("------------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No messages found for recipient: " + phoneNumber);
        }
    }
    
    // Delete a message using message hash
    public static void deleteByMessageHash(String hash) {
        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).getMessageHash().equals(hash)) {
                allMessages.remove(i);
                storeMessage();
                System.out.println("Message with hash '" + hash + "' successfully deleted.");
                return;
            }
        }
        System.out.println("No message found with hash: " + hash);
    }
    
    // Display full report of all stored messages
    public static void displayFullReport() {
        if (allMessages.isEmpty()) {
            System.out.println("No messages to display.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("            COMPLETE MESSAGE REPORT");
        System.out.println("=".repeat(60));
        
        for (MessageClass msg : allMessages) {
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Recipient:    " + msg.getRecipient());
            System.out.println("Message:      " + msg.getMessageText());
            System.out.println("Status:       " + msg.getStatus());
            System.out.println("-".repeat(40));
        }
        
        System.out.println("Total messages: " + allMessages.size());
    }
    
    // Display all sent messages
    public static void displaySentMessages() {
        System.out.println("\n--- SENT MESSAGES ---");
        for (String msg : sentMessages) {
            System.out.println("- " + msg);
        }
    }
    
    // GETTERS AND SETTERS 
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
        // ========== GETTERS FOR TESTING (Part 3) ==========
    public static ArrayList<String> getSentMessages() {
        return sentMessages;
    }
    
    public static ArrayList<String> getStoredMessages() {
        return storedMessages;
    }
    
    public static ArrayList<String> getMessageHashes() {
        return messageHashes;
    }
    
    public static ArrayList<String> getMessageIds() {
        return messageIds;
    }
    
    
}
