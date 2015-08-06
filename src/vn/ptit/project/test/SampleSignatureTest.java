package vn.ptit.project.test;

import java.security.cert.Certificate;
import java.util.Random;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;

import vn.ptit.project.token.TokenModule;
import vn.ptit.project.token.TokenModules;

import com.ecoit.asia.EcoitApplet;

public class SampleSignatureTest {
	public String generateRandomString(int length) {
		String tmp = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int loai = (int) (random.nextFloat() * 3.0F);
			switch (loai) {
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
	
	public String encrypt(String text, Certificate cert) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
			cipher.init(1, cert.getPublicKey());
			byte[] original = text.getBytes();
			byte[] cipherData = cipher.doFinal(original);
			return Hex.encodeHexString(cipherData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public SampleSignatureTest() {
		try {
			TokenModule token = TokenModules.newDefaultTokenModule();
			byte[] b = token.getCertificate().getEncoded();
			int randomStringLength = 128;
			if ((b.length > 1024) && (b.length < 2048)) {
				randomStringLength = 128;
			} else if ((b.length > 2048) && (b.length < 4086)) {
				randomStringLength = 256;
			} else if ((b.length > 4096) && (b.length < 8192)) {
				randomStringLength = 512;
			}
			String randomString = generateRandomString(randomStringLength);
			String encrypted = encrypt(randomString, token.getEncryptCertificate());
			String response = new EcoitApplet().response(encrypted);
			
			System.out.println(response.equals(randomString));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new SampleSignatureTest();
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.test.SampleSignatureTest Java Class Version:
 * 7 (51.0) JD-Core Version: 0.7.1
 */