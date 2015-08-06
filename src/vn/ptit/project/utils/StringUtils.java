package vn.ptit.project.utils;

import java.math.BigInteger;

import java.security.SecureRandom;

public class StringUtils {
	public StringUtils() {
	}
	
	public static String getRandomString(int length) {
		/* 35 */SecureRandom random = new SecureRandom();
		/* 36 */return new BigInteger(130, random).toString(length);
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.utils.StringUtils Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */