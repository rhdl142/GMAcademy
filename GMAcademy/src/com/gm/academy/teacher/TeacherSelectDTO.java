package com.gm.academy.teacher;

public class TeacherSelectDTO {
	private String TCHSeq;
	private String TCHName;
	private String TCHSsn;
	private String TCHTel;
	private String TCHId;
	private String TCHRegdate;
	
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
	public String getTCHId() {
		return TCHId;
	}
	public void setTCHId(String tCHId) {
		TCHId = tCHId;
	}
	public String getTCHRegdate() {
		return TCHRegdate;
	}
	public void setTCHRegdate(String tCHRegdate) {
		TCHRegdate = tCHRegdate;
	}
	@Override
	public String toString() {
		return "TeacherSelectDTO [TCHSeq=" + TCHSeq + ", TCHName=" + TCHName + ", TCHSsn=" + TCHSsn + ", TCHTel="
				+ TCHTel + ", TCHId=" + TCHId + ", TCHRegdate=" + TCHRegdate + "]";
	}
}
