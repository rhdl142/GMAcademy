package com.gm.academy.teacher;

public class SubjectEvaluationDTO {
	private String evalsubSeq;
	private String TCHSeq;
	private String evalsubScore;
	private String evalsubComment;
	private String LecSubSeq;
	private String courseSeq;
	public String getEvalsubSeq() {
		return evalsubSeq;
	}
	public void setEvalsubSeq(String evalsubSeq) { 
		this.evalsubSeq = evalsubSeq;
	}
	public String getTCHSeq() {
		return TCHSeq;
	}
	public void setTCHSeq(String tCHSeq) {
		TCHSeq = tCHSeq;
	}
	public String getEvalsubScore() {
		return evalsubScore;
	}
	public void setEvalsubScore(String evalsubScore) {
		this.evalsubScore = evalsubScore;
	}
	public String getEvalsubComment() {
		return evalsubComment;
	}
	public void setEvalsubComment(String evalsubComment) {
		this.evalsubComment = evalsubComment;
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
		return "SubjectEvaluation [evalsubSeq=" + evalsubSeq + ", TCHSeq=" + TCHSeq + ", evalsubScore=" + evalsubScore
				+ ", evalsubComment=" + evalsubComment + ", LecSubSeq=" + LecSubSeq + ", courseSeq=" + courseSeq + "]";
	}
	
	
	
}
