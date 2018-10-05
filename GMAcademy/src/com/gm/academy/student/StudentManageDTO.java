package com.gm.academy.student;
/**
 * 
 * @author 공동
 *
 * 사후처리 <tblStudentManager>
 */
public class StudentManageDTO {
	private String ManageSeq;
	private String companyName;
	private String courseSeq;
	public String getManageSeq() {
		return ManageSeq;
	}
	public void setManageSeq(String manageSeq) {
		ManageSeq = manageSeq;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	@Override
	public String toString() {
		return "StudentManage [ManageSeq=" + ManageSeq + ", companyName=" + companyName + ", courseSeq=" + courseSeq
				+ "]";
	}
	
	
}
