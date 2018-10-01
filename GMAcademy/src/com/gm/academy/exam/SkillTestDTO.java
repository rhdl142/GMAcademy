package com.gm.academy.exam;

public class SkillTestDTO {
	private String skillQueSeq;
	private String skillQuestion;
	private String skillDistribution;
	private String subjectSeq;
	public String getSkillQueSeq() {
		return skillQueSeq;
	}
	public void setSkillQueSeq(String skillQueSeq) {
		this.skillQueSeq = skillQueSeq;
	}
	public String getSkillQuestion() {
		return skillQuestion;
	}
	public void setSkillQuestion(String skillQuestion) {
		this.skillQuestion = skillQuestion;
	}
	public String getSkillDistribution() {
		return skillDistribution;
	}
	public void setSkillDistribution(String skillDistribution) {
		this.skillDistribution = skillDistribution;
	}
	public String getSubjectSeq() {
		return subjectSeq;
	}
	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	@Override
	public String toString() {
		return "SkillTest [skillQueSeq=" + skillQueSeq + ", skillQuestion=" + skillQuestion + ", skillDistribution="
				+ skillDistribution + ", subjectSeq=" + subjectSeq + "]";
	}
	
	
}
