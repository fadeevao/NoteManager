package app;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import app.login.HashGenerationHelper;

public class HashGenerationHelperTest {
	
	private HashGenerationHelper helper;
	
	@Before
	public void setUp() {
		helper = new HashGenerationHelper();
	}
	
	@Test
	public void testHashGeneration() {
		String messageOne = "messageOne";
		String messageTwo = "messageTwo";
		
		String hashOne = helper.generateSHA256(messageOne);
		String hashTwo = helper.generateSHA256(messageTwo);
		assertFalse(hashOne.equals(hashTwo));
		
	}
	
	
	
}
