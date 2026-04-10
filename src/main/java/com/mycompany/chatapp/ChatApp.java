package com.mycompany.chatapp;

import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("--Registration---");
        
        System.out.print("Enter First Name: ");
        String fName = input.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lName = input.nextLine();
        
        // --- Username Validation ---
        String user;
        while (true) {
            System.out.print("Enter Username: ");
            user = input.nextLine();
            // We create a temporary object to check the name
            Login temp = new Login(user, "", "", ""); 
            if (temp.checkUserName()) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        // --- Password Validation ---
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

        // Finalize the actual user object
        Login auth = new Login(user, pass, fName, lName);

        // --- Login Section ---
        System.out.println("\n--Login---");
        System.out.print("Enter Username: ");
        String loginUser = input.nextLine();
        System.out.print("Enter Password: ");
        String loginPass = input.nextLine();

        boolean loginSuccess = auth.loginUser(loginUser, loginPass);
        System.out.println(auth.returnLoginStatus(loginSuccess));
    }
}