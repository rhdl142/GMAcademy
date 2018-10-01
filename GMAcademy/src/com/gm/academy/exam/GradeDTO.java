package com.gm.academy.exam;

public class GradeDTO {
	private String gradeSeq;
	private String gradeNoteScore;
	private String gradeSkillScore;
	private String gradeAttendanceScore;
	private String LecSubSeq;
	private String courseSeq;
	public String getGradeSeq() {
		return gradeSeq;
	}
	public void setGradeSeq(String gradeSeq) {
		this.gradeSeq = gradeSeq;
	}
	public String getGradeNoteScore() {
		return gradeNoteScore;
	}
	public void setGradeNoteScore(String gradeNoteScore) {
		this.gradeNoteScore = gradeNoteScore;
	}
	public String getGradeSkillScore() {
		return gradeSkillScore;
	}
	public void setGradeSkillScore(String gradeSkillScore) {
		this.gradeSkillScore = gradeSkillScore;
	}
	public String getGradeAttendanceScore() {
		return gradeAttendanceScore;
	}
	public void setGradeAttendanceScore(String gradeAttendanceScore) {
		this.gradeAttendanceScore = gradeAttendanceScore;
	}
	public String getLecSubSeq() {
		return LecSubSeq;
	}
	public void setLecSubSeq(String lecSubSeq) {
		LecSubSeq = lecSubSeq;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "Grade [gradeSeq=" + gradeSeq + ", gradeNoteScore=" + gradeNoteScore + ", gradeSkillScore="
				+ gradeSkillScore + ", gradeAttendanceScore=" + gradeAttendanceScore + ", LecSubSeq=" + LecSubSeq
				+ ", courseSeq=" + courseSeq + "]";
	}
	
	
}
