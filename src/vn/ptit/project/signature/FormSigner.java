package vn.ptit.project.signature;

import java.security.PrivateKey;

import java.security.PublicKey;

import java.security.Signature;

import sun.misc.BASE64Decoder;

import sun.misc.BASE64Encoder;

import vn.ptit.project.exception.SignatureException;

import vn.ptit.project.token.TokenModule;

import vn.ptit.project.token.TokenModules;

import vn.ptit.project.utils.Convert;

public class FormSigner {
	public FormSigner() {
	}
	
	public static String signForm(String original) throws SignatureException {
		try {
			TokenModule token = TokenModules.newDefaultTokenModule();
			PrivateKey privateKey = token.getEncryptPrivateKey();
			Signature signature = Signature.getInstance("SHA512withRSA");
			signature.initSign(privateKey);
			byte[] b = Convert.unicodeDecode(original);
			signature.update(b);
			byte[] _signature = signature.sign();
			return new BASE64Encoder().encode(_signature);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SignatureException();
		}
	}
	
	public static boolean verifyForm(String signature, String original, PublicKey pub) {
		try {
			Signature sign = Signature.getInstance("SHA512withRSA");
			sign.initVerify(pub);
			byte[] byteOriginal = Convert.unicodeDecode(original);
			sign.update(byteOriginal);
			byte[] _signature = new BASE64Decoder().decodeBuffer(signature);
			return sign.verify(_signature);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.signature.FormSigner Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */