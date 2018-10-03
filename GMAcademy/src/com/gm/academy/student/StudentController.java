package com.gm.academy.student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.gm.academy.MainClass;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
import com.gm.academy.exam.GradeDTO;

public class StudentController {
	private UtilScanner scan;
	private UtilPrint out;
	private StudentDAO dao;
	
	public StudentController() {
		this.scan = new UtilScanner();
		this.out = new UtilPrint();
		this.dao = new StudentDAO();
	}
	
	public void main(String id, String pw) {
		check(id,pw);
		while(true) {
			if(MainClass.isAuth == null) {
				break;
			} else {
				out.bigTitle("회원");
				myPage();
				
				out.bar();
				out.menu(UtilPrint.STUDENT_LOGIN);
				int input = scan.nextInt("선택");
				
				if(input == 1) {
					MainClass.crumb.in("성적조회");
					gradeSearch();
					MainClass.crumb.out();
				} else if(input == 2) {
					MainClass.crumb.in("출결");
					Attendance();
					MainClass.crumb.out();					
				} else if(input == 3) {
					MainClass.crumb.in("교사평가");
					teacherEvaluation();
					MainClass.crumb.out();					
				} else if(input == 4) {
					MainClass.crumb.in("상담일지");
					consulting();
					MainClass.crumb.out();					
				} else if(input == 5) {
					break;
				} else {
					out.result("잘못입력하였습니다.");
				}
			}
		}
	}
	
	//상담일지
	private void consulting() {
		while(true) {
			out.bigTitle("상담일지");
			myPage();
			out.bar();
			
			out.menu(UtilPrint.CUNSULTING);
			int input = scan.nextInt("선택");
			
			if(input == 1) {
				MainClass.crumb.in("상담일지 조회");
				ArrayList<CourseRecordDTO> list = dao.consulting();
				
				out.header(new String[] {"등록일","상담내용"});
				
				for(CourseRecordDTO crDTO : list) {
					out.data(new Object[] {crDTO.getCounseRegdate(),crDTO.getCounseContents()});
				}
				
				out.pause();
				
				MainClass.crumb.out();
			} else if(input == 2) {
				break;
			} else {
				out.result("잘못입력하였습니다.");
			}
		}
		out.pause();
	}

	//교사평가
	private void teacherEvaluation() {
		while(true) {
			out.bigTitle("교사평가");
			myPage();
			out.bar();
			
			out.menu(UtilPrint.TEACHER_EVALUATION);
			int input = scan.nextInt("선택");
			
			if(input == 1) {
				MainClass.crumb.in("교사평가 등록");
				teRegister();
				MainClass.crumb.out();
			} else if(input == 2) {
				break;
			} else {
				out.result("잘못입력하였습니다.");
			}
		}
	}

	//교사평가 등록
	private void teRegister() {
		out.bigTitle("교사평가 등록");
		myPage();
		out.bar();
		
		out.menu(UtilPrint.TEREGISTER);
		int input = scan.nextInt("선택");
		
		if(input == 1) {
			MainClass.crumb.in("과정평가");
			lectureEvaluation();
			MainClass.crumb.out();
		} else if(input == 2) {
			MainClass.crumb.in("과목평가");
			subjectEvaluation();
			MainClass.crumb.out();
		} else {
			out.result("잘못입력하였습니다.");
		}
		out.pause();
	}

	private void subjectEvaluation() {
		out.bigTitle("과목평가");
		myPage();
		out.bar();
		
		ArrayList<StudentGradeDTO> list = dao.grade();
		
		out.header(new String[] {"과목코드","과목명"});
		
		for(StudentGradeDTO sgDTO : list) {
			out.data(new Object[] {sgDTO.getLecSubseq(),sgDTO.getSubjectName()});
		}
		
		String name = scan.next("과목코드");
		String score = scan.next("점수");
		String command = scan.next("코멘트");
		
		int result = dao.subjectEvaluation(name,score,command);
		
		out.result(result,"과정평가를 완료했습니다.");
		out.pause();
	}

