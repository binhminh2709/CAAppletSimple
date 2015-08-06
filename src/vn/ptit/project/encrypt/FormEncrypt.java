package vn.ptit.project.encrypt;

import java.security.PrivateKey;

import java.security.PublicKey;

import java.util.Random;

import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;

import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.codec.binary.Hex;

import sun.misc.BASE64Decoder;

import sun.misc.BASE64Encoder;

public class FormEncrypt {
	public FormEncrypt() {
	}
	
	public static String generateRandomString(int length) {
		String tmp = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int type = (int) (random.nextFloat() * 3.0F);
			switch (type) {
				case 0:
					tmp = tmp + (char) (48 + (int) (random.nextFloat() * 9.0F));
					break;
				case 1:
					tmp = tmp + (char) (97 + (int) (random.nextFloat() * 26.0F));
					break;
				case 2:
					tmp = tmp + (char) (65 + (int) (random.nextFloat() * 26.0F));
			}
		}
		return tmp;
	}
	
	public static String authenticate(String request, PrivateKey priKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			cipher.init(2, priKey);
			byte[] b = Hex.decodeHex(request.toCharArray());
			byte[] cipherText = cipher.doFinal(b);
			return new String(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encrypt(String plaintext, PublicKey pub) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(1, pub);
		byte[] bytes = plaintext.getBytes("UTF-8");
		
		byte[] encrypted = blockCipher(bytes, cipher, 1);
		
		return new BASE64Encoder().encode(encrypted);
	}
	
	public static String decrypt(String encrypted, PrivateKey privKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(2, privKey);
		byte[] bts = new BASE64Decoder().decodeBuffer(encrypted);
		
		byte[] decrypted = blockCipher(bts, cipher, 2);
		
		return new String(decrypted, "UTF-8");
	}
	
	private static byte[] blockCipher(byte[] bytes, Cipher cipher, int mode) throws IllegalBlockSizeException, BadPaddingException {
		byte[] scrambled = new byte[0];
		
		byte[] toReturn = new byte[0];
		
		int length = mode == 1 ? 200 : 256;
		
		byte[] buffer = new byte[length];
		
		for (int i = 0; i < bytes.length; i++) {
			
			if ((i > 0) && (i % length == 0)) {
				scrambled = cipher.doFinal(buffer);
				
				toReturn = append(toReturn, scrambled);
				
				int newlength = length;
				
				if (i + length > bytes.length) {
					newlength = bytes.length - i;
				}
				
				buffer = new byte[newlength];
			}
			
			buffer[(i % length)] = bytes[i];
		}
		
		scrambled = cipher.doFinal(buffer);
		
		toReturn = append(toReturn, scrambled);
		
		return toReturn;
	}
	
	private static byte[] append(byte[] prefix, byte[] suffix) {
		byte[] toReturn = new byte[prefix.length + suffix.length];
		for (int i = 0; i < prefix.length; i++) {
			toReturn[i] = prefix[i];
		}
		for (int i = 0; i < suffix.length; i++) {
			toReturn[(i + prefix.length)] = suffix[i];
		}
		return toReturn;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.encrypt.FormEncrypt Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */