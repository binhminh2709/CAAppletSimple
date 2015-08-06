package vn.ptit.project.token;

import java.io.ByteArrayInputStream;

import java.security.GeneralSecurityException;

import java.security.KeyStore;

import java.security.KeyStore.Builder;

import java.security.KeyStore.CallbackHandlerProtection;

import java.security.KeyStore.PrivateKeyEntry;

import java.security.PrivateKey;

import java.security.Provider;

import java.security.Security;

import java.security.cert.Certificate;

import java.util.Enumeration;

import sun.security.pkcs11.SunPKCS11;

import sun.security.pkcs11.wrapper.PKCS11;

import vn.ptit.project.exception.TokenException;

public class SampleTokenModule implements TokenModule {
	private Provider provider;
	private KeyStore.Builder builder;
	private PKCS11 pkcs11;
	public static int lock = 2;
	
	public SampleTokenModule(String driverPath) throws TokenException {
		Provider sunPKCS11 = Security.getProvider("Token");
		
		if (sunPKCS11 == null) {
			sunPKCS11 = addSecurityProvider(driverPath);
		}
		if (sunPKCS11 == null) {
			throw new TokenException("Token not available");
		}
		this.provider = sunPKCS11;
		this.builder = KeyStore.Builder.newInstance("PKCS11", null, new KeyStore.CallbackHandlerProtection(PinInputter.getCallbackHandler()));
	}
	
	public Certificate getCertificate() throws TokenException {
		try {
			KeyStore keyStore = this.builder.getKeyStore();
			
			Enumeration<String> aliasEnum = keyStore.aliases();
			Certificate certificate = null;
			
			while (aliasEnum.hasMoreElements()) {
				String alias = (String) aliasEnum.nextElement();
				
				certificate = ((KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, this.builder.getProtectionParameter(alias))).getCertificate();
			}
			
			return certificate;
		} catch (GeneralSecurityException e) {
			throw new TokenException(e);
		}
	}
	
	public PrivateKey getPrivateKey() throws TokenException {
		try {
			KeyStore keyStore = this.builder.getKeyStore();
			Enumeration<String> aliasEnum = keyStore.aliases();
			PrivateKey key = null;
			while (aliasEnum.hasMoreElements()) {
				String alias = (String) aliasEnum.nextElement();
				key = ((KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, this.builder.getProtectionParameter(alias))).getPrivateKey();
			}
			
			return key;
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new TokenException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TokenException(e);
		}
	}
	
	public boolean checkTokenAvailable() {
		return !this.provider.isEmpty();
	}
	
	private Provider addSecurityProvider(String driverPath) {
		PKCS11 pkcs11 = null;
		try {
			pkcs11 = PKCS11.getInstance(driverPath, "C_GetFunctionList", null, false);
			long[] slotList = pkcs11.C_GetSlotList(true);
			if (slotList.length == 0)
				return null;
			String providerString = "name=Token\nlibrary=" + driverPath + "\nslot=" + slotList[0];
			SunPKCS11 sunPKCS11 = new SunPKCS11(new ByteArrayInputStream(providerString.getBytes()));
			
			Security.addProvider(sunPKCS11);
			this.pkcs11 = pkcs11;
			return sunPKCS11;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void closeToken() {
		Security.removeProvider(this.provider.getName());
		try {
			this.pkcs11.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public Certificate getEncryptCertificate() throws Exception {
		try {
			KeyStore keyStore = this.builder.getKeyStore();
			
			Enumeration<String> aliasEnum = keyStore.aliases();
			Certificate certificate = null;
			String alias;
			if (aliasEnum.hasMoreElements()) {
				alias = (String) aliasEnum.nextElement();
			}
			return
			
			((KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, this.builder.getProtectionParameter(alias))).getCertificate();
			
		} catch (GeneralSecurityException e) {
			
			throw new TokenException(e);
		}
	}
	
	public PrivateKey getEncryptPrivateKey() throws TokenException {
		try {
			KeyStore keyStore = this.builder.getKeyStore();
			Enumeration<String> aliasEnum = keyStore.aliases();
			PrivateKey key = null;
			String alias;
			if (aliasEnum.hasMoreElements())
				alias = (String) aliasEnum.nextElement();
			return ((KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, this.builder.getProtectionParameter(alias))).getPrivateKey();
			
		} catch (GeneralSecurityException e) {
			
			throw new TokenException(e);
		}
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.token.SampleTokenModule Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */