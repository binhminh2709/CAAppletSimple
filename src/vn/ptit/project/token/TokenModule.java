package vn.ptit.project.token;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import vn.ptit.project.exception.TokenException;

public abstract interface TokenModule {
	
	public abstract Certificate getCertificate() throws TokenException;
	
	public abstract PrivateKey getPrivateKey() throws TokenException;
	
	public abstract Certificate getEncryptCertificate() throws Exception;
	
	public abstract PrivateKey getEncryptPrivateKey() throws TokenException;
	
	public abstract boolean checkTokenAvailable();
	
	public abstract void closeToken();
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.token.TokenModule Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */