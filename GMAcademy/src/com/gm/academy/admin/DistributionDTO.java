package com.gm.academy.admin;

/**
 * 배점 객체
 * @author 3조
 *
 */
public class DistributionDTO {
	private String dstrSeq;
	private String dstrNote;
	private String dstrSkill;
	private String dstrAttendance;
	public String getDstrSeq() {
		return dstrSeq;
	}
	public void setDstrSeq(String dstrSeq) {
		this.dstrSeq = dstrSeq;
	}
	public String getDstrNote() {
		return dstrNote;
	}
	public void setDstrNote(String dstrNote) {
		this.dstrNote = dstrNote;
	}
	public String getDstrSkill() {
		return dstrSkill;
	}
	public void setDstrSkill(String dstrSkill) {
		this.dstrSkill = dstrSkill;
	}
	public String getDstrAttendance() {
		return dstrAttendance;
	}
	public void setDstrAttendance(String dstrAttendance) {
		this.dstrAttendance = dstrAttendance;
	}
	@Override
	public String toString() {
		return "Distribution [dstrSeq=" + dstrSeq + ", dstrNote=" + dstrNote + ", dstrSkill=" + dstrSkill
				+ ", dstrAttendance=" + dstrAttendance + "]";
	}
	
	
}
