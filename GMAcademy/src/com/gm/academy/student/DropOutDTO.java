package com.gm.academy.student;
/**
 * 
 * @author 공동
 *
 * 중도탈락 <tblDropOut>
 */
public class DropOutDTO {
	private String dropSeq;
	private String dropDate;
	private String courseSeq;
	public String getDropSeq() {
		return dropSeq;
	}
	public void setDropSeq(String dropSeq) {
		this.dropSeq = dropSeq;
	}
	public String getDropDate() {
		return dropDate;
	}
	public void setDropDate(String dropDate) {
		this.dropDate = dropDate;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "DropOut [dropSeq=" + dropSeq + ", dropDate=" + dropDate + ", courseSeq=" + courseSeq + "]";
	}
	
}
