package com.gm.academy.admin;;

public class LoginDTO {
	private String logInSeq;
	private String logInCode;
	private String logInDate;
	private String logoutDate;
	
	public String getLogInSeq() {
		return logInSeq;
	}
	public void setLogInSeq(String logInSeq) {
		this.logInSeq = logInSeq;
	}
	public String getLogInCode() {
		return logInCode;
	}
	public void setLogInCode(String logInCode) {
		this.logInCode = logInCode;
	}
	public String getLogInDate() {
		return logInDate;
	}
	public void setLogInDate(String logInDate) {
		this.logInDate = logInDate;
	}
	public String getLogoutDate() {
		return logoutDate;
	}
	public void setLogoutDate(String logoutDate) {
		this.logoutDate = logoutDate;
	}
	@Override
	public String toString() {
		return "Login [logInSeq=" + logInSeq + ", logInCode=" + logInCode + ", logInDate=" + logInDate + "]";
	}
}
