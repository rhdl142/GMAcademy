package com.gm.academy.admin;

/**
 * 관리자 로그인 객체
 * @author 3조
 *
 */
public class AdminLogInDTO {
	private String AdminID;
	private String AdminPW;
	public String getAdminID() {
		return AdminID;
	}
	public void setAdminID(String adminID) {
		AdminID = adminID;
	}
	public String getAdminPW() {
		return AdminPW;
	}
	public void setAdminPW(String adminPW) {
		AdminPW = adminPW;
	}
	@Override
	public String toString() {
		return "AdminLogIn [AdminID=" + AdminID + ", AdminPW=" + AdminPW + "]";
	}
	
	
}
