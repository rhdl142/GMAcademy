package com.gm.academy.admin;
/**
 * 
 * @author 임광민
 * 
 * 관리자_교재관리
 *
 */
public class TextBookManagementDTO {
	private String subjectName;
	private String textBookWriter;
	private String textBookName;
	private String textBookPrice;
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getTextBookWriter() {
		return textBookWriter;
	}
	public void setTextBookWriter(String textBookWriter) {
		this.textBookWriter = textBookWriter;
	}
	public String getTextBookName() {
		return textBookName;
	}
	public void setTextBookName(String textBookName) {
		this.textBookName = textBookName;
	}
	public String getTextBookPrice() {
		return textBookPrice;
	}
	public void setTextBookPrice(String textBookPrice) {
		this.textBookPrice = textBookPrice;
	}
	@Override
	public String toString() {
		return "TextBookManagementDTO [subjectName=" + subjectName + ", textBookWriter=" + textBookWriter
				+ ", textBookName=" + textBookName + ", textBookPrice=" + textBookPrice + "]";
	}
}
