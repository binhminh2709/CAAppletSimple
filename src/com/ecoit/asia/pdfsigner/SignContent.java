package com.ecoit.asia.pdfsigner;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.xml.crypto.dsig.XMLSignature;

public abstract interface SignContent<T> {
	public abstract T getContentObject();
	
	public abstract XMLSignature[] getSignatures();
	
	public abstract boolean isSignedBy(X509Certificate paramX509Certificate);
	
	public abstract void addSignature(X509Certificate paramX509Certificate, PrivateKey paramPrivateKey) throws Exception;
	
	public abstract boolean validateSignatures();
	
	public abstract String getPathSigned();
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.pdfsigner.SignContent Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */