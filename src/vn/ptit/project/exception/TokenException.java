package vn.ptit.project.exception;

public class TokenException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public TokenException(String message) {
		super(message);
	}
	
	public TokenException(Exception e) {
	super(e.getCause());
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.exception.TokenException Java Class Version:
 * 7 (51.0) JD-Core Version: 0.7.1
 */