package com.ecoit.asia.pdfsigner;

import java.security.cert.X509Certificate;

public class PdfSigner implements Signer<PdfContent> {
	
	private X509Certificate certificate;
	private java.security.PrivateKey privateKey;
	
	public PdfSigner(X509Certificate certificate, java.security.PrivateKey privateKey) {
		/* 11 */this.certificate = certificate;
		/* 12 */this.privateKey = privateKey;
	}
	
	public PdfContent[] sign(PdfContent... data) throws Exception {
		PdfContent[] arrayOfPdfContent;
		/* 16 */int j = (arrayOfPdfContent = data).length;
		for (int i = 0; i < j; i++) {
			PdfContent content = arrayOfPdfContent[i];
			/* 17 */content.addSignature(this.certificate, this.privateKey);
		}
		/* 19 */return data;
	}
	
	public boolean[] verify(PdfContent... data) {
		/* 23 */boolean[] results = new boolean[data.length];
		/* 24 */for (int i = 0; i < data.length; i++) {
			/* 25 */results[i] = data[i].validateSignatures();
		}
		/* 27 */return results;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.pdfsigner.PdfSigner Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */