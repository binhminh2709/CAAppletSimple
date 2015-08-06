package vn.ptit.project.utils;

public class Message {
	private String senderId;
	
	private String recvId;
	
	private String message;
	
	private String signature;
	
	private int isSign;
	
	private int isEncrypt;
	
	public Message() {
	}
	
	public Message(String senderId, String recvId, String message, String signature, int isSign, int isEncrypt) {
		this.senderId = senderId;
		this.recvId = recvId;
		this.message = message;
		this.signature = signature;
		this.isSign = isSign;
		this.isEncrypt = isEncrypt;
	}
	
	public String getSenderId() {
		return this.senderId;
	}
	
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	public String getRecvId() {
		return this.recvId;
	}
	
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSignature() {
		return this.signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public int getIsSign() {
		return this.isSign;
	}
	
	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}
	
	public int getIsEncrypt() {
		return this.isEncrypt;
	}
	
	public void setIsEncrypt(int isEncrypt) {
		this.isEncrypt = isEncrypt;
	}
	
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: vn.ptit.project.utils.Message Java Class Version: 7 (51.0)
 * JD-Core Version: 0.7.1
 */