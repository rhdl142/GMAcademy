package com.gm.academy.teacher;

import java.util.ArrayList;

import com.gm.academy.admin.DistributionDTO;
import com.gm.academy.lecture.LectureDAO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.SubjectDTO;

public class TeacherUtil {
	public static TeacherDTO loginTeacher = null;
	public static String loginTchSeq = null;
	public static DistributionDTO distribution = null;
	

	public static ArrayList<LectureDTO> getLectureDTO() {
		ArrayList<LectureDTO> array = new ArrayList<LectureDTO>();
		LectureDAO dao = new LectureDAO();
		array = dao.getLectureDTO();
		return array;
	}

	public static void setDistribution() {
		TeacherDAO dao = new TeacherDAO();
		TeacherUtil.distribution = dao.getDistribution();
	}

	public static void myPage() {
		TeacherDAO tdao = new TeacherDAO();
		ArrayList<LectureDTO> list = TeacherUtil.getLectureDTO();
		LectureDTO tchLectureDTO = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTCHSeq().equals(TeacherUtil.loginTeacher.getTCHSeq())) {
				tchLectureDTO = list.get(i);
				break;
			}
		}
		System.out.println("[이름] : " + TeacherUtil.loginTeacher.getTCHName());
		System.out.println(
				"[담당 과정명](과정번호) : " + tchLectureDTO.getLectuerName() + "(" + tchLectureDTO.getLectuerName() + ")");
		System.out.println("[과정기간] : "+tchLectureDTO.getLectureStartDate()+" ~ "+
				tchLectureDTO.getLectureEndDate());
		System.out.println("[담당 과목명](과목번호)");
		ArrayList<SubjectDTO> slist = tdao.getSubjectAndSeqList(TeacherUtil.loginTeacher.getTCHSeq()); 
		for(int i =0; i<slist.size(); i++) {
			System.out.println(slist.get(i).getSubjectName()+"("+slist.get(i).getSubjectSeq()+")");
		}
	}

}
