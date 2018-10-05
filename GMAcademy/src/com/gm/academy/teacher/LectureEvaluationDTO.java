package com.gm.academy.teacher;
/**
 * 
 * @author 공동
 *
 * 교사과정_평가 <tblLectureEvaluation>
 */
public class LectureEvaluationDTO {
	private String evalLecSeq;
	private String TCHSeq;
	private String evalLecScore;
	private String evalLecCommnet;
	private String lectureSeq;
	private String courseSeq;
	
	public String getEvalLecSeq() {
		return evalLecSeq;
	}
	public void setEvalLecSeq(String evalLecSeq) {
		this.evalLecSeq = evalLecSeq;
	}
	public String getTCHSeq() {
		return TCHSeq;
	}
	public void setTCHSeq(String tCHSeq) {
		TCHSeq = tCHSeq;
	}
	public String getEvalLecScore() {
		return evalLecScore;
	}
	public void setEvalLecScore(String evalLecScore) {
		this.evalLecScore = evalLecScore;
	}
	public String getEvalLecCommnet() {
		return evalLecCommnet;
	}
	public void setEvalLecCommnet(String evalLecCommnet) {
		this.evalLecCommnet = evalLecCommnet;
	}
	public String getLectureSeq() {
		return lectureSeq;
	}
	public void setLectureSeq(String lectureSeq) {
		this.lectureSeq = lectureSeq;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	
	@Override
	public String toString() {
		return "LectureEvaluation [evalLecSeq=" + evalLecSeq + ", TCHSeq=" + TCHSeq + ", evalLecScore=" + evalLecScore
				+ ", evalLecCommnet=" + evalLecCommnet + ", lectureSeq=" + lectureSeq + ", courseSeq=" + courseSeq
				+ "]";
	}
	
	
}