	private void lectureEvaluation() {
		out.bigTitle("과정평가");
		myPage();
		out.bar();
		
		String score = scan.next("점수");
		String command = scan.next("코멘트");
		
		int result = dao.lectureEvaluation(score,command);
		
		out.result(result,"과정평가를 완료했습니다.");
		out.pause();
	}

	//출결
	private void Attendance() {
		while(true) {
			int cate = 0;
			
			out.bigTitle("학생_출결");
			myPage();
			out.bar();
			
			out.menu(UtilPrint.STUDENT_ATTENDACE);
			int input = scan.nextInt("선택");
			
			if(input == 1) {
				MainClass.crumb.in("입실등록");
				cate = 0;
				inOutRegister(cate);
				MainClass.crumb.out();
			} else if(input == 2) {
				MainClass.crumb.in("퇴실등록");
				cate = 1;
				inOutRegister(cate);
				MainClass.crumb.out();
			} else if(input == 3) {
				MainClass.crumb.in("출결현황");
				attendanceStatus();
				MainClass.crumb.out();				
			} else if(input == 4) {
				break;
			} else {
				out.result("잘못입력하였습니다.");
			}
		}
		out.pause();
	}

	//출결현황
	private void attendanceStatus() {
		out.bigTitle("출결현황");
		myPage();
		out.bar();
		
		ArrayList<AbsenceRecordType> list = dao.attendanceStatus();
		
		out.header(new String[] {"전체","정상","지각","조퇴","외출","병가","기타"});
		
		for(AbsenceRecordType artDTO : list) {
			int total = Integer.parseInt(artDTO.getTypeA())
					+Integer.parseInt(artDTO.getTypeB())
					+Integer.parseInt(artDTO.getTypeC())
					+Integer.parseInt(artDTO.getTypeD())
					+Integer.parseInt(artDTO.getTypeE())
					+Integer.parseInt(artDTO.getTypeF());
			
			out.data(new Object[] {total,
					artDTO.getTypeA(),
					artDTO.getTypeB(),
					artDTO.getTypeC(),
					artDTO.getTypeD(),
					artDTO.getTypeE(),
					artDTO.getTypeF()});
			
			System.out.println("[출석룰]");
			System.out.println(Integer.parseInt(artDTO.getTypeA())/total * 100);
		}
		out.pause();
	}

	//입실등록
	private void inOutRegister(int cate) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String today = date.format(c.getTime());

		int result = dao.inOutRegister(today,cate);
		
		out.result(result,String.format("%s 입실등록 완료 되었습니다.", today));
		
