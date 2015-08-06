package vn.ptit.project.utils;

public class User {
	private String id;
	
	private String username;
	
	private String certificate;
	
	public User(String id, String username, String certificate) {
		/* 31 */this.id = id;
		/* 32 */this.username = username;
		/* 33 */this.certificate = certificate;
	}
	
	public String getId() {
		/* 38 */return this.id;
	}
	
	public void setId(String id) {
		/* 42 */this.id = id;
	}
	
	public String getUsername() {
		/* 46 */return this.username;
	}
	
	public void setUsername(String username) {
		/* 50 */this.username = username;
	}
	
	public String getCertificate() {
		/* 54 */return this.certificate;
	}
	
	public void setCertificate(String certificate) {
		/* 58 */this.certificate = certificate;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.utils.User Java Class Version: 7 (51.0)
 * JD-Core Version: 0.7.1
 */