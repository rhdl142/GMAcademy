package com.gm.academy.admin;

public class LogDTO {
	private String logSeq;
	private String logCode;
	private String logContents;
	private String logDate;
	public String getLogSeq() {
		return logSeq;
	}
	public void setLogSeq(String logSeq) {
		this.logSeq = logSeq;
	}
	public String getLogCode() {
		return logCode;
	}
	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
	public String getLogContents() {
		return logContents;
	}
	public void setLogContents(String logContents) {
		this.logContents = logContents;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	@Override
	public String toString() {
		return "Log [logSeq=" + logSeq + ", logCode=" + logCode + ", logContents=" + logContents + ", logDate="
				+ logDate + "]";
	}
	
	
}
