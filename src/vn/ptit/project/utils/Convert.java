package vn.ptit.project.utils;

import java.nio.ByteBuffer;

import java.nio.CharBuffer;

import java.nio.charset.Charset;

public class Convert {
	public Convert() {
	}
	
	public static byte[] unicodeDecode(String data) {
		ByteBuffer bb = Charset.forName("UTF-8").encode(data);
		return bb.array();
	}
	
	public static String unicodeEncode(byte[] byteData) {
		String originalData = "";
		ByteBuffer bb = ByteBuffer.wrap(byteData);
		CharBuffer cb = Charset.forName("UTF-8").decode(bb);
		char[] ch = cb.array();
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] != 0) {
				originalData = originalData + ch[i];
			}
		}
		return originalData;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.utils.Convert Java Class Version: 7 (51.0)
 * JD-Core Version: 0.7.1
 */