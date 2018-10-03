package com.gm.academy.student;

public class StudentDTO {
	private String STDSeq;
	private String STDName;
	private String STDSsn;
	private String STDTel;
	private String STDLecCnt;
	private int cate;
	public String getSTDSeq() {
		return STDSeq;
	}
	public void setSTDSeq(String sTDSeq) {
		STDSeq = sTDSeq;
	}
	public String getSTDName() {
		return STDName;
	}
	public void setSTDName(String sTDName) {
		STDName = sTDName;
	}
	public String getSTDSsn() {
		return STDSsn;
	}
	public void setSTDSsn(String sTDSsn) {
		STDSsn = sTDSsn;
	}
	public String getSTDTel() {
		return STDTel;
	}
	public void setSTDTel(String sTDTel) {
		STDTel = sTDTel;
	}
	public String getSTDLecCnt() {
		return STDLecCnt;
	}
	public void setSTDLecCnt(String sTDLecCnt) {
		STDLecCnt = sTDLecCnt;
	}
	public int getCate() {
		return cate;
	}
	public void setCate(int cate) {
		this.cate = cate;
	}
	@Override
	public String toString() {
		return "Student [STDSeq=" + STDSeq + ", STDName=" + STDName + ", STDSsn=" + STDSsn + ", STDTel=" + STDTel
				+ ", STDLecCnt=" + STDLecCnt + "]";
	}
}
