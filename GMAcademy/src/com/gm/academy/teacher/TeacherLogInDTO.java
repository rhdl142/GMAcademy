package com.gm.academy.teacher;

public class TeacherLogInDTO {
	private String TCHSeq;
	private String TCHID;
	private String TCHPW;
	private String TCHRegdate;
	public String getTCHSeq() {
		return TCHSeq;
	}
	public void setTCHSeq(String tCHSeq) {
		TCHSeq = tCHSeq;
	}
	public String getTCHID() {
		return TCHID;
	}
	public void setTCHID(String tCHID) {
		TCHID = tCHID;
	}
	public String getTCHPW() {
		return TCHPW;
	}
	public void setTCHPW(String tCHPW) {
		TCHPW = tCHPW;
	}
	public String getTCHRegdate() {
		return TCHRegdate;
	}
	public void setTCHRegdate(String tCHRegdate) {
		TCHRegdate = tCHRegdate;
	}
	@Override
	public String toString() {
		return "TeacherLogIn [TCHSeq=" + TCHSeq + ", TCHID=" + TCHID + ", TCHPW=" + TCHPW + ", TCHRegdate=" + TCHRegdate
				+ "]";
	}
	
	
}
