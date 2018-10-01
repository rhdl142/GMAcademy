package com.gm.academy.admin;

public class ErrorLogDTO {
	private String errorSeq;
	private String errorCode;
	private String errorDate;
	public String getErrorSeq() {
		return errorSeq;
	}
	public void setErrorSeq(String errorSeq) {
		this.errorSeq = errorSeq;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDate() {
		return errorDate;
	}
	public void setErrorDate(String errorDate) {
		this.errorDate = errorDate;
	}
	@Override
	public String toString() {
		return "ErrorLog [errorSeq=" + errorSeq + ", errorCode=" + errorCode + ", errorDate=" + errorDate + "]";
	}
	
	
}
