package com.gm.academy.lecture;

public class LectureDTO {
	private String lectureSeq; //과정번호
	private String lectureName; //과정 명
	private String lectureStartDate; //과정 시작일
	private String lectureEndDate; //과정 종료일
	private String classSeq; //강의실 번호
	private String lectureProgress; //강의 진행 현황
	private String lectureAcceptSTD; //강의 수용 인원
	private String lectureCurrentSTD; //현재 수강 인원
	private String TCHSeq; //교사 번호
	private String TeacherName; //교사 명
	private String period;
	
	public String getLectureSeq() {
		return lectureSeq;
	}
	public void setLectureSeq(String lectureSeq) {
		this.lectureSeq = lectureSeq;
	}
	public String getLectuerName() {
		return lectureName;
	}
	public void setLectuerName(String lectuerName) {
		this.lectureName = lectuerName;
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
	public void setTCHSeq(String TCHSeq) {
		this.TCHSeq = TCHSeq;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getLectureName() {
		return lectureName;
	}
	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}
	public String getTeacherName() {
		return TeacherName;
	}
	public void setTeacherName(String teacherName) {
		this.TeacherName = teacherName;
	}
	@Override
	public String toString() {
		return "LectureDTO [lectureSeq=" + lectureSeq + ", lectureName=" + lectureName + ", lectureStartDate="
				+ lectureStartDate + ", lectureEndDate=" + lectureEndDate + ", classSeq=" + classSeq
				+ ", lectureProgress=" + lectureProgress + ", lectureAcceptSTD=" + lectureAcceptSTD
				+ ", lectureCurrentSTD=" + lectureCurrentSTD + ", TCHSeq=" + TCHSeq + ", TeacherName=" + TeacherName
				+ ", period=" + period + "]";
	}
}	
