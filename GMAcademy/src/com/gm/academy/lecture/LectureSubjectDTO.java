package com.gm.academy.lecture;

/**
 * 과정과목 객체
 * @author 3조
 *
 */
public class LectureSubjectDTO {
	private String LecSubSeq;
	private String lectureSeq;
	private String subjectSeq;
	private String SubjectStartDate;
	private String SubjectEndDate;
	private String textBookSeq;
	public String getLecSubSeq() {
		return LecSubSeq;
	}
	public void setLecSubSeq(String lecSubSeq) {
		LecSubSeq = lecSubSeq;
	}
	public String getLectureSeq() {
		return lectureSeq;
	}
	public void setLectureSeq(String lectureSeq) {
		this.lectureSeq = lectureSeq;
	}
	public String getSubjectSeq() {
		return subjectSeq;
	}
	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	public String getSubjectStartDate() {
		return SubjectStartDate;
	}
	public void setSubjectStartDate(String subjectStartDate) {
		SubjectStartDate = subjectStartDate;
	}
	public String getSubjectEndDate() {
		return SubjectEndDate;
	}
	public void setSubjectEndDate(String subjectEndDate) {
		SubjectEndDate = subjectEndDate;
	}
	public String getTextBookSeq() {
		return textBookSeq;
	}
	public void setTextBookSeq(String textBookSeq) {
		this.textBookSeq = textBookSeq;
	}
	@Override
	public String toString() {
		return "LectureSubject [LecSubSeq=" + LecSubSeq + ", lectureSeq=" + lectureSeq + ", subjectSeq=" + subjectSeq
				+ ", SubjectStartDate=" + SubjectStartDate + ", SubjectEndDate=" + SubjectEndDate + ", textBookSeq="
				+ textBookSeq + "]";
	}
	
	
}
