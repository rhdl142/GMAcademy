package com.gm.academy.admin;

public class courseRecoardListDTO {

	private String courseseq;
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
	public String getStdName() {
		return stdName;
	}
	public void setStdName(String stdName) {
		this.stdName = stdName;
	}
	@Override
	public String toString() {
		return "courseRecoardListDTO [courseseq=" + courseseq + ", stdName=" + stdName + ", counseregdate="
				+ counseregdate + ", counsecontents=" + counsecontents + "]";
	}
	
	
	
	
	
	
	
	
}