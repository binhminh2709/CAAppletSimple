package vn.ptit.project.test;

import com.ecoit.asia.EcoitApplet;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

public class SampleCertificateExport {
	public SampleCertificateExport() {
	}
	
	public void doTest() {
		try {
			try {
				String base64Certificate = new EcoitApplet().getCertificate();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cas_server", "root", "1234");
				PreparedStatement pre = conn.prepareStatement("UPDATE tbl_users SET certificate = ? WHERE username = 'demo3'");
				pre.setString(1, base64Certificate);
				pre.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new SampleCertificateExport().doTest();
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.test.SampleCertificateExport Java Class
 * Version: 7 (51.0) JD-Core Version: 0.7.1
 */