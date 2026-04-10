package com.mycompany.chatapp;

/**
 * Handles user authentication, registration validation, and status messages.
 * @author peter
 */
public class Login {

    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;

    // Constructor updated to include names required for the welcome message
    public Login(String userName, String passWord, String firstName, String lastName) {
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Task 1: Username must contain '_' and be <= 5 characters
    public boolean checkUserName() {
        return userName.contains("_") && userName.length() <= 5;
    }

    // Task 1: Password complexity requirements
    public boolean checkPasswordComplexity() {
        boolean hasCap = false, hasNum = false, hasSpec = false;
        for (int i = 0; i < passWord.length(); i++) {
            char ch = passWord.charAt(i);
            if (Character.isUpperCase(ch)) hasCap = true;
            if (Character.isDigit(ch)) hasNum = true;
            if (!Character.isLetterOrDigit(ch)) hasSpec = true;
        }
        return passWord.length() >= 8 && hasCap && hasNum && hasSpec;
    }

    // Task 1: Regex for SA Phone Number (+27 followed by 9 digits)
    // Reference: https://www.w3schools.com/java/java_regex.asp
    public boolean checkCellPhoneNumber(String phone) {
        return phone.matches("^\\+27[0-9]{9}$");
    }

    // Returns specific error or success messages for each registration part
    public String registerUser(String phone) {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        } 
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(phone)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
        // FIXED: Returns welcome message (matches unit test expectation)
        return "Welcome " + firstName + ", " + lastName + " it is great to see you.";
    }

    // Task 2: Authentication logic
    public boolean loginUser(String userTry, String passTry) {
        return userTry.equals(this.userName) && passTry.equals(this.passWord);
    }

    // Task 2: Formats the success/failure message for login
    public String returnLoginStatus(boolean isSuccessful) {
        if (isSuccessful) {
            // FIXED: Removed "again" to match rubric exactly
            return "Welcome " + firstName + ", " + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}