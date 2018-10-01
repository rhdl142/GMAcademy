package com.gm.academy.student;

public class CourseRecordDTO {

	private String counseSeq;
	private String counseRegdate;
	private String counseContents;
	private String courseSeq;
	public String getCounseSeq() {
		return counseSeq;
	}
	public void setCounseSeq(String counseSeq) {
		this.counseSeq = counseSeq;
	}
	public String getCounseRegdate() {
		return counseRegdate;
	}
	public void setCounseRegdate(String counseRegdate) {
		this.counseRegdate = counseRegdate;
	}
	public String getCounseContents() {
		return counseContents;
	}
	public void setCounseContents(String counseContents) {
		this.counseContents = counseContents;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "CourseRecord [counseSeq=" + counseSeq + ", counseRegdate=" + counseRegdate + ", counseContents="
				+ counseContents + ", courseSeq=" + courseSeq + "]";
	}
	
	
}
