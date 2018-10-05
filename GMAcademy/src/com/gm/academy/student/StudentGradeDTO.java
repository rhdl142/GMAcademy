package com.gm.academy.student;
/**
 * 
 * @author 공동
 * 
 * 학생 성적
 */
public class StudentGradeDTO {
	private String subjectName;
	private String gradeNoteScore;
	private String gradeSkillScore;
	private String gradeAttendanceScore;
	private String lecSubseq;
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	public void setGradeSkillScore(String gradeSkillSore) {
		this.gradeSkillScore = gradeSkillSore;
	}
	public String getGradeAttendanceScore() {
		return gradeAttendanceScore;
	}
	public void setGradeAttendanceScore(String gradeAttendanceScore) {
		this.gradeAttendanceScore = gradeAttendanceScore;
	}
	public String getLecSubseq() {
		return lecSubseq;
	}
	public void setLecSubseq(String lecSubseq) {
		this.lecSubseq = lecSubseq;
	}
	@Override
	public String toString() {
		return "StudentGrade [subjectName=" + subjectName + ", gradeNoteScore=" + gradeNoteScore + ", gradeSkillSore="
				+ gradeSkillScore + "]";
	}
}
