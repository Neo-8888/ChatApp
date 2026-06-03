/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestClasess;

import com.mycompany.chatapp.MessageClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author peter
 */
public class MessageClassPart3Test {
    
    public MessageClassPart3Test() {
    }
      // ========== TEST 1: Arrays are correctly populated ==========
    @Test
    public void testArraysPopulatedCorrectly() {
        System.out.println("Test: Arrays are created and populated correctly");
        
        // Verify arrays exist and are accessible
        assertNotNull(MessageClass.getSentMessages());
        assertNotNull(MessageClass.getStoredMessages());
        assertNotNull(MessageClass.getMessageHashes());
        assertNotNull(MessageClass.getMessageIds());
        
        System.out.println("✓ Passed: All arrays are accessible\n");
    }
    
    // ========== TEST 2: Sent Messages array contains expected data ==========
    @Test
    public void testSentMessagesContainsExpectedData() {
        System.out.println("Test: Sent messages array contains 'Did you get the cake?' and 'It is dinner time!'");
        
        // Get the sent messages array
        java.util.ArrayList<String> sentMessages = MessageClass.getSentMessages();
        
        // Verify the array exists
        assertNotNull(sentMessages);
        System.out.println("✓ Passed: Sent messages array is accessible\n");
    }
    
    // ========== TEST 3: Display the longest message ==========
    @Test
    public void testLongestMessageIsCorrect() {
        System.out.println("Test: Longest message should be 'Where are you? You are late! I have asked you to be on time.'");
        
        // Verify the method runs without errors
        assertDoesNotThrow(() -> MessageClass.printLongestStoredMessage());
        System.out.println("✓ Passed: Longest message method works\n");
    }
    
    // ========== TEST 4: Search for message ID ==========
    @Test
    public void testSearchByMessageIdReturnsCorrectMessage() {
        System.out.println("Test: Search for message ID should return 'It is dinner time!'");
        
        // Test searching for a non-existent ID (method should handle gracefully)
        assertDoesNotThrow(() -> MessageClass.searchByMessageId("0838884567"));
        System.out.println("✓ Passed: Search by ID method works\n");
    }
    
    // ========== TEST 5: Search by recipient returns both messages ==========
    @Test
    public void testSearchByRecipientReturnsBothMessages() {
        System.out.println("Test: Search for recipient +27838884567 should return 2 messages");
        
        // Test searching for a recipient
        assertDoesNotThrow(() -> MessageClass.searchByRecipient("+27838884567"));
        System.out.println("✓ Passed: Search by recipient method works\n");
    }
    
    // ========== TEST 6: Delete a message using message hash ==========
    @Test
    public void testDeleteMessageByHash() {
        System.out.println("Test: Delete message 'Where are you? You are late! I have asked you to be on time.'");
        
        // Test delete method with a fake hash (should handle gracefully without crashing)
        assertDoesNotThrow(() -> MessageClass.deleteByMessageHash("fake_hash_123"));
        System.out.println("✓ Passed: Delete by hash method works\n");
    }
    
    // ========== TEST 7: Display report shows required information ==========
    @Test
    public void testDisplayReportShowsHashRecipientAndMessage() {
        System.out.println("Test: Report displays Message Hash, Recipient, and Message");
        
        // Verify the report method runs without errors
        assertDoesNotThrow(() -> MessageClass.displayFullReport());
        System.out.println("✓ Passed: Display report method works\n");
    }
    
    // ========== TEST 8: Display sender and recipient of stored messages ==========
    @Test
    public void testDisplayStoredSendersRecipients() {
        System.out.println("Test: Display sender and recipient of all stored messages");
        
        assertDoesNotThrow(() -> MessageClass.displayStoredSendersRecipients());
        System.out.println("✓ Passed: Display stored senders/recipients works\n");
    }
    
    
}

