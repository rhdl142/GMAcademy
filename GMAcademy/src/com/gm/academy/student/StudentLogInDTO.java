package com.gm.academy.student;
/**
 * 
 * @author 공동
 * 
 * 교육생 로그인 <tblStudentLogin>
 */
public class StudentLogInDTO {
	private String STDSeq;
	private String STDID;
	private String STDPW;
	private String STDRegdate;
	public String getSTDSeq() {
		return STDSeq;
	}
	public void setSTDSeq(String sTDSeq) {
		STDSeq = sTDSeq;
	}
	public String getSTDID() {
		return STDID;
	}
	public void setSTDID(String sTDID) {
		STDID = sTDID;
	}
	public String getSTDPW() {
		return STDPW;
	}
	public void setSTDPW(String sTDPW) {
		STDPW = sTDPW;
	}
	public String getSTDRegdate() {
		return STDRegdate;
	}
	public void setSTDRegdate(String sTDRegdate) {
		STDRegdate = sTDRegdate;
	}
	@Override
	public String toString() {
		return "StudentLogIn [STDSeq=" + STDSeq + ", STDID=" + STDID + ", STDPW=" + STDPW + ", STDRegdate=" + STDRegdate
				+ "]";
	}
	
	
}
