package TestClasess;

import com.mycompany.chatapp.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginUnitTest {
   
    @Test
    public void testUserNameCorrect() {
        Login auth = new Login("kyl_1", "P@ssword1", "Peter", "Peker");
        boolean actual = auth.checkUserName();
        assertTrue(actual, "The username kyl_1 should be valid (contains underscore and <= 5 chars)");
    }
    
    @Test
    public void testUserNameIncorrect() {
        // You should ALSO test a failing username
        Login auth = new Login("kyle!!!!!!!", "P@ssword1", "Peter", "Peker");
        boolean actual = auth.checkUserName();
        assertFalse(actual, "Username without underscore should be invalid");
    }
       
    @Test
    public void testPasswordSuccess() {
        Login auth = new Login("user_", "Ch&&sec@ke99!", "kaylen", "smith");
        boolean actual = auth.checkPasswordComplexity();
        assertTrue(actual, "The password should meet all complexity requirements");
    }
    
    @Test
    public void testPasswordFailure() {
        // You should ALSO test a failing password (from rubric)
        Login auth = new Login("user_", "password", "Peter", "Peker");
        boolean actual = auth.checkPasswordComplexity();
        assertFalse(actual, "Password without capital/number/special should fail");
    }
    
    @Test
    public void testRegistrationMessage() {
        Login auth = new Login("kyl_1", "Ch&&sec@ke99!", "Peter", "Peker");
        
        String phone = "+27838968976"; 
        String actual = auth.registerUser(phone);
        
        // FIXED: Matches rubric Screenshot 3
        String expected = "Welcome Anna, smith it is great to see you.";
        assertEquals(expected, actual);
    }
    
    @Test
    public void testPhoneNumberSuccess() {
        Login auth = new Login("kyl_1", "P@ssword1", "Neo", "kaylen");
        assertTrue(auth.checkCellPhoneNumber("+27838968976"), "Valid SA international format should pass");
    }

    @Test
    public void testPhoneNumberFailure() {
        Login auth = new Login("user_", "P@ssword1", "Neooo", "kayle");
        assertFalse(auth.checkCellPhoneNumber("08966553"), "Invalid format should fail");
    }
}