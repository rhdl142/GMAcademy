package com.gm.academy.admin;;

/**
 * 로그인 객체
 * @author 3조
 *
 */
public class LoginDTO {
	private String logInSeq;
	private String logInCode;
	private String logInDate;
	private String logOutDate;
	
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
	public String getLogOutDate() {
		return logOutDate;
	}
	public void setLogOutDate(String logOutDate) {
		this.logOutDate = logOutDate;
	}
	
	

}
