package com.ecoit.asia.pdfsigner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.List;

import javax.xml.crypto.dsig.XMLSignature;

import com.ecoit.asia.EcoitApplet;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.text.pdf.security.TSAClient;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;

public class PdfContent implements SignContent<PdfReader> {
	private PdfReader content;
	private String path;
	private String signedPath;
	private File signatureFile;
	private float minX;
	private float minY;
	private float maxX;
	private float maxY;
	
	public PdfContent(String path, File signatureFile, float minX, float minY, float maxX, float maxY) throws Exception {
		/* 49 */this.minX = minX;
		/* 50 */this.minY = minY;
		/* 51 */this.maxX = maxX;
		/* 52 */this.maxY = maxY;
		/* 53 */this.path = path;
		/* 54 */this.content = new PdfReader(path);
		/* 55 */this.signatureFile = signatureFile;
	}
	
	public PdfContent(String path, byte[] byteArrOfFile) throws IOException {
		/* 59 */this.path = path;
		/* 60 */File file = new File(path);
		/* 61 */FileOutputStream fos = new FileOutputStream(file);
		/* 62 */fos.write(byteArrOfFile);
		/* 63 */fos.close();
		
		/* 65 */this.content = new PdfReader(path);
	}
	
	public PdfReader getContentObject() {
		/* 70 */return this.content;
	}
	
	public XMLSignature[] getSignatures() {
		/* 75 */return null;
	}
	
	public boolean isSignedBy(X509Certificate certificate) {
		/* 80 */return false;
	}
	
	/* 83 */static int k = 0;
	
	public void addSignature(X509Certificate certificate, PrivateKey key) throws Exception {
		/* 88 */Certificate[] cer = { certificate };
		/* 89 */for (int index = this.path.length() - 1; index >= 0; index--)
			/* 90 */if (this.path.charAt(index) == '.') {
				/* 91 */this.signedPath =
				/* 92 */(this.path.substring(0, index) + "_signed" + this.path.substring(index));
				/* 93 */break;
			}
		/* 95 */File file = new File(this.signedPath);
		/* 96 */FileOutputStream out = new FileOutputStream(file);
		/* 97 */PdfStamper stp = PdfStamper.createSignature(this.content, out, '\000', null,
		/* 98 */true);
		
		/* 100 */PdfSignatureAppearance sap = stp.getSignatureAppearance();
		/* 101 */Image img = Image.getInstance(this.signatureFile.getAbsolutePath());
		/* 102 */sap.setImage(img);
		/* 103 */sap.setSignatureGraphic(img);
		/* 104 */sap.setLayer2Text("");
		/* 105 */sap.setLayer4Text("");
		
		/* 107 */sap.setVisibleSignature(new Rectangle(this.minX, this.minY, this.maxX, this.maxY),
		/* 108 */EcoitApplet.page + 1, null);
		try {
			/* 111 */TSAClient tsc = new TSAClientBouncyCastle("http://ca.gov.vn/tsa", "", "", 2000, "sha-1");
			
			/* 113 */ExternalDigest digest = new BouncyCastleDigest();
			/* 114 */ExternalSignature signature = new PrivateKeySignature(key, "SHA-1",
			/* 115 */"SunPKCS11-Token");
			
			/* 117 */OcspClientBouncyCastle ocsp = new OcspClientBouncyCastle();
			
			/* 120 */MakeSignature.signDetached(sap, digest, signature,
			/* 121 */new Certificate[] { certificate }, null, ocsp, tsc, 0,
			/* 122 */MakeSignature.CryptoStandard.CMS);
			/* 123 */stp.close();
		} catch (Exception localException) {
		}
	}
	
	public String getPathSigned() {
		/* 131 */return this.signedPath;
	}
	
	public boolean validateSignatures() {
		/* 136 */AcroFields af = this.content.getAcroFields();
		/* 137 */List<String> names = af.getSignatureNames();
		/* 138 */String name = (String) names.get(0);
		/* 139 */PdfPKCS7 pk = af.verifySignature(name);
		/* 140 */X509Certificate[] pkc = (X509Certificate[]) pk.getCertificates();
		/* 141 */Calendar calendar = pk.getTimeStampDate();
		/* 142 */boolean b = false;
		try {
			/* 144 */b = pk.verify();
		} catch (SignatureException e) {
			/* 146 */e.printStackTrace();
		}
		
		/* 152 */return b;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.pdfsigner.PdfContent Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */