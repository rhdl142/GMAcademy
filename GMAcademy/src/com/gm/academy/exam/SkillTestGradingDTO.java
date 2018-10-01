package com.gm.academy.exam;

public class SkillTestGradingDTO {
	private String skillQandASeq;
	private String skillQueSeq;
	private String skillAnswerPF;
	private String courseSeq;
	public String getSkillQandASeq() {
		return skillQandASeq;
	}
	public void setSkillQandASeq(String skillQandASeq) {
		this.skillQandASeq = skillQandASeq;
	}
	public String getSkillQueSeq() {
		return skillQueSeq;
	}
	public void setSkillQueSeq(String skillQueSeq) {
		this.skillQueSeq = skillQueSeq;
	}
	public String getSkillAnswerPF() {
		return skillAnswerPF;
	}
	public void setSkillAnswerPF(String skillAnswerPF) {
		this.skillAnswerPF = skillAnswerPF;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "SkillTestGrading [skillQandASeq=" + skillQandASeq + ", skillQueSeq=" + skillQueSeq + ", skillAnswerPF="
				+ skillAnswerPF + ", courseSeq=" + courseSeq + "]";
	}
	
	
}
