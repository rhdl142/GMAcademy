package com.gm.academy.teacher;

/**
 * 우에에엑
 * @author 3조
 *
 */
public class PossibleLecDTO {
	private String lecSeq;
	private String subjectSeq;
	private String TCHSeq;
	public String getLecSeq() {
		return lecSeq;
	}
	public void setLecSeq(String lecSeq) {
		this.lecSeq = lecSeq;
	}
	public String getSubjectSeq() {
		return subjectSeq;
	}
	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	public String getTCHSeq() {
		return TCHSeq;
	}
	public void setTCHSeq(String tCHSeq) {
		TCHSeq = tCHSeq;
	}
	@Override
	public String toString() {
		return "PossibleLec [lecSeq=" + lecSeq + ", subjectSeq=" + subjectSeq + ", TCHSeq=" + TCHSeq + "]";
	}
	
	
}
