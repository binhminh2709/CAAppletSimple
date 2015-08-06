package com.ecoit.asia.pdfsigner;

import java.io.File;

import java.security.cert.X509Certificate;

import vn.ptit.project.token.TokenModule;

import vn.ptit.project.token.TokenModules;

public class SigningModules {
	public SigningModules() {
	}
	
	public static String signPDF(String filePath, File signatureFile, float minX, float minY, float maxX, float maxY) {
		try {
			TokenModule token = TokenModules.newDefaultTokenModule();
			PdfContent content = new PdfContent(filePath, signatureFile, minX, minY, maxX, maxY);
			PdfSigner signer = new PdfSigner(
(X509Certificate) token.getCertificate(),
		token.getPrivateKey());
		signer.sign(new PdfContent[] { content });
		return content.getPathSigned();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	return null;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.pdfsigner.SigningModules Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */