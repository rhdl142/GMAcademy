package com.gm.academy.admin;

public class StudentGradeInfoDTO {
	private String STDseq;
	private String STDName;
	private String gradeNoteScore;
	private String gradeSkillScore;
	private String gradeAttendanceScore;
	private String lectureName;
	private String subjectSeq;
	private String subjectName;
	private String updateGradeNoteScore;		//수정되는 필기점수
	private String updateGradeSkillScore;		//수정되는 실기점수
	private String updateGradeAttendanceScore;		//수정되는 출석점수
	
	public String getSTDseq() {
		return STDseq;
	}
	public String getUpdateGradeNoteScore() {
		return updateGradeNoteScore;
	}
	public void setUpdateGradeNoteScore(String updateGradeNoteScore) {
		this.updateGradeNoteScore = updateGradeNoteScore;
	}
	public String getUpdateGradeSkillScore() {
		return updateGradeSkillScore;
	}
	public void setUpdateGradeSkillScore(String updateGradeSkillScore) {
		this.updateGradeSkillScore = updateGradeSkillScore;
	}
	public String getUpdateGradeAttendanceScore() {
		return updateGradeAttendanceScore;
	}
	public void setUpdateGradeAttendanceScore(String updateGradeAttendanceScore) {
		this.updateGradeAttendanceScore = updateGradeAttendanceScore;
	}
	public void setSTDseq(String sDTseq) {
		STDseq = sDTseq;
	}
	public String getSTDName() {
		return STDName;
	}
	public void setSTDName(String sTDName) {
		STDName = sTDName;
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
	public String getLectureName() {
		return lectureName;
	}
	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectSeq() {
		return subjectSeq;
	}
	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	
	@Override
	public String toString() {
		
		return "StudentGradeInfo [STDseq=" + STDseq + ", STDName=" + STDName + ", gradeNoteScore="
				+ gradeNoteScore + ", gradeSkillScore=" + gradeSkillScore + ", gradeAttendanceScore=" + gradeAttendanceScore
				+ ", lectureName=" + lectureName + ", subjectName=" + subjectName + "]";
				
	}
	
	
}
