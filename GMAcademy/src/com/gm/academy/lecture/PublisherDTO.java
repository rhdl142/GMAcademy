package com.gm.academy.lecture;

/**
 * 출판사 객체
 * @author 3조
 *
 */
public class PublisherDTO {
	private String publisherSeq;
	private String publisherName;
	public String getPublisherSeq() {
		return publisherSeq;
	}
	public void setPublisherSeq(String publisherSeq) {
		this.publisherSeq = publisherSeq;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	@Override
	public String toString() {
		return "Publisher [publisherSeq=" + publisherSeq + ", publisherName=" + publisherName + "]";
	}
	
	
}
