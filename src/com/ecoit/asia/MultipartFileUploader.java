package com.ecoit.asia;

public class MultipartFileUploader {
	public MultipartFileUploader() {
	}
	
	public String doUpload(String requestURL, String filePath) {
		System.out.println("requestURL: " + requestURL);
		System.out.println("filePath: " + filePath);
		String charset = "UTF-8";
		java.io.File uploadFile1 = new java.io.File(filePath);
		try {
			MultipartUtility multipart = new MultipartUtility(requestURL, charset);
			
			multipart.addHeaderField("User-Agent", "CodeJava");
			multipart.addHeaderField("Test-Header", "Header-Value");
			
			multipart.addFilePart("uploadfile", uploadFile1);
			
			java.util.List<String> response = multipart.finish();
			
			String s = "";
			for (String line : response) {
				s = s + line;
			}
			return s.trim();
		} catch (java.io.IOException ex) {
			System.err.println(ex);
		}
		return null;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.MultipartFileUploader Java Class Version: 7
 * (51.0) JD-Core Version: 0.7.1
 */