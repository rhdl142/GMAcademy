package com.gm.academy.admin;;

public class LoginDTO {
	private String logInSeq;
	private String logInCode;
	private String logInDate;
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
	@Override
	public String toString() {
		return "Login [logInSeq=" + logInSeq + ", logInCode=" + logInCode + ", logInDate=" + logInDate + "]";
	}
	
	

}
