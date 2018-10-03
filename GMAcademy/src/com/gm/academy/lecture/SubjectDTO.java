package com.gm.academy.lecture;

public class SubjectDTO {
	private String subjectSeq;
	private String subjectName;
	private String updatesubjectname;
	
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
	public String getUpdatesubjectName() {
		return updatesubjectname;
	}
	public void setUpdatesubjectName(String updatesubjectname) {
		this.updatesubjectname = updatesubjectname;
	}
	
	@Override
	public String toString() {
		return "Subject [subjectSeq=" + subjectSeq + ", subjectName=" + subjectName + "]";
	}
}
