package com.gm.academy.lecture;

public class SubjectDTO {
	private String subjectSeq;
	private String subjectName;
	private String updatesubjectName;
	private String lectureName;
	private String lectureSeq;
	public String getSubjectSeq() {
		return subjectSeq;
	}
	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getUpdatesubjectname() {
		return updatesubjectName;
	}
	public void setUpdatesubjectname(String updatesubjectname) {
		this.updatesubjectName = updatesubjectname;
	}
	public String getLectureName() {
		return lectureName;
	}
	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}
	public String getLectureSeq() {
		return lectureSeq;
	}
	public void setLectureSeq(String lectureSeq) {
		this.lectureSeq = lectureSeq;
	}
	@Override
	public String toString() {
		return "SubjectDTO [subjectSeq=" + subjectSeq + ", subjectName=" + subjectName + ", updatesubjectname="
				+ updatesubjectName + ", lectureName=" + lectureName + ", lectureSeq=" + lectureSeq + "]";
	}
}
