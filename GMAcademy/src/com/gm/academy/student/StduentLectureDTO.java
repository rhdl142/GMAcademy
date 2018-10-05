package com.gm.academy.student;
/**
 * 
 * @author 공동
 * 
 * 마이페이지
 */
public class StduentLectureDTO {
	private String studentSeq;
	private String studentName;
	private String studentTel;
	private String lectureName;
	private String lectureStartDate;
	private String lectureEndDate;
	private String lectureSeq;
	private String tchSeq;
	private String courseSeq;
	
	public String getStudentSeq() {
		return studentSeq;
	}
	public void setStudentSeq(String studentSeq) {
		this.studentSeq = studentSeq;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentTel() {
		return studentTel;
	}
	public void setStudentTel(String studentTel) {
		this.studentTel = studentTel;
	}
	public String getLectureName() {
		return lectureName;
	}
	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}
	public String getLectureStartDate() {
		return lectureStartDate;
	}
	public void setLectureStartDate(String lectureStartDate) {
		this.lectureStartDate = lectureStartDate;
	}
	public String getLectureEndDate() {
		return lectureEndDate;
	}
	public void setLectureEndDate(String lectureEndDate) {
		this.lectureEndDate = lectureEndDate;
	}
	public String getLectureSeq() {
		return lectureSeq;
	}
	public void setLectureSeq(String lectureSeq) {
		this.lectureSeq = lectureSeq;
	}
	public String getTchSeq() {
		return tchSeq;
	}
	public void setTchSeq(String tchSeq) {
		this.tchSeq = tchSeq;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "StduentLectureDTO [studentName=" + studentName + ", studentTel=" + studentTel 
				 + ", lectureName=" + lectureName + ", lectureStartDate=" + lectureStartDate
				+ ", lectureEndDate=" + lectureEndDate + "]";
	}
}
