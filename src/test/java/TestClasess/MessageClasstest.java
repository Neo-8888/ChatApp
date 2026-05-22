/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestClasess;

import com.mycompany.chatapp.MessageClass;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author peter
 */
public class MessageClasstest {
    
    public MessageClasstest() {
    }
    
      //TEST 1: Message Length SUCCESS 
    @Test
    public void testMessageLengthSuccess() {
        System.out.println("Test: Message length success (≤ 250 chars)");
        
        MessageClass msg = new MessageClass();
        String shortMessage = "Hi Mike, can you join us for dinner tonight?";
        String result = msg.checkMessageLength(shortMessage);
        
        assertEquals("Message ready to send.", result);
        System.out.println("✓ Passed: Short message is accepted\n");
    }
    
    // \TEST 2: Message Length FAILURE
    @Test
    public void testMessageLengthFailure() {
        System.out.println("Test: Message length failure (> 250 chars)");
        
        MessageClass msg = new MessageClass();
        
       
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMessage.append("a");
        }
        
        String result = msg.checkMessageLength(longMessage.toString());
        
        
        assertTrue(result.contains("exceeds 250 characters by 10"));
        System.out.println("✓ Passed: Long message is rejected with correct message\n");
    }
    
    // TEST 3: Recipient SUCCESS (+27 format) 
    @Test
    public void testRecipientSuccess() {
        System.out.println("Test: Recipient format success (+27XXXXXXXXX)");
        
        MessageClass msg = new MessageClass();
        String result = msg.checkRecipientCell("+27718693002");
        
        assertEquals("Cell phone number successfully captured.", result);
        System.out.println("✓ Passed: Valid phone number accepted\n");
    }
    
    //TEST 4: Recipient FAILURE wrong format
    @Test
    public void testRecipientFailure() {
        System.out.println("Test: Recipient format failure (wrong format)");
        
        MessageClass msg = new MessageClass();
        String result = msg.checkRecipientCell("08575975889");
        
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
        System.out.println("✓ Passed: Invalid phone number rejected\n");
    }
    
    // TEST 5: Message Hash 
    @Test
    public void testMessageHashCorrect() {
        System.out.println("Test: Message hash creation");
        
        MessageClass msg = new MessageClass();
        
        // Using test data from the POE
        // Message ID starting with "00", Message number = 0, Message = "HI THANKS"
        String hash = msg.createMessageHash("0012345678", 0, "HI THANKS");
        
        assertEquals("00:0:HITHANKS", hash);
        System.out.println("✓ Passed: Message hash is correct format\n");
    }
    
    //  TEST 6: Message Hash from Test Case
    @Test
    public void testMessageHashFromRubric() {
        System.out.println("Test: Message hash from rubric test case 1");
        
        MessageClass msg = new MessageClass();
        
        // Test from rubric: Should return "00:0:HITONIGHT"
        String hash = msg.createMessageHash("0012345678", 0, "Hi Mike, can you join us for dinner tonight?");
        
        assertEquals("00:0:HITONIGHT", hash);
        System.out.println("✓ Passed: Message hash matches rubric example\n");
    }
    
    //  TEST 7: Message ID is 10 digits
    @Test
    public void testMessageIdIsTenDigits() {
        System.out.println("Test: Message ID is 10 digits");
        
        MessageClass msg = new MessageClass();
        String messageId = msg.generateMessageId();
        
        assertEquals(10, messageId.length());
        assertTrue(messageId.matches("\\d{10}")); 
        System.out.println("✓ Passed: Message ID is 10 digits: " + messageId + "\n");
    }
    
    // TEST 8: Message Hash with one word messag
    @Test
    public void testMessageHashWithOneWord() {
        System.out.println("Test: Message hash with one word message");
        
        MessageClass msg = new MessageClass();
        String hash = msg.createMessageHash("9912345678", 5, "Hello");
        
        // With one word, first word and last word are the same
        assertEquals("99:5:HELLOHELLO", hash);
        System.out.println("✓ Passed: One word message hash is correct\n");
    }
}
    
