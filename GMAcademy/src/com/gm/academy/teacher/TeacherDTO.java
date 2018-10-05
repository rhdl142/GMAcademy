package com.gm.academy.teacher;

/**
 * 교사객체
 * @author 3조
 *
 */
public class TeacherDTO {
	private String TCHSeq;
	private String TCHName;
	private String TCHSsn;
	private String TCHTel;
	private String TCHId;
	private int cate;
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
	public int getCate() {
		return cate;
	}
	public void setCate(int cate) {
		this.cate = cate;
	}
	public String getTCHId() {
		return TCHId;
	}
	public void setTCHId(String tCHId) {
		TCHId = tCHId;
	}
	@Override
	public String toString() {
		return "Teacher [TCHSeq=" + TCHSeq + ", TCHName=" + TCHName + ", TCHSsn=" + TCHSsn + ", TCHTel=" + TCHTel + "]";
	}
}
