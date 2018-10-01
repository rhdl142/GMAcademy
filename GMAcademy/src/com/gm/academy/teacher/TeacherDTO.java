package com.gm.academy.teacher;

public class TeacherDTO {
	private String TCHSeq;
	private String TCHName;
	private String TCHSsn;
	private String TCHTel;
	public String getTCHSeq() {
		return TCHSeq;
	}
	public void setTCHSeq(String tCHSeq) {
		TCHSeq = tCHSeq;
	}
	public String getTCHName() {
		return TCHName;
	}
	public void setTCHName(String tCHName) {
		TCHName = tCHName;
	}
	public String getTCHSsn() {
		return TCHSsn;
	}
	public void setTCHSsn(String tCHSsn) {
		TCHSsn = tCHSsn;
	}
	public String getTCHTel() {
		return TCHTel;
	}
	public void setTCHTel(String tCHTel) {
		TCHTel = tCHTel;
	}
	@Override
	public String toString() {
		return "Teacher [TCHSeq=" + TCHSeq + ", TCHName=" + TCHName + ", TCHSsn=" + TCHSsn + ", TCHTel=" + TCHTel + "]";
	}
	
	
}