		out.pause();
	}

	//성적 조회
	private void gradeSearch() {
		while(true)	 {
			out.bigTitle("성적조회");
			myPage();
			out.bar();
			
			out.menu(UtilPrint.STUDNET_GRADE);
			int input = scan.nextInt("선택");
			
			ArrayList<StudentGradeDTO> list = dao.grade();
			
			if(input == 1) {
				MainClass.crumb.in("필기 성적 조회");
				out.bigTitle("필기 성적 조회");
				
				out.header(new String[] {"필기점수","과목명"});
				
				for(StudentGradeDTO sgDTO : list) {
					out.data(new Object[] {sgDTO.getGradeNoteScore()
							,sgDTO.getSubjectName()});
				}
				
				out.pause();
				MainClass.crumb.out();
			} else if(input == 2) {
				MainClass.crumb.in("실기 성적 조회");
				out.bigTitle("실기 성적 조회");
				
				out.header(new String[] {"실기점수","과목명"});
				
				for(StudentGradeDTO sgDTO : list) {
					out.data(new Object[] {sgDTO.getGradeSkillScore()
							,sgDTO.getSubjectName()});
				}
				
				out.pause();
				MainClass.crumb.out();
			} else if(input == 3) {
				MainClass.crumb.in("출결 성적 조회");
				out.bigTitle("출결 성적 조회");
				
				out.header(new String[] {"출결점수","과목명"});
				
				for(StudentGradeDTO sgDTO : list) {
					out.data(new Object[] {sgDTO.getGradeAttendanceScore()
							,sgDTO.getSubjectName()});
				}
				
				out.pause();
				MainClass.crumb.out();
			} else if(input == 4) {
				MainClass.crumb.in("전체 성적 조회");
				out.bigTitle("전체 성적 조회");
				
				out.header(new String[] {"전체점수","과목명"});
				
				for(StudentGradeDTO sgDTO : list) {
					out.data(new Object[] {(int)((Float.parseFloat(sgDTO.getGradeNoteScore()) * 0.3)
							+ (Float.parseFloat(sgDTO.getGradeSkillScore()) * 0.3)
							+ (Float.parseFloat(sgDTO.getGradeAttendanceScore()) * 0.3)) + "점"
							,sgDTO.getSubjectName()});
				}
				
				out.pause();
				MainClass.crumb.out();
			} else if(input == 5) {
				break;
			} else {
				out.result("잘못입력하였습니다.");
			}
		}
		out.pause();
	}

	//로그인 인증
	private void check(String id, String pw) {
		StudentLogInDTO slDTO = new StudentLogInDTO();
		
		slDTO.setSTDID(id);
		slDTO.setSTDPW(pw);
		
		StduentLectureDTO sltDTO = dao.auth(slDTO);
		
		if(sltDTO != null) {
			MainClass.isAuth = sltDTO.getStudentSeq();
			MainClass.name = sltDTO.getStudentName();
			MainClass.tel = sltDTO.getStudentTel();
			MainClass.lectureName = sltDTO.getLectureName();
			MainClass.lectureDate = sltDTO.getLectureStartDate() + "~" + sltDTO.getLectureEndDate();
			MainClass.lectureSeq = sltDTO.getLectureSeq();
			MainClass.tchSeq = sltDTO.getTchSeq();
			MainClass.courseSeq = sltDTO.getCourseSeq();
		} else {
			MainClass.isAuth = null;
			MainClass.name = null;
			MainClass.tel = null;	
			MainClass.lectureName = null;
			MainClass.lectureDate = null;
		}
		
		out.result(sltDTO != null ? 1:0,"인증에 성공했습니다.");
		out.pause();
	}
	
	//항시출력하는 마이페이지
	private void myPage() {
		System.out.printf("[회원번호] : %s\n",MainClass.isAuth);
		System.out.printf("[이름] : %s\n",MainClass.name);
		System.out.printf("[전화번호] : %s\n",MainClass.tel);
		System.out.printf("[과정명] : %s\n",MainClass.lectureName);
		System.out.printf("[과정기간] : %s\n",MainClass.lectureDate);
	}
//------------------------------------------------------------------------------------------
//ID/PW찾기------------------------------------------------------------------------------------------
	/**
	 * 교육생 ID/PW찾기
	 * 
	 * @param name 이름
	 * @param telCode 전화번호 및 코드
	 * @param cate ID/PW찾기 구분
	 */
	public void search(String name, String telCode, int cate) {
		StudentDTO sDTO = new StudentDTO();
		
		if(cate == 1) { 
			sDTO.setCate(1);
			sDTO.setSTDName(name);
			sDTO.setSTDTel(telCode);
			dao.search(sDTO);
			out.header(new String[] {"[이름]","[전화번호]","[코드]"});
			out.data(new Object[] {sDTO.getSTDName()
					+ sDTO.getSTDTel()
					+ sDTO.getSTDSeq()});
		} else {
			sDTO.setCate(2);
			sDTO.setSTDName(name);
			sDTO.setSTDSeq(telCode);
			dao.search(sDTO);
			out.header(new String[] {"[이름]","[전화번호]","[비밀번호]"});
			out.data(new Object[] {sDTO.getSTDName()
					+ sDTO.getSTDTel()
					+ sDTO.getSTDSsn()});
		}
		
		out.result(sDTO != null ? 1:0,"ID/PW찾기에 성공했습니다.");
	}
}
