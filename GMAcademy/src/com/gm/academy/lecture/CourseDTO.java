package com.gm.academy.lecture;

/**
 * 과정객체
 * @author 3조
 *
 */
public class CourseDTO {
	private String courseSeq;
	private String STDSeq;
	private String lectureSeq;
	public String getCourseSeq() {
		return courseSeq;
	}
	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}
	public String getSTDSeq() {
		return STDSeq;
	}
	public void setSTDSeq(String sTDSeq) {
		STDSeq = sTDSeq;
	}
	public String getLectureSeq() {
		return lectureSeq;
	}
	public void setLectureSeq(String lectureSeq) {
		this.lectureSeq = lectureSeq;
	}
	@Override
	public String toString() {
		return "Course [courseSeq=" + courseSeq + ", STDSeq=" + STDSeq + ", lectureSeq=" + lectureSeq + "]";
	}
	
	
}
