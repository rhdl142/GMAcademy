package com.gm.academy.admin;

public class RecommendCompanyDTO {
	private String RecCompanySeq;
	private String RecCompanyName;
	private String RecCompanyLocation;
	private String RecCompanyPayment;
	public String getRecCompanySeq() {
		return RecCompanySeq;
	}
	public void setRecCompanySeq(String recCompanySeq) {
		RecCompanySeq = recCompanySeq;
	}
	public String getRecCompanyName() {
		return RecCompanyName;
	}
	public void setRecCompanyName(String recCompanyName) {
		RecCompanyName = recCompanyName;
	}
	public String getRecCompanyLocation() {
		return RecCompanyLocation;
	}
	public void setRecCompanyLocation(String recCompanyLocation) {
		RecCompanyLocation = recCompanyLocation;
	}
	public String getRecCompanyPayment() {
		return RecCompanyPayment;
	}
	public void setRecCompanyPayment(String recCompanyPayment) {
		RecCompanyPayment = recCompanyPayment;
	}
	@Override
	public String toString() {
		return "RecommendCompany [RecCompanySeq=" + RecCompanySeq + ", RecCompanyName=" + RecCompanyName
				+ ", RecCompanyLocation=" + RecCompanyLocation + ", RecCompanyPayment=" + RecCompanyPayment + "]";
	}
	
	
}
