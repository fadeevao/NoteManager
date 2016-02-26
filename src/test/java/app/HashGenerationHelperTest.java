package app;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import app.login.HashGenerationHelper;

public class HashGenerationHelperTest {
	
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testHashGeneration() {
		String messageOne = "messageOne";
		String messageTwo = "messageTwo";
		
		String hashOne = HashGenerationHelper.generateSHA256(messageOne);
		String hashTwo = HashGenerationHelper.generateSHA256(messageTwo);
		assertFalse(hashOne.equals(hashTwo));
		
	}
	
	
	
}
