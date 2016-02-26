package app.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/*
 * Helps to generate hash for salt+password using SHA-256
 */
public class HashGenerationHelper {
	
	private static final Logger log = Logger.getLogger(HashGenerationHelper.class);
	
	private static final String HASHING_ALGORITHM = "SHA-256";
	
	 public static String generateSHA256(String message) {
	        try {
				return hashString(message);
			} catch (UnsupportedEncodingException e) {
				log.error("No supporting encoding found" + e.getStackTrace());
			}
	        return null;
	    }
	 
	 private static String hashString(String message) throws UnsupportedEncodingException{
	 
	            MessageDigest digest;
				try {
					digest = MessageDigest.getInstance(HASHING_ALGORITHM);
				
	            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
	 
	            return convertByteArrayToHexString(hashedBytes);
				} catch (NoSuchAlgorithmException e) {
					log.error("No algorithm was found for hashing" + e.getStackTrace());
				}
				
				return null;
	      
	    }
	 
	 private static String convertByteArrayToHexString(byte[] arrayBytes) {
	        StringBuffer stringBuffer = new StringBuffer();
	        for (int i = 0; i < arrayBytes.length; i++) {
	            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                    .substring(1));
	        }
	        return stringBuffer.toString();
	    }

}
