package com.maxlifeinsurance.mpro.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import com.itextpdf.text.DocumentException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Utility Program for encrypting and decrypting strings
 */

public class StringEncrypter {
	/**
	 * Utility Program for encrypting and decrypting strings
	 */
	/**
	 * DESede encryption scheme
	 */
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

	/**
	 * DES encryption scheme
	 */
	public static final String DES_ENCRYPTION_SCHEME = "DES";

	private static final String DEFAULT_ENCRYPTION_KEY = "This is a fairly long phrase used to encrypt";

	private static final String UNICODE_FORMAT = "UTF8";

	private KeySpec keySpec;

	private SecretKeyFactory keyFactory;

	private static final String DEFAULT_ENCRYPTION_KEY1 = "p@o@s@vMLI#q&&u**a**l@a%a&&di000mli11";
	
	private Cipher cipher;

        public StringEncrypter() throws NoSuchPaddingException,NoSuchAlgorithmException,UnsupportedEncodingException, InvalidKeyException
        {
        	this(DES_ENCRYPTION_SCHEME, DEFAULT_ENCRYPTION_KEY1);
        }
	/**
	 * Constructor that internally uses a default encryption key
	 * 
	 * @param encryptionScheme
	 *            the encryption scheme to be used. Pass one of the encryption
	 *            schemes defined in this class
	 * @exception DocumentException
	 *                any exception thrown in this method will be wrapped in an
	 *                PortalException and thrown again
	 */
	public StringEncrypter(String encryptionScheme) throws NoSuchPaddingException,NoSuchAlgorithmException,UnsupportedEncodingException,UnsupportedEncodingException, InvalidKeyException 
	{
		this(encryptionScheme, DEFAULT_ENCRYPTION_KEY);
//		logger.info("in constructor");
	}

	/**
	 * Constructor that uses the provided encryption key
	 * 
	 * @param encryptionScheme
	 *            the encryption scheme to be used. Pass one of the encryption
	 *            schemes defined in this class
	 * @param encryptionKey
	 *            encryption key to be used. While decrypting a string, the same
	 *            key used for encryption must be used
	 * @exception DocumentException
	 *                any exception thrown in this method will be wrapped in an
	 *                PortalException and thrown again
	 */
	public StringEncrypter(String encryptionScheme, String encryptionKey)throws  UnsupportedEncodingException,InvalidKeyException,NoSuchAlgorithmException,NoSuchPaddingException
	{
		if (encryptionKey == null) 
		{
			throw new IllegalArgumentException("Encryption key was null");
		}
		if (encryptionKey.trim().length() < 24) 
		{
			throw new IllegalArgumentException("Encryption key was less than 24 characters");
		}
		try 
		{
			byte[] keyAsBytes = encryptionKey.getBytes(UNICODE_FORMAT);

			if (encryptionScheme.equals(DESEDE_ENCRYPTION_SCHEME)) 
			{
				keySpec = new DESedeKeySpec(keyAsBytes);
			}
			else if (encryptionScheme.equals(DES_ENCRYPTION_SCHEME)) 
			{
				keySpec = new DESKeySpec(keyAsBytes);
			}
			else 
			{
				throw new IllegalArgumentException("Encryption scheme not supported: " + encryptionScheme);
			}
			keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
			cipher = Cipher.getInstance(encryptionScheme);
		}
		
		catch(Exception e)
		{
			
		}
	}

	/**
	 * Encrypt a string
	 * 
	 * @param unencryptedString
	 *            the string to be encrypted
	 * @return the encrypted string
	 * @exception DocumentException
	 *                any exception thrown in this method will be wrapped in an
	 *                PortalException and thrown again
	 */
	public String encrypt(String unencryptedString)  throws InvalidKeyException ,InvalidKeySpecException, UnsupportedEncodingException,IllegalBlockSizeException,BadPaddingException
	{
		if ((unencryptedString == null)	|| (unencryptedString.trim().length() == 0)) 
		{
			throw new IllegalArgumentException("unencrypted string was null or empty");
		}
		try 
		{
			SecretKey key = keyFactory.generateSecret(keySpec);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] ciphertext = cipher.doFinal(cleartext);

			BASE64Encoder base64encoder = new BASE64Encoder();

			return base64encoder.encode(ciphertext);
		}
		
		catch (Exception e) 
		{
			return null;
		}
	}

	/**
	 * Decrypt a string that was encrypted by this utility earlier
	 * 
	 * @param encryptedString
	 *            the string to be decrypted
	 * @return the decrypted string
	 * @exception DocumentException
	 *                any exception thrown in this method will be wrapped in an
	 *                PortalException and thrown again
	 */
	public String decrypt(String encryptedString) throws InvalidKeyException,InvalidKeySpecException,IllegalBlockSizeException,BadPaddingException,IOException
	{
		if ((encryptedString == null) || (encryptedString.trim().length() <= 0)) 
		{
			throw new  IllegalArgumentException("encrypted string was null or empty");
		}
		try 
		{
			SecretKey key = keyFactory.generateSecret(keySpec);
			cipher.init(Cipher.DECRYPT_MODE, key);

			BASE64Decoder base64decoder = new BASE64Decoder();
			byte[] cleartext = base64decoder.decodeBuffer(encryptedString);
			byte[] ciphertext = cipher.doFinal(cleartext);

			return bytes2String(ciphertext);
		}
		catch (Exception e)
		{
		  return null;
		}
		
	}

	private static String bytes2String(byte[] bytes) 
	{
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) 
		{
			stringBuilder.append((char) bytes[i]);
		}
		return stringBuilder.toString();
	}

	/**
	 * Use this method for generating encrypted strings.
	 * 
	 * @param args
	 *            Provide the string to be encrypted
	 * @exception DocumentException
	 *                any exception thrown in this method will be wrapped in an
	 *                PortalException and thrown again
	 */
	public String encryptKey(String args) throws NoSuchPaddingException,IOException,  NoSuchAlgorithmException,InvalidKeyException, BadPaddingException, IllegalBlockSizeException,InvalidKeySpecException
	{	String encryptionKey = "p@o@s@vMLI#q&&u**a**l@a%a&&di000mli11";
		if(args!=null && !"".equalsIgnoreCase(args)) {
		String encryptionScheme = StringEncrypter.DES_ENCRYPTION_SCHEME;
		StringEncrypter encrypter = new StringEncrypter(encryptionScheme,encryptionKey);				
        return encrypter.encrypt(args);		
		}
		else {
			return args;
		}
	}
	
	public String decryptKey(String args) throws NoSuchPaddingException,IOException,  NoSuchAlgorithmException,InvalidKeyException, BadPaddingException, IllegalBlockSizeException,InvalidKeySpecException
	{		
		String encryptionKey = "p@o@s@vMLI#q&&u**a**l@a%a&&di000mli11";
		
		if(args!=null && !"".equalsIgnoreCase(args)) {
		String encryptionScheme = StringEncrypter.DES_ENCRYPTION_SCHEME;
		StringEncrypter encrypter = new StringEncrypter(encryptionScheme,encryptionKey);				
        return encrypter.decrypt(args);	
		}
		else {
			return args;
		}
	}	
	
	
	
    
}