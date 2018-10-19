package fr.acceis.forum.classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Passwords {

	private String password;
	private String salt;
	private MessageDigest messageDigest;
	private static final Random RANDOM = new SecureRandom();

	/**
	 * static utility class
	 */
	public Passwords(String pass, String bs) {
		this.salt = bs;
		password = hash(pass+bs);
	}

	
	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}
	/**
	 * Returns a random salt to be used to hash a password.
	 *
	 * @return a 16 bytes random salt
	 */
	public static String getNextSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		
		 StringBuffer salted = new StringBuffer();
			for (byte bytes : salt) {
				salted.append(String.format("%02x", bytes & 0xff));
			}
			
		return salted.toString();
	}
	
	/**
	 * Hash.
	 *
	 * @param data the data
	 * @return the string
	 */
	private String hash(String data) {
		StringBuffer stringBuffer = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(data.getBytes());
			byte[] messageDigestMD5 = messageDigest.digest();
			stringBuffer = new StringBuffer();
			for (byte bytes : messageDigestMD5) {
				stringBuffer.append(String.format("%02x", bytes & 0xff));
			}

		} catch (NoSuchAlgorithmException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		return stringBuffer.toString();
	}	
}
