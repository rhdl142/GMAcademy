package com.gm.academy.lecture;

/**
 * 과정객체
 * @author 3조
 *
 */
public class LectureDTO {
	private String lectureSeq;
	private String lectuerName;
	private String lectureStartDate;
	private String lectureEndDate;
	private String classSeq;
	private String lectureProgress;
	private String lectureAcceptSTD;
	private String lectureCurrentSTD;
	private String TCHSeq;
	private String period;
	private String TeacherName; //교사 명
	
	public String getLectureSeq() {
		return lectureSeq;
	}
	public void setLectureSeq(String lectureSeq) {
		this.lectureSeq = lectureSeq;
	}
	public String getLectuerName() {
		return lectuerName;
	}
	public void setLectuerName(String lectuerName) {
		this.lectuerName = lectuerName;
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
	public String getClassSeq() {
		return classSeq;
	}
	public void setClassSeq(String classSeq) {
		this.classSeq = classSeq;
	}
	public String getLectureProgress() {
		return lectureProgress;
	}
	public void setLectureProgress(String lectureProgress) {
		this.lectureProgress = lectureProgress;
	}
	public String getLectureAcceptSTD() {
		return lectureAcceptSTD;
	}
	public void setLectureAcceptSTD(String lectureAcceptSTD) {
		this.lectureAcceptSTD = lectureAcceptSTD;
	}
	public String getLectureCurrentSTD() {
		return lectureCurrentSTD;
	}
	public void setLectureCurrentSTD(String lectureCurrentSTD) {
		this.lectureCurrentSTD = lectureCurrentSTD;
	}
	public String getTCHSeq() {
		return TCHSeq;
	}
	public void setTCHSeq(String tCHSeq) {
		TCHSeq = tCHSeq;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getTeacherName() {
		return TeacherName;
	}
	public void setTeacherName(String teacherName) {
		TeacherName = teacherName;
	}
	@Override
	public String toString() {
		return "Lecture [lectureSeq=" + lectureSeq + ", lectuerName=" + lectuerName + ", lectureStartDate="
				+ lectureStartDate + ", lectureEndDate=" + lectureEndDate + ", classSeq=" + classSeq
				+ ", lectureProgress=" + lectureProgress + ", lectureAcceptSTD=" + lectureAcceptSTD
				+ ", lectureCurrentSTD=" + lectureCurrentSTD + ", TCHSeq=" + TCHSeq + "]";
	}
}	
