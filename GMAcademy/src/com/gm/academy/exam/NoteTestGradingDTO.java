package com.gm.academy.exam;

public class NoteTestGradingDTO {
	private String noteQandASeq;
	private String noteQueSeq;
	private String noteAnswerPF;
	private String courseSeq;
	public String getNoteQandASeq() {
		return noteQandASeq;
	}
	public void setNoteQandASeq(String noteQandASeq) {
		this.noteQandASeq = noteQandASeq;
	}
	public String getNoteQueSeq() {
		return noteQueSeq;
	}
	public void setNoteQueSeq(String noteQueSeq) {
		this.noteQueSeq = noteQueSeq;
	}
	public String getNoteAnswerPF() {
		return noteAnswerPF;
	}
	public void setNoteAnswerPF(String noteAnswerPF) {
		this.noteAnswerPF = noteAnswerPF;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "NoteTestGrading [noteQandASeq=" + noteQandASeq + ", noteQueSeq=" + noteQueSeq + ", noteAnswerPF="
				+ noteAnswerPF + ", courseSeq=" + courseSeq + "]";
	}
	
	
}
