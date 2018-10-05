package com.gm.academy.lecture;

/**
 * 강의실객체
 * @author 3조
 *
 */
public class ClassroomDTO {
	private String classSeq;
	private String className;
	private String classAcceptSTD;
	public String getClassSeq() {
		return classSeq;
	}
	public void setClassSeq(String classSeq) {
		this.classSeq = classSeq;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassAcceptSTD() {
		return classAcceptSTD;
	}
	public void setClassAcceptSTD(String classAcceptSTD) {
		this.classAcceptSTD = classAcceptSTD;
	}
	@Override
	public String toString() {
		return "Classroom [classSeq=" + classSeq + ", className=" + className + ", classAcceptSTD=" + classAcceptSTD
				+ "]";
	}
	
	
}
