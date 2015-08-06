package com.ecoit.asia;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.io.OutputStreamWriter;

import java.io.PrintWriter;

import java.net.HttpURLConnection;

import java.net.URL;

import java.net.URLConnection;

import java.util.ArrayList;

import java.util.List;

public class MultipartUtility {
	private final String boundary;
	private static final String LINE_FEED = "\r\n";
	private HttpURLConnection httpConn;
	private String charset;
	private OutputStream outputStream;
	private PrintWriter writer;
	
	public MultipartUtility(String requestURL, String charset) throws IOException {
		/* 34 */this.charset = charset;
		
		/* 37 */this.boundary = ("===" + System.currentTimeMillis() + "===");
		
		/* 39 */URL url = new URL(requestURL);
		/* 40 */this.httpConn = ((HttpURLConnection) url.openConnection());
		/* 41 */this.httpConn.setUseCaches(false);
		/* 42 */this.httpConn.setDoOutput(true);
		/* 43 */this.httpConn.setDoInput(true);
		/* 44 */this.httpConn.setRequestProperty("Content-Type",
		/* 45 */"multipart/form-data; boundary=" + this.boundary);
		/* 46 */this.httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
		/* 47 */this.httpConn.setRequestProperty("Test", "Bonjour");
		/* 48 */this.outputStream = this.httpConn.getOutputStream();
		/* 49 */this.writer = new PrintWriter(new OutputStreamWriter(this.outputStream, charset),
		/* 50 */true);
	}
	
	public void addFormField(String name, String value) {
		/* 59 */this.writer.append("--" + this.boundary).append("\r\n");
		/* 60 */this.writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
		/* 61 */.append("\r\n");
		/* 62 */this.writer.append("Content-Type: text/plain; charset=" + this.charset).append(
		/* 63 */"\r\n");
		/* 64 */this.writer.append("\r\n");
		/* 65 */this.writer.append(value).append("\r\n");
		/* 66 */this.writer.flush();
	}
	
	public void addFilePart(String fieldName, File uploadFile) throws IOException {
		/* 77 */String fileName = uploadFile.getName();
		/* 78 */this.writer.append("--" + this.boundary).append("\r\n");
		/* 79 */this.writer.append(
		/* 80 */"Content-Disposition: form-data; name=\"" + fieldName +
		/* 81 */"\"; filename=\"" + fileName + "\"")
		/* 82 */.append("\r\n");
		/* 83 */this.writer.append(
		/* 84 */"Content-Type: " +
		/* 85 */URLConnection.guessContentTypeFromName(fileName))
		/* 86 */.append("\r\n");
		/* 87 */this.writer.append("Content-Transfer-Encoding: binary").append("\r\n");
		/* 88 */this.writer.append("\r\n");
		/* 89 */this.writer.flush();
		
		/* 91 */FileInputStream inputStream = new FileInputStream(uploadFile);
		/* 92 */byte[] buffer = new byte['?'];
		/* 93 */int bytesRead = -1;
		/* 94 */while ((bytesRead = inputStream.read(buffer)) != -1) {
			/* 95 */this.outputStream.write(buffer, 0, bytesRead);
		}
		/* 97 */this.outputStream.flush();
		/* 98 */inputStream.close();
		
		/* 100 */this.writer.append("\r\n");
		/* 101 */this.writer.flush();
	}
	
	public void addHeaderField(String name, String value) {
		/* 110 */this.writer.append(name + ": " + value).append("\r\n");
		/* 111 */this.writer.flush();
	}
	
	public List<String> finish() throws IOException {
		/* 121 */List<String> response = new ArrayList();
		
		/* 123 */this.writer.append("\r\n").flush();
		/* 124 */this.writer.append("--" + this.boundary + "--").append("\r\n");
		/* 125 */this.writer.close();
		
		/* 128 */int status = this.httpConn.getResponseCode();
		/* 129 */if (status == 200) {
			/* 130 */BufferedReader reader = new BufferedReader(new InputStreamReader(
			/* 131 */this.httpConn.getInputStream()));
			/* 132 */String line = null;
			/* 133 */while ((line = reader.readLine()) != null) {
				/* 134 */response.add(line);
			}
			/* 136 */reader.close();
			/* 137 */this.httpConn.disconnect();
		} else {
			/* 139 */throw new IOException("Server returned non-OK status: " + status);
		}
		
		/* 142 */return response;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.MultipartUtility Java Class Version: 7 (51.0)
 * JD-Core Version: 0.7.1
 */