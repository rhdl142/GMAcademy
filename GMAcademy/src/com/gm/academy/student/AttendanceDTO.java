package com.gm.academy.student;
/**
 * 
 * @author 공통
 *
 * 출결 <tblAttendance>
 */
public class AttendanceDTO {
	private String attendanceSeq;
	private String onTime;
	private String offTime;
	private String absenceSeq;
	private String courseSeq;
	public String getAttendanceSeq() {
		return attendanceSeq;
	}
	public void setAttendanceSeq(String attendanceSeq) {
		this.attendanceSeq = attendanceSeq;
	}
	public String getOnTime() {
		return onTime;
	}
	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}
	public String getOffTime() {
		return offTime;
	}
	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}
	public String getAbsenceSeq() {
		return absenceSeq;
	}
	public void setAbsenceSeq(String absenceSeq) {
		this.absenceSeq = absenceSeq;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "Attendance [attendanceSeq=" + attendanceSeq + ", onTime=" + onTime + ", offTime=" + offTime
				+ ", absenceSeq=" + absenceSeq + ", courseSeq=" + courseSeq + "]";
	}
	
	
}
