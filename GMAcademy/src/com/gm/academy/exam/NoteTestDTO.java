package com.gm.academy.exam;

public class NoteTestDTO {
	private String noteQueSeq;
	private String noteQuestion;
	private String noteDistribution;
	private String subjectSeq;
	public String getNoteQueSeq() {
		return noteQueSeq;
	}
	public void setNoteQueSeq(String noteQueSeq) {
		this.noteQueSeq = noteQueSeq;
	}
	public String getNoteQuestion() {
		return noteQuestion;
	}
	public void setNoteQuestion(String noteQuestion) {
		this.noteQuestion = noteQuestion;
	}
	public String getNoteDistribution() {
		return noteDistribution;
	}
	public void setNoteDistribution(String noteDistribution) {
		this.noteDistribution = noteDistribution;
	}
	public String getSubjectSeq() {
		return subjectSeq;
	}
	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	@Override
	public String toString() {
		return "NoteTest [noteQueSeq=" + noteQueSeq + ", noteQuestion=" + noteQuestion + ", noteDistribution="
				+ noteDistribution + ", subjectSeq=" + subjectSeq + "]";
	}
	
	
}
