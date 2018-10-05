package com.gm.academy.admin;

/**
 * 에러로그 객체
 * @author 3조
 *
 */
public class ErrorLogDTO {
	private String errorSeq;
	private String errorCode;
	private String errorDate;
	private String part;
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
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	@Override
	public String toString() {
		return "ErrorLog [errorSeq=" + errorSeq + ", errorCode=" + errorCode + ", errorDate=" + errorDate + "]";
	}
}
