package vn.ptit.project.token;

import java.nio.file.Files;

import java.nio.file.LinkOption;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.util.HashMap;

import java.util.Map;

import vn.ptit.project.exception.TokenException;

public class TokenModules {
	public static final String providers = "VNPT-CA\\VNPT-CA PKI Token CSP\\wdpkcs.dll;etpkcs11.dll;eTPKCS11.dll;ostc1_csp11.dll;vdctdcsp11.dll;vnpt-ca_csp11.dll;BkavCA.dll;vnpt-ca_v34.dll;viettel-ca_v2_s.x64.dll;ShuttleCsp11_3003.dll;ngp11v211.dll;st3csp11.dll;gclib.dll;fpt-ca.dll;CA2_v34.dll;CA2_csp11.dll;psapkcs.dll;viettel-ca_v2.dll;gclib.dll";
	
	public TokenModules() {
	}
	
	private static Map<String, TokenModule> cache = new HashMap();
	
	public static TokenModule newDefaultTokenModule() {
		String[] providerList = "VNPT-CA\\VNPT-CA PKI Token CSP\\wdpkcs.dll;etpkcs11.dll;eTPKCS11.dll;ostc1_csp11.dll;vdctdcsp11.dll;vnpt-ca_csp11.dll;BkavCA.dll;vnpt-ca_v34.dll;viettel-ca_v2_s.x64.dll;ShuttleCsp11_3003.dll;ngp11v211.dll;st3csp11.dll;gclib.dll;fpt-ca.dll;CA2_v34.dll;CA2_csp11.dll;psapkcs.dll;viettel-ca_v2.dll;gclib.dll"
		        .split(";");
		
		TokenModule token = null;
		String[] arrayOfString1 = providerList;
		int j = providerList.length;
		for (int i = 0; i < j; i++) {
			String provider = arrayOfString1[i];
			Path driver = Paths.get(System.getenv("windir"), new String[] { "system32", provider });
			
			if (Files.exists(driver, new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
				try {
					token = (TokenModule) cache.get(driver.toString());
					if (token == null) {
						token = new SampleTokenModule(driver.toString());
					}
					if (token != null) {
						cache.put(driver.toString(), token);
						return token;
					}
				} catch (Exception e) {
					if (!(e instanceof TokenException)) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return token;
	}
	
	public static void clearCache() {
		for (TokenModule tokenModule : cache.values()) {
			tokenModule.closeToken();
		}
		cache.clear();
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.token.TokenModules Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */