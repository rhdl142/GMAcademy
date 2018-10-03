package com.gm.academy.admin;



import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.SubjectDTO;
import com.gm.academy.teacher.TeacherDTO;

public class AdminController {
	
	private UtilScanner scan;
	private UtilPrint out;
	private AdminDAO dao;
	
	public AdminController() {
		this.scan = new UtilScanner();
		this.out = new UtilPrint();
		this.dao = new AdminDAO();
	}


	public void main(String id, String pw) {
		
		
		MainClass.crumb.in("관리자");
		while(true) {
			out.middleTitle("관리자 페이지");
			out.menu(UtilPrint.ADMINMAIN);
			int input = scan.nextInt("선택");			
			
			if(input == 1) {
				TeacherManagente(); //교사 계정 관리 메소드
			}else if(input == 2) {
				//개설 과정 관리///
				while(true) {
					MainClass.crumb.in("개설 과정 관리");
					out.bigTitle("개설 과정 관리");
					out.menu(UtilPrint.ADMIN_LECTURE_MANAGEMENT);
					
					input = scan.nextInt("선택");
					
					if(input == 1) {
						MainClass.crumb.in("과정 현황 조회");
						Admin_LectureCheck();
						MainClass.crumb.out();				
					}else if(input == 2) {
						//과정 등록
						MainClass.crumb.in("과정 등록");
						Admin_LectureRegist();
						MainClass.crumb.out();
					}else if(input == 3) {
						//과정 삭제
						MainClass.crumb.in("과정 삭제");
						Admin_LectureRemove();
						MainClass.crumb.out();
					}else if(input == 4) {
						//과정 수정
						MainClass.crumb.in("과정 수정");
						Admin_LectureUpdate();
						MainClass.crumb.out();
					}else if(input == 5){
						break;
					}else {
						System.out.println("잘못 입력하였습니다. 다시 입력하세요");
						continue;
					}
				}//while
			}else if(input == 3) {
		       	 
	        	 MainClass.crumb.in("개설과목 관리");
	        	 
	        	 out.bigTitle(">>개설과목 관리<<");
	        	 out.menu(UtilPrint.ADMIN_SUBJECTMANAGEMENT);
	        	 
	        	 System.out.println();
	             input = scan.nextInt(">>선택");
	             
	             //선택
	             if(input == 1) {
	            	 
	            	 MainClass.crumb.in("과목 현황 조회");
	            	 subjectlist();		//현재 강의중인 과정의 과목 리스트 출력 메소드
	            	 MainClass.crumb.out();
	            	 
	             }else if(input == 2) {
	            	 
	            	 MainClass.crumb.in("과목 등록");
	            	 addsubject();
	            	 MainClass.crumb.out();
	            	 
	             }else if(input == 3) {
	            	 
	            	 MainClass.crumb.in("과목 삭제");
	            	 deletesubject();
	            	 MainClass.crumb.out();
	            	 
	             }else if(input == 4) {
	            	 
	            	 MainClass.crumb.in("과목 수정");
	            	 updatesubject();
	            	 MainClass.crumb.out();
	            	 
	             }else if(input == 5) {
	            	 out.pause();
	             }
			}else if(input == 4) {
				
			}else if(input == 5) {
				
			}else if(input == 6) {
				
			}else if(input == 7) {
				
			}else if(input == 8) {
				
				MainClass.crumb.out();
				break;
			}
			
			
		}

		
	}

//교사계정관리-------------------------------------------------------------------------------------------------	
	
	private void TeacherManagente() {//교사 계정 관리 메소드
		MainClass.crumb.in("교사 계정 관리");
		while(true) {
			int input;
			out.middleTitle(">>교사 계정 관리<<");
			out.menu(UtilPrint.ADMINTEACHERMANAGEMENT);
			input = scan.nextInt("선택");
			if(input == 1) {
				
				TeacherServed(); //교사 재직 현황
			}else if(input == 2) {
				
				TeacherRegister();// 교사 등록
			}else if(input == 3) {
				
				TeacherRemove();// 교사 삭제
			}else if(input == 4) {
				
				TeacherUpdateMenu();//교사 수정	
			}else if(input == 5) {
					
				MainClass.crumb.out();
				break;
			}

		}
	}
	
	private void TeacherUpdateMenu() { // 수정 세부 메뉴
		MainClass.crumb.in("교사 수정");
		while(true) {
			int input;
			out.middleTitle(">> 교사 수정 <<");
			out.menu(UtilPrint.ADMINTEACHERUPDATEMENU);
			input = scan.nextInt("선택");
			if(input == 1) { // 이름 수정
				TeacherUpdateName();
			}else if(input == 2) { //전화번호 수정
				TeacherUpdateTel();
			}else if(input == 3) { // 돌아가기
				MainClass.crumb.out();
				break;
			}
		}
	}
	
	private void TeacherUpdateTel() { // 전화번호 수정
		MainClass.crumb.in("전화번호 수정");
		out.middleTitle(">> 전화번호 수정 <<");
		out.bar(40);
		TeacherList();
		String tchTelBefor = scan.next("▶전화 번호");
		String tchTelAfter = scan.next("▶변경할 전화 번호");
		
		int result = dao.updateTel(tchTelBefor,tchTelAfter);
		
		out.bar(40);
		out.bar2(40);
		out.result(result,"수정이 완료되었습니다.");
		out.pause();
		MainClass.crumb.out();
	}


	private void TeacherUpdateName() { // 이름 수정
		MainClass.crumb.in("이름 수정");
		out.middleTitle(">> 이름 수정 <<");
		out.bar(40);
		TeacherList();
		String tchNameBefor = scan.next("▶교사 이름");
		String tchNameAfter = scan.next("▶변경할 교사 이름");
		
		int result = dao.updateName(tchNameBefor,tchNameAfter);
		
		out.bar(40);
		out.bar2(40);
		out.result(result,"수정이 완료되었습니다.");
		out.pause();
		MainClass.crumb.out();

	}


	//교사 삭제 메소드
	private void TeacherRemove() {
		
		
		MainClass.crumb.in("교사 삭제");
		out.middleTitle(">> 교사 삭제 <<");
		TeacherList();
		out.bar(40);
		
		String tchId = scan.next("▶ 교사ID");
		String tchName = scan.next("▶ 교사 이름");
		
		
		String sel = scan.next("정말 삭제 하시겠습니까? (y/n)");
		
		if(sel.equals("y")) {
			int result = dao.Remove(tchId,tchName);
			out.result(result,"삭제가 완료되었습니다.");
		} else if(sel.equals("n")) {
			out.result("삭제가 취소 되었습니다.");
		}
		
		out.bar(40);
		out.bar2(40);
		out.pause();
		 MainClass.crumb.out();

	}
	


	//교사 등록 메소드
	private void TeacherRegister() {
		MainClass.crumb.in("교사 등록");
		out.middleTitle(">> 교사 등록 <<");
		TeacherList();
		out.bar(40);
		
		String tchname = scan.next("▶ 이름");
		String tchssn = scan.next("▶ 주민번호(뒷자리)");
		String tchtel = scan.next("▶ 전화번호");
		
		int checkSsn = dao.checkSsn(tchssn);
		int checkTel = dao.checkTel(tchtel);
		
			if(checkSsn > 0) {
				out.result("중복되는 주민번호 입니다. 다시 처음부터 입력해 주세요");
			}
			else if(checkTel > 0){
				out.result("중복되는 전화번호 입니다. 다시 처음부터 입력해 주세요");
			}else {
				int result = dao.Register(tchname,tchssn,tchtel);
				out.result(result,"등록이 완료되었습니다.");
			}
		
		out.bar(40);
		out.bar2(40);
		out.pause();
		 MainClass.crumb.out();

		
	}
	
	//교사 정보 출력
		private void TeacherList() {
			out.bigTitle(">> 교사 리스트 <<");
			out.bar(80);
			ArrayList<TeacherDTO> list = dao.list(MainClass.isAuth != null? true : false);
			
			out.header(new String[]{"교사코드", "교사명", "주민번호(뒷자리)","전화번호" });
			
			for(TeacherDTO teacher : list) {
				out.data(new Object[] {			
												teacher.getTCHSeq()+"\t",
												teacher.getTCHName()+"\t",
												teacher.getTCHSsn()+"\t\t",
												teacher.getTCHTel()
						
												});
			}
			out.bar(80);
			out.bar2(80);
			
		}

	//교사 재직 현황 출력 메소드
	private void TeacherServed() {
		MainClass.crumb.in("교사 조회");
		out.bigTitle(">> 교사 재직 현황 <<");
		out.bar(80);
		ArrayList<TeacherDTO> list = dao.list(MainClass.isAuth != null? true : false);
		
		out.header(new String[]{"교사코드", "교사명", "주민번호(뒷자리)","전화번호" });
		
		for(TeacherDTO teacher : list) {
			out.data(new Object[] {			
											teacher.getTCHSeq()+"\t",
											teacher.getTCHName()+"\t",
											teacher.getTCHSsn()+"\t\t",
											teacher.getTCHTel()
					
											});
		}
		out.bar(80);
		out.bar2(80);
		out.pause();
		MainClass.crumb.out();

	}
	
//----------------------------------------------------------------------------------------------------------
	
//개설과정관리----------------------------------------------------------------------------------------------------------
	
	private void Admin_LectureUpdate() {
		out.bigTitle("과정 수정");
		
		ArrayList<LectureDTO> lecList = dao.LectureList();

		out.header(new String[] { "과정코드", "시작날짜", "종료날짜", "진행여부", "과정 명" });
		for (LectureDTO lec : lecList) {
			out.data(new Object[] { lec.getLectureSeq() + "\t", lec.getLectureStartDate(), lec.getLectureEndDate(),
					lec.getLectureProgress() + "\t", lec.getLectuerName() });
		} // for
		out.line(UtilPrint.LONG);
		
		while(true) {
			
			out.bigTitle("과정 수정");
			out.menu(UtilPrint.ADMIM_LETUREUPDATE);
			int select = scan.nextInt("▶ 선택");

			if (select == 1) {
				// 과정 명 수정
				MainClass.crumb.in("과정 명 수정");
				LectureNameUpdate();
				MainClass.crumb.out();
			} else if (select == 2) {
				// 과정 날짜 수정
				MainClass.crumb.in("과정 날짜 수정");
				LectureDateUpdate();
				MainClass.crumb.out();
			} else if (select == 3) {
				// 강의 진행 여부 수정
				MainClass.crumb.in("강의 진행 여부 수정");
				LectureProgressUpdate();
				MainClass.crumb.out();
			} else if (select == 4) {
				// 학생 수 수정
				MainClass.crumb.in("학생 인원 수정");
				LetureStudentUpdate();
				MainClass.crumb.out();
			} else if (select == 5) {
				// 강의실 번호 수정
				MainClass.crumb.in("강의실 번호 수정");
				LectureClassRoomUpdate();
				MainClass.crumb.out();
			} else if (select == 6) {
				// 교사 코드 수정
				MainClass.crumb.in("교사 코드 수정");
				LectureTeacherUpdate();
				MainClass.crumb.out();
			} else if (select == 7) {
				// 돌아가기
				break;
			}
		}
		
		
	}

	private void LectureTeacherUpdate() {
		
		while (true) {
			out.bigTitle("교사 코드 수정");

			String lectureSeq = scan.next("▶ 수정할 과정 코드 입력 (0 : 돌아가기)");

			if (!lectureSeq.equals("0")) {
				String TCHSeq = scan.next("▶ 수정할 교사 코드 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setTCHSeq(TCHSeq);

				int result = dao.updateTeacher(lecDTO);

				out.result(result, " ■교사 코드 수정 성공■");
				out.pause();
			}else 
				break;
		}
	}


	private void LectureClassRoomUpdate() {
		
		while(true) {
			out.bigTitle("강의실 수정");
			
			String lectureSeq = scan.next("▶ 수정할 과정 코드 입력 (0 : 돌아가기)");
			if(!lectureSeq.equals("0")) {

				String classSeq = scan.next("▶ 수정할 강의실 번호 입력");
				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setClassSeq(classSeq);

				int result = dao.updateClassRoom(lecDTO);

				out.result(result, " ■강의실 수정 성공■");
				out.pause();
			}else
				break;
		}
	}


	private void LetureStudentUpdate() {

		while (true) {
			out.bigTitle("학생 인원 수정");

			String lectureSeq = scan.next("▶ 수정할 과정 코드 입력 (0 : 돌아가기)");

			if (!lectureSeq.equals("0")) {
				String lectureAcceptSTD = scan.next("▶ 수정할 총 인원 입력");
				String lectureCurrentSTD = scan.next("▶ 수정할 현 수강 인원 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setLectureAcceptSTD(lectureAcceptSTD);
				lecDTO.setLectureCurrentSTD(lectureCurrentSTD);

				int result = dao.updateStuedent(lecDTO);
				out.result(result, " ■학생 인원 수정 성공■");
				out.pause();
			}else
				break;
		}
	}

	private void LectureProgressUpdate() {
		while (true) {

			out.bigTitle("강의 진행 여부 수정");

			String lectureSeq = scan.next("▶ 수정할 과정 코드 입력 (0 : 돌아가기)");
			if (!lectureSeq.equals("0")) {
				String lectureProgress = scan.next("▶ 강의 진행 여부 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setLectureProgress(lectureProgress);

				int result = dao.updateLectureProgress(lecDTO);

				out.result(result, " ■강의 진행 여부 수정 성공■");
				out.pause();
			}else
				break;
		}
	}

	private void LectureDateUpdate() {
		while (true) {
			out.bigTitle("과정 날짜 수정");
			String lectureSeq = scan.next("▶ 수정할 과정 코드 입력(0 : 돌아가기)");
			if (!lectureSeq.equals("0")) {
				String lectureStartDate = scan.next("▶ 과정 시작 날짜 입력");
				String lectureEndDate = scan.next("▶ 과정 종료 날짜 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setLectureStartDate(lectureStartDate);
				lecDTO.setLectureEndDate(lectureEndDate);

				int result = dao.updateLectureDate(lecDTO);

				out.result(result, " ■과정 날짜 수정 성공■");
				out.pause();
			}else
				break;
		}

	}

	private void LectureNameUpdate() {

		while (true) {
			out.bigTitle("과정 명 수정");

			String lectureSeq = scan.next("▶ 수정할 과정 코드 입력(0 : 돌아가기)");
			if (!lectureSeq.equals("0")) {
				String lectureName = scan.next("▶ 수정 할 과목 명 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setLectuerName(lectureName);

				int result = dao.updateLectureName(lecDTO);

				out.result(result, " ■과정 명 수정 성공■");
				out.pause();
			}else
				break;
		}
	}


	private void Admin_LectureRemove() {
		ArrayList<LectureDTO> lecList = dao.LectureList();

		out.bigTitle("과정 삭제");
		out.header(new String[] { "과정코드", "시작날짜", "종료날짜", "진행여부", "과정 명" });
		for (LectureDTO lec : lecList) {
			out.data(new Object[] { lec.getLectureSeq() + "\t", lec.getLectureStartDate(), lec.getLectureEndDate(),
					lec.getLectureProgress() + "\t", lec.getLectuerName() });
		} // for
		out.line(UtilPrint.LONG);

		String seq = scan.next("▶ 삭제할 과정 코드 입력(0 : 돌아가기)");
		if (!seq.equals("0")) {
			LectureDTO lecDTO = new LectureDTO();
			lecDTO.setLectureSeq(seq);

			int result = dao.LectureRemove(lecDTO);

			out.result(result, " ■과정 삭제 성공■");
			out.pause();
		} else
			System.out.println("과정 삭제 종료");
		out.pause();
	}


	private void Admin_LectureRegist() {
		out.bigTitle("과정 등록");

		String lectureName = scan.next("▶ 과정 명 (0 : 돌아가기)");
		if (!lectureName.equals("0")) {
			String lectureStartDate = scan.next("▶ 과정 시작 날짜");
			String lectureEndDate = scan.next("▶ 과정 종료 날짜");
			String classSeq = scan.next("▶ 강의실 번호");
			String lectureProgress = scan.next("▶ 강의 진행 여부");
			String lectureAcceptSTD = scan.next("▶ 수강 가능 인원");
			String lectureCurrentSTD = scan.next("▶ 현재 수강 인원");
			String tchSeq = scan.next("▶ 교사 코드");

			// DTO통합
			LectureDTO lecDTO = new LectureDTO();

			lecDTO.setLectuerName(lectureName);
			lecDTO.setLectureStartDate(lectureStartDate);
			lecDTO.setLectureEndDate(lectureEndDate);
			lecDTO.setClassSeq(classSeq);
			lecDTO.setLectureProgress(lectureProgress);
			lecDTO.setLectureAcceptSTD(lectureAcceptSTD);
			lecDTO.setLectureCurrentSTD(lectureCurrentSTD);
			lecDTO.setTCHSeq(tchSeq);

			int result = dao.lectureRegister(lecDTO);

			out.result(result, " ■과정 등록 성공■");
			out.pause();
		}else
			System.out.println("과정 등록 종료");
		out.pause();
	}


	private void Admin_LectureCheck() {

		//리스트 출력
		out.bigTitle("과정 현황 조회");
		ArrayList<LectureDTO> lecList = dao.LectureList();

		out.header(new String[] {"과정코드","시작날짜","종료날짜","진행여부","과정 명"});
		for(LectureDTO lec : lecList) {
			out.data(new Object[] {
					lec.getLectureSeq()+"\t",
					lec.getLectureStartDate(),
					lec.getLectureEndDate(),
					lec.getLectureProgress()+"\t",
					lec.getLectuerName()
			});
		}//for
		out.line(UtilPrint.LONG);
		
		
		
	
		//삭제 코드 입력받기
		String lecSeq = scan.next("▶ 과정 세부 내역 보기(코드 입력)(0 : 돌아가기)");
		if(!lecSeq.equals("0")) {
			
			ArrayList<LectureDTO> lecDetail = dao.LectureDetail(lecSeq);
			out.header(new String[] {"과정코드","진행여부","교사이름","수강인원","강의실","과정 명"});
			for(LectureDTO lec : lecDetail) {
				out.data(new Object[] {
					lec.getLectureSeq()+"\t",
					lec.getLectureProgress()+"\t",
					lec.getTeacherName()+"\t",
					lec.getLectureCurrentSTD()+"\t",
					lec.getClassSeq(),
					lec.getLectuerName()
				});//for
				out.line(UtilPrint.LONG);
			}
			
		}else {
			System.out.println("과정 현황 보기 종료");
		}
		
		out.pause();
		
	}//LectureCheck
//----------------------------------------------------------------------------------------------------------------------
//개설과목관리----------------------------------------------------------------------------------------------------------------------
	private void updatesubject() {
		
		out.middleTitle(">>과목 수정<<");
		
		//과목 수정
		String updatesubjectname = scan.next("수정할 과목명 : ");
		String subjectname = scan.next("기존 과목명 : ");
		
		//DAO 위임 > update
		SubjectDTO subject = new SubjectDTO();
		
		subject.setUpdatesubjectName(updatesubjectname);
		subject.setSubjectName(subjectname);
		
		int result = dao.updatesubject(subject);
		
		out.result("과목을 수정하였습니다.");
		out.pause();
}


	private void deletesubject() {
	
		out.middleTitle(">>과목 삭제<<");
		
		//과목 삭제
		String subjectname = scan.next("삭제할 과목명 : ");
		
		//DAO 위임 > delete
		SubjectDTO subject = new SubjectDTO();
		
		subject.setSubjectName(subjectname);
		
		int result = dao.deletesubject(subject);
		
		out.result(result, "과목을 삭제하였습니다.");
		out.pause();
	
}


	private void addsubject() {
	
		out.middleTitle(">>과목 등록<<");
		
		//과목 등록
		String subjectname = scan.next("등록할 과목명 : ");
		
		//DAO 위임 > insert
		SubjectDTO subject = new SubjectDTO();
		
		subject.setSubjectName(subjectname);
		
		int result = dao.addsubject(subject);
		
		out.result(result, "과목을 등록하였습니다.");
		out.pause();
		
}


	private void subjectlist() {
		
		out.middleTitle(">>과목현황 조회<<");
		
		//과목코드, 과목명 목록 출력
		ArrayList<SubjectDTO> list = dao.subjectlist();
		
		out.header(new String[] {"과목번호", "과목명"});
		
		for(SubjectDTO dto : list) {
			out.data(new Object[] {dto.getSubjectSeq() + "\t", dto.getSubjectName()});
		}
		
		
	}//과목현황 조회

//----------------------------------------------------------------------------------------------------------------------
}
