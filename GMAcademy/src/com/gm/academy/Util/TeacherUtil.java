package com.gm.academy.Util;

import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.admin.DistributionDTO;
import com.gm.academy.lecture.LectureDAO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.SubjectDTO;
import com.gm.academy.teacher.TeacherDAO;
import com.gm.academy.teacher.TeacherDTO;

/**
 * 교사측 유틸 
 * @author 3조
 *
 */
public class TeacherUtil {
	/**
	 * 
	 */
	public static TeacherDTO loginTeacher = null;
	public static String loginTchSeq = null;
	public static DistributionDTO distribution = null;

	/**
	 * 과정배열을 반환하는 메소드
	 * 
	 * @return 과정
	 */
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
		try {
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}System.out.println("[이름] : " + TeacherUtil.loginTeacher.getTCHName());
			
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}System.out.println(
					"[담당 과정명](과정번호) : " + tchLectureDTO.getLectuerName() + "(" + tchLectureDTO.getLectuerName() + ")");
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}System.out.println(
					"[과정기간] : " + tchLectureDTO.getLectureStartDate() + " ~ " + tchLectureDTO.getLectureEndDate());
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}System.out.println("[담당 과목명](과목번호)");
			ArrayList<SubjectDTO> slist = tdao.getSubjectAndSeqList(TeacherUtil.loginTeacher.getTCHSeq());
			for (int i = 0; i < slist.size(); i++) {
				for(int j =0 ; j<30; j++) {
					System.out.print(" ");
				}System.out.println(slist.get(i).getSubjectName() + "(" + slist.get(i).getSubjectSeq() + ")");
			}
		} catch (Exception e) {
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}System.out.println(
					"[담당 과정명](과정번호) : 진행중인 과정이 없습니다." );
		}
	}

}
