package com.gm.academy.admin;


/**
 * 상담일지 객체
 * @author 3조
 *
 */
public class courseRecoardListDTO {

	private String courseseq;
	private String stdSeq;
	private String stdName;
	private String counseregdate;
	private String counsecontents;
	
	
	public String getCourseseq() {
		return courseseq;
	}
	public void setCourseseq(String courseseq) {
		this.courseseq = courseseq;
	}
	public String getCounseregdate() {
		return counseregdate;
	}
	public void setCounseregdate(String counseregdate) {
		this.counseregdate = counseregdate;
	}
	public String getCounsecontents() {
		return counsecontents;
	}
	public void setCounsecontents(String counsecontents) {
		this.counsecontents = counsecontents;
		
	
		
	}
	public String getStdSeq() {
		return stdSeq;
	}
	public void setStdSeq(String stdSeq) {
		this.stdSeq = stdSeq;
	}
	public String getStdName() {
		return stdName;
	}
	public void setStdName(String stdName) {
		this.stdName = stdName;
	}
	@Override
	public String toString() {
		return "courseRecoardListDTO [courseseq=" + courseseq + ", stdSeq=" + stdSeq + ", stdName=" + stdName
				+ ", counseregdate=" + counseregdate + ", counsecontents=" + counsecontents + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
