package com.gm.academy.lecture;

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
