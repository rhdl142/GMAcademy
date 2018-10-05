package com.gm.academy.student;
/**
 * 
 * @author 공통
 * 
 * 근태 <tblAbsenceRecord>
 */
public class AbsenceRecordDTO {
	private String absenceSeq;
	private String absenceSituation;
	
	public String getAbsenceSeq() {
		return absenceSeq;
	}
	public void setAbsenceSeq(String absenceSeq) {
		this.absenceSeq = absenceSeq;
	}
	public String getAbsenceSituation() {
		return absenceSituation;
	}
	public void setAbsenceSituation(String absenceSituation) {
		this.absenceSituation = absenceSituation;
	}
	@Override
	public String toString() {
		return "AbsenceRecord [absenceSeq=" + absenceSeq + ", absenceSituation=" + absenceSituation + "]";
	}
}
