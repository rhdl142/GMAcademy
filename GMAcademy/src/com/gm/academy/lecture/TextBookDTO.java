package com.gm.academy.lecture;

/**
 * 교재 객체
 * @author 3조
 *
 */
public class TextBookDTO {
	private String textBookSeq;
	private String textBookName;
	private String textBookWriter;
	private String textBookPrice;
	private String publisherSeq;
	private String subjectSeq;
	public String getTextBookSeq() {
		return textBookSeq;
	}
	public void setTextBookSeq(String textBookSeq) {
		this.textBookSeq = textBookSeq;
	}
	public String getTextBookName() {
		return textBookName;
	}
	public void setTextBookName(String textBookName) {
		this.textBookName = textBookName;
	}
	public String getTextBookWriter() {
		return textBookWriter;
	}
	public void setTextBookWriter(String textBookWriter) {
		this.textBookWriter = textBookWriter;
	}
	public String getTextBookPrice() {
		return textBookPrice;
	}
	public void setTextBookPrice(String textBookPrice) {
		this.textBookPrice = textBookPrice;
	}
	public String getPublisherSeq() {
		return publisherSeq;
	}
	public void setPublisherSeq(String publisherSeq) {
		this.publisherSeq = publisherSeq;
	}
	public String getSubjectSeq() {
		return subjectSeq;
	}
	public void setSubjectSeq(String subjectSeq) {
		this.subjectSeq = subjectSeq;
	}
	@Override
	public String toString() {
		return "TextBook [textBookSeq=" + textBookSeq + ", textBookName=" + textBookName + ", textBookWriter="
				+ textBookWriter + ", textBookPrice=" + textBookPrice + ", publisherSeq=" + publisherSeq
				+ ", subjectSeq=" + subjectSeq + "]";
	}
	
	
}
