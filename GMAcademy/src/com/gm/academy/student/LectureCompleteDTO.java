package com.gm.academy.student;
/**
 * 
 * @author 공동
 *
 * 과정수료여부 <tblLectureComplete>
 */
public class LectureCompleteDTO {
	private String LecCompSeq;
	private String LecCompDate;
	private String courseSeq;
	public String getLecCompSeq() {
		return LecCompSeq;
	}
	public void setLecCompSeq(String lecCompSeq) {
		LecCompSeq = lecCompSeq;
	}
	public String getLecCompDate() {
		return LecCompDate;
	}
	public void setLecCompDate(String lecCompDate) {
		LecCompDate = lecCompDate;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "LectureComplete [LecCompSeq=" + LecCompSeq + ", LecCompDate=" + LecCompDate + ", courseSeq=" + courseSeq
				+ "]";
	}
	
	
}
