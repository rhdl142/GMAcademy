package com.gm.academy.admin;



import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
import com.gm.academy.exam.NoteTestDTO;
import com.gm.academy.exam.SkillTestDTO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.PublisherDTO;
import com.gm.academy.lecture.SubjectDTO;
import com.gm.academy.lecture.TextBookDTO;
import com.gm.academy.student.CourseRecordDTO;
import com.gm.academy.student.StudentDTO;
import com.gm.academy.student.StudentManageDTO;
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
		check(id, pw);
		
		MainClass.crumb.in("관리자");
		while(true) {
			if(MainClass.isAuth == null) {
				break;
			} else {
				out.bigTitle("관리자 페이지");
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
					studentMange(); 
				}else if(input == 5) {
					MainClass.crumb.in("시험관리 및 성적조회");

					out.bigTitle(">>시험관리 및 성적조회<<");
					out.menu(UtilPrint.ADMIN_TESTMANAGEMENT);

					System.out.println();
					input = scan.nextInt(">>선택");

					// 선택
					if (input == 1) {

						MainClass.crumb.in("과정 별 시험 성적 조회");
						Admin_ScoreList();
						MainClass.crumb.out();

					} else if (input == 2) {

						MainClass.crumb.in("시험정보 수정하기");
						updateTest();
						MainClass.crumb.out();

					} else if (input == 3) {

						MainClass.crumb.in("학생별 성적조회");
						studentTestInfo();
						MainClass.crumb.out();

					} else if (input == 4) {

						MainClass.crumb.in("성적 수정하기");
						updateGrade();
						MainClass.crumb.out();

					} else if (input == 5) {
						out.pause();
					}

					MainClass.crumb.out();
				}else if(input == 6) {
					while (true) {
						out.bigTitle("출결관리 및 출결조회");
						out.menu(new String[] { "과정별 출결조회", "기간별 출결조회", "학생 출결조회", "출결 수정하기", "돌아가기" });
						input = 0;
						try {
							input = scan.nextInt("선택");
						} catch (Exception e) {
							out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
						}
						if (input == 1) {
							// 과정별 출결조회
							MainClass.crumb.in("과정별 출결조회");
							showAttendanceByLecture();
							MainClass.crumb.out();
						} else if (input == 2) {
							// 기간별 출결조회
							MainClass.crumb.in("기간별 출결조회");
							showAttendanceByDay();
							MainClass.crumb.out();
						} else if (input == 3) {
							// 학생 출결조회
							MainClass.crumb.in("학생 출결조회");
							showAttendanceByStudent();
							MainClass.crumb.out();
						} else if (input == 4) {
							// 출결 수정하기
							MainClass.crumb.in("출결 수정하기");
							updateAttendance();
							MainClass.crumb.out();
						} else {
							// 돌아가기
							break;
						}
					}				
				}else if(input == 7) {
					MainClass.crumb.in("교재관리");
					textBookManagement();
					MainClass.crumb.out();
				}else if(input == 8) {
					break;
				}
				
				
			}
		}
		
	}

//로그인--------------------------------------------------------------------------------------------	
		//로그인
		private void check(String id, String pw) {
			AdminLogInDTO alDTO = new AdminLogInDTO();
			
			alDTO.setAdminID(id);
			alDTO.setAdminPW(pw);
			
			int result = Integer.parseInt(dao.auth(alDTO));
			
			if(result == 0) {
				MainClass.isAuth = null;
			} else {
				MainClass.isAuth = alDTO.getAdminID();
			}
		
			out.result(result,"인증에 성공했습니다.");
			out.pause();
		}
//------------------------------------------------------------------------------------------------------------------------------------------
//교사계정관리-------------------------------------------------------------------------------------------------	
	
	private void TeacherManagente() {//교사 계정 관리 메소드
		MainClass.crumb.in("교사 계정 관리");
		while(true) {
			int input;
			out.bigTitle(">>교사 계정 관리<<");
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
			out.bigTitle(">> 교사 수정 <<");
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
		out.bigTitle(">> 전화번호 수정 <<");
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
		out.bigTitle(">> 이름 수정 <<");
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
		out.bigTitle(">> 교사 삭제 <<");
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
		out.bigTitle(">> 교사 등록 <<");
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
		
		out.bigTitle(">>과목 수정<<");
		
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
	
		out.bigTitle(">>과목 삭제<<");
		
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
	
		out.bigTitle(">>과목 등록<<");
		
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
		
		out.bigTitle(">>과목현황 조회<<");
		
		//과목코드, 과목명 목록 출력
		ArrayList<SubjectDTO> list = dao.subjectlist();
		
		out.header(new String[] {"과목번호", "과목명"});
		
		for(SubjectDTO dto : list) {
			out.data(new Object[] {dto.getSubjectSeq() + "\t", dto.getSubjectName()});
		}
		
		
	}//과목현황 조회

//----------------------------------------------------------------------------------------------------------------------
//교육생관리----------------------------------------------------------------------------------------------------------------------
	private void studentMange() {
		
		while(true) {
			
			int input;
			
			MainClass.crumb.in("교육생관리");
			
			out.bigTitle("교육생 관리");
			out.menu(UtilPrint.ADMINSTUDENT);
			
			input = scan.nextInt("선택");
			MainClass.crumb.out();
			if(input == 1) {
				
				MainClass.crumb.in("교육생조회");
				out.bigTitle("교육생 조회");
				list();
				String seq = scan.next("과정 선택");
				
				if(seq != "0") {
					String name = scan.next("이름");
				
					stdFind(seq,name);//해당 과정 교육생 찾기
				}

				out.pause();
				MainClass.crumb.out();
				
			}else if(input == 2) {
				
				MainClass.crumb.in("교육생등록");
				out.bigTitle("교육생 등록");
				insertList();
				String seq = scan.next("과정 선택");
				stdInsert(seq);
				MainClass.crumb.out();
				
			}else if(input == 3) {
				MainClass.crumb.in("교육생삭제");
				out.bigTitle("교육생 삭제");
				list();
				String seq = scan.next("과정 선택");
				
				if(seq != "0") {
					String name = scan.next("이름");
					
					stdFind(seq,name);//해당 과정 교육생 찾기
					stdDelete(name);//해당 과정 교육생 삭제
				}
				MainClass.crumb.out();
				
			}else if(input == 4) {
				MainClass.crumb.in("교육생수정");
				out.bigTitle("교육생 수정");
				stdUpdate();
				MainClass.crumb.out();
			}else if(input == 5) {
				MainClass.crumb.in("상담일지 관리");
				counseling();
				MainClass.crumb.out();
			}else if(input == 6) {
				MainClass.crumb.in("사후 관리");
				
				oversight();
				MainClass.crumb.out();
			}else if(input == 7) {
				MainClass.crumb.out();
				break;
				
			}
			
			
			
			
			
		}
		
	}



	private void oversight() {	//사후 관리
		
		while(true) {
			out.bigTitle("사후 관리");
			out.menu(out.OVERSIGHT);
			
			System.out.println();
			int input = scan.nextInt("선택");
			System.out.println();
			
			if(input == 1) {
				selectOversight();
			}else if (input == 2) {
				insertOversight();
			}else if (input == 3) {
				deleteOversight();
			}else if (input == 4) {
				updateOversight();				
			}else if (input == 5) {
				MainClass.crumb.out();
				break;
			}
		}
		
	}



	private void updateOversight() {

		list();
		
		String input = scan.next("선택");
		
		stdList(input);
		
		
		String seq = scan.next("학생 코드 입력");
		
		seq = "ST" + seq.substring(2);
		
		
		vwStudentDTO std = dao.vwfind2(input,seq);
		
		
		int cnt = dao.OversightCnt(std.getLectureseq(),std.getStdseq());
		
		
		if(cnt == 1) {
			StudentManageDTO dto = dao.selectStdManage(std.getLectureseq(),std.getStdseq());
			
			System.out.printf("[%s]\n\n[학생 코드]\t[학생명]\n\n%s\t\t%s\n\n[회사명]\n\n%s\n\n"
					,std.getLecturename()
					,std.getStdseq()
					,std.getStdname()
					,dto.getCompanyName()
						);
			
			
			String company = scan.next("수정 회사명");
			
			
			if(!company.equals(dto.getCompanyName())) {
				
				
				int result = dao.updateOversight(std.getStdseq(),company);
			
				if(result > 0) {
					System.out.println("\n-> 수정 완료");					
				}else {
					System.out.println("\n-> 수정 실패");
				}
				
			}
			
			
		}else if(cnt == 0) {
			System.out.println("찾으시는 자료는 없습니다.");
			out.pause();
		}
		
		
	}


	private void selectOversight() {

		list();
		
		String input = scan.next("선택");
		
		stdList(input);
		
		
		String seq = scan.next("학생 코드 입력");
		
		seq = "ST" + seq.substring(2);
		
		
		vwStudentDTO std = dao.vwfind2(input,seq);
		
		
		int cnt = dao.OversightCnt(std.getLectureseq(),std.getStdseq());
		
		
		if(cnt == 1) {
			StudentManageDTO dto = dao.selectStdManage(std.getLectureseq(),std.getStdseq());
			
			System.out.printf("[%s]\n\n[학생 코드]\t[학생명]\n\n%s\t\t%s\n\n[회사명]\n\n%s\n\n"
					,std.getLecturename()
					,std.getStdseq()
					,std.getStdname()
					,dto.getCompanyName()
						);
			
			out.pause();
			
		}else if(cnt == 0) {
			System.out.println("찾으시는 자료는 없습니다.");
			out.pause();
		}
		
		
	}
	
	
	private void insertOversight() {

		list();
		
		String input = scan.next("선택");
		
		stdList(input);
		
		
		String seq = scan.next("학생 코드 입력");
		
		seq = "ST" + seq.substring(2);
		
		
		vwStudentDTO std = dao.vwfind2(input,seq);
		
		
		int cnt = dao.OversightCnt(std.getLectureseq(),std.getStdseq());
		
		
		if(cnt == 0) {
			StudentManageDTO dto = new StudentManageDTO();
			
			String company = scan.next("회사 이름");
			
			dto.setCompanyName(company);
			dto.setCourseSeq(seq.substring(2));
			
			int result = dao.insertOversight(dto);
			
			if(result > 0) {

				
				System.out.println("\n-> 입력 성공\n\n");
				
				System.out.printf("[%s]\n\n[학생 코드]\t[학생명]\n\n%s\t\t%s\n\n[회사명]\n\n%s\n\n"
						,std.getLecturename()
						,std.getStdseq()
						,std.getStdname()
						,dto.getCompanyName()
							);
				
				out.pause();
				
			} else {
				System.out.println("\n-> 입력 실패");
				
				out.pause();
			}
			
		}else if(cnt == 1) {
			System.out.println("이미 처리된 정보 입니다.");
			out.pause();
		}
		
		
	}
	
	private void deleteOversight() {

		list();
		
		String input = scan.next("선택");
		
		stdList(input);
		
		
		String seq = scan.next("학생 코드 입력");
		
		seq = "ST" + seq.substring(2);
		
		
		vwStudentDTO std = dao.vwfind2(input,seq);
		
		
		int cnt = dao.OversightCnt(std.getLectureseq(),std.getStdseq());
		
		
		if(cnt == 1) {
			StudentManageDTO dto = dao.selectStdManage(std.getLectureseq(),std.getStdseq());
			
			System.out.printf("[%s]\n\n[학생 코드]\t[학생명]\n\n%s\t\t%s\n\n[회사명]\n\n%s\n\n"
					,std.getLecturename()
					,std.getStdseq()
					,std.getStdname()
					,dto.getCompanyName()
						);
			
			out.pause();

			String answer = scan.next("해당 정보를 삭제 하시겠습니까?(Y/N)");
			
			answer = answer.toUpperCase();
			
			if(answer.equals("Y")) {
				
				int result = dao.deleteOversight(std.getStdseq());
				
				if(result > 0) {
					
					System.out.println("\n-> 삭제 완료\n");
					out.pause();
					
				} else {
					
					System.out.println("\n-> 삭제 실패\n");
					out.pause();
					
				}
				
			}
			
			
		}else if(cnt == 0) {
			System.out.println("찾으시는 자료는 없습니다.");
			out.pause();
		}
		
	}
	


	private void counseling() {

		while(true) {
			
			out.bigTitle("상담일지 관리");
			out.menu(out.COUNSELING);
			
			int input = scan.nextInt("선택");
			
			if(input == 1) {
				selectCouSnseling();
			}else if(input == 2) {
				insertCouSnseling();
			}else if(input == 3) {
				deleteCouSnseling();
			}else if(input == 4) {
				updateCouSnseling();
			}else if(input == 5) {
				
				MainClass.crumb.out();
				break;
				
			}
			
			
		}
		
	}

	private void updateCouSnseling() {

		list();
		
		int seq = scan.nextInt("선택");
		
		cousnselingStList(seq);
		
		String input = scan.next("학생 코드");
		
		input = "ST" + input.substring(2);
		
			
		if(input != null) {
			
			while(true) {
			
			vwStudentDTO std = dao.vwStd(input);
			
			ArrayList<CourseRecordDTO> list = dao.courseRecordStd(std.getStdseq());
			
			for(CourseRecordDTO courseRecord : list) {
			System.out.println(String.format("[%s] \n\n[학생 코드]\t[학생명]\t[상담 날짜]\n%s\t\t%s\t%s\n[상담 내용]\n%s"
												,std.getLecturename()
												,std.getStdseq()
												,std.getStdname()+"\t"
												,courseRecord.getCounseRegdate()
												,courseRecord.getCounseContents()
												));
			}
			break;
				
			}
			
		} else {
			System.out.println("잘못된 입력입니다.");
		}
		
		System.out.println();
		System.out.println("수정할 날짜를 입력해주세요(취소-> 0)");
		System.out.println();
		
		String data = scan.next("날짜 입력");
		
		if(!data.equals("0")) {
			

			String contents = scan.next("상담 내용");
			
			int result = dao.updateCourseRecord(input,data,contents);
			
			if(result > 0) {
				
				System.out.println("\n-> 수정 완료");
				System.out.println();
				out.pause();
				
			}
			
		}else {
		
			System.out.println("-> 취소");
			
		}
		
	}


	private void deleteCouSnseling() {

		list();
		
		int seq = scan.nextInt("선택");
		
		cousnselingStList(seq);
		
		String input = scan.next("학생 코드");
		
		input = "ST" + input.substring(2);
		
		if(input != null) {
			
			while(true) {
			
			vwStudentDTO std = dao.vwStd(input);
			
			ArrayList<CourseRecordDTO> list = dao.courseRecordStd(std.getStdseq());
			
			for(CourseRecordDTO courseRecord : list) {
			System.out.println(String.format("[%s] \n\n[학생 코드]\t[학생명]\t[상담 날짜]\n%s\t\t%s\t%s\n[상담 내용]\n%s"
												,std.getLecturename()
												,std.getStdseq()
												,std.getStdname()
												,courseRecord.getCounseRegdate()
												,courseRecord.getCounseContents()
												));
			}
			break;
				
			}
			
		} else {
			System.out.println("잘못된 입력입니다.");
		}
		
		System.out.println();
		String data = scan.next("삭제할 날짜 입력(2000-01-01");
		
		String answer = scan.next("["+data+"] 해당 날짜 상담 기록을 삭제하시겠습까?(Y/N)");
		
		answer = answer.toUpperCase();
		
		if(answer.equals("Y")) {
			
			int result = dao.deleteCourseRecord(data,input);
			
			System.out.println(result);
			
			if(result > 0) {
				System.out.println("-> 삭제 완료");
				out.pause();
			}else {
				System.out.println("-> 삭제 실패");
				out.pause();
			}
			
			
		}
		
		
		
	}


	private void insertCouSnseling() {

		System.out.println("상담일지 입력할 과정 선택해주세요.");
		
		list();
		
		String seq = scan.next("선택");
		
		stdList(seq);
		


		CourseRecord();
		
	}




	


	private void stdUpdate() {
		
		StudentDTO std = new StudentDTO();
		while(true) {
		out.menu(out.STDUPDATE);
		
		int input = scan.nextInt("선택");
		
		if(input == 1) {
			String name = scan.next("이름");
			
			std.setSTDName(name);
			
			int nameCheck = dao.stdName(name);
						
			if(nameCheck > 0) {
				
				
			
				System.out.println("변경할 이름을 입력해주세요.");
				String nameUpdate = scan.next("이름");
				
				int result = dao.nameUpdate(name,nameUpdate);
				
				if(result > 0) {
					System.out.println("성공");
				}
				
			}
		}else if (input == 2) {
			String ssn = scan.next("주민번호");
			
			std.setSTDSsn(ssn);
			
			int ssnCheck = dao.stdSsn(ssn);
			
			if(ssnCheck > 0) {
				
				
			
				System.out.println("변경할 주민번호를 입력해주세요.");
				String ssnUpdate = scan.next("주민번호");
				
				int result = dao.ssnUpdate(ssn,ssnUpdate);
				
				if(result > 0) {
					System.out.println("성공");
				}
				
			}
		}else if (input == 3) {
			String tel = scan.next("전화번호");
			
			std.setSTDTel(tel);
			
			int telCheck = dao.stdTel(tel);
			
			if(telCheck > 0) {
				
				
			
				System.out.println("변경할 전화번호를 입력해주세요.");
				String telUpdate = scan.next("전화번호");
				
				int result = dao.telUpdate(tel,telUpdate);
				
				if(result > 0) {
					System.out.println("성공");
				}
				
			}
		}else if(input == 4) {
			MainClass.crumb.out();
			break;
			
		}
			
		}
		
	}


	private void stdDelete(String name) {
		
		vwStudentDTO std = new vwStudentDTO();
		
		std.setStdname(name);
		
		int result = dao.stdCheck(name);
		if(result > 0) {
		
			String input = scan.next("["+ name +"]"+"학생을 삭제 하시겠습니까?(y/n)");
		
			if(input.equals("y")) {
				
				int delete = dao.delete(name);
				
				if(delete > 0) {
					System.out.println("-> 삭제 완료");
					
					out.pause();
					MainClass.crumb.out();
					}
					
			} else {
				System.out.println("취소 되었습니다.");
				out.pause();
				MainClass.crumb.out();
			}
			
		}
		
	}


	private void stdInsert(String seq) {//교육생 등록

		while(true) {
		String stdSeq = scan.next("번호");//테스트후 지워야할것
		String stdName = scan.next("이름");
		String stdSsn = scan.next("주민번호(뒷자리)");
		String stdTel = scan.next("전화번호");
		
		if(stdSeq != null || stdName != null || stdSsn != null || stdTel != null) {
			
			StudentDTO std = new StudentDTO();
			
			std.setSTDSeq(stdSeq);
			std.setSTDName(stdName);
			std.setSTDSsn(stdSsn);
			std.setSTDTel(stdTel);
			
			int result = dao.insert(std);//교육생 등록
			
			if(result > 0) {
				System.out.println(result);
				
				int result2 = dao.add(std,seq);
				
				if(result2 > 0) {
					out.result(result2,"알림을 작성했습니다.");
					out.pause();
					MainClass.crumb.out();
					break;
				}
				
			}
			
		} else if(stdSeq == "0" || stdName == "0" || stdSsn == "0" || stdTel == "0") {
			System.out.println("취소 되었습니다.");
			System.out.println();
			out.pause();
			MainClass.crumb.out();
			break;
		} else {
			System.out.println("다시입력해주세요!");
		}
		
		}
		
	}




	private void list() { //교육생 > 과정 
		while(true) {
		ArrayList<LectureDTO> list = dao.lList(MainClass.isAuth != null ? true : false);
		
		for(LectureDTO lecture : list) {
			out.data(new Object[] {
					
					lecture.getLectureSeq(),
					lecture.getLectuerName()
					
			});
		
		}
		break;
		
		}
		
	}
	
	
	private void stdList(String seq) {

		while(true) {
			ArrayList<vwStudentDTO> list = dao.stdList(seq);
			
			out.header(new String[] {"학생 코드","학생명"});
			
			for(vwStudentDTO student : list) {
				out.data(new Object[]{
					
						student.getStdseq() +"\t",
						student.getStdname()
						
				});
			}
			break;
			
		}

		
		
	}
	

	private void CourseRecord() {
		
		String input = scan.next("학생 코드");
		
		input = "ST" + input.substring(2);
		
		if(input != null) {
			vwStudentDTO std = dao.vwStd(input);
			
			System.out.println(String.format("[%s] \n\n[학생 코드]\t[학생명]\n%s\t\t%s"
												,std.getLecturename()
												,std.getStdseq()
												,std.getStdname()
												));
			
			String date = scan.next("등록날짜");
			String content = scan.next("내용");
			
			CourseRecordDTO courseRecord = new CourseRecordDTO();
			
			courseRecord.setCounseRegdate(date);
			courseRecord.setCounseContents(content);
			courseRecord.setCourseSeq(std.getStdseq());
			
			
			System.out.println(courseRecord.getCounseRegdate());
			System.out.println(courseRecord.getCounseContents());
			System.out.println(courseRecord.getCourseSeq());
			
			
					
			int result = dao.insetCourseRecord(courseRecord);
			
			if(result > 0) {
				System.out.println("-> 입력 완료");
			}else {
				System.out.println("-> 입력 실패");
			}
		
		} else {
			System.out.println("잘못 입력되었습니다.");
		}
		
	}


	private void insertList() {
		
		while(true) {
			ArrayList<LectureDTO> list = dao.insertList(MainClass.isAuth != null ? true : false);
			
			out.header(new String[] {"번호", "과정", "수강인원"});
			
			for(LectureDTO lecture : list) {
				out.data(new Object[] {
						
						lecture.getLectureSeq(),
						lecture.getLectuerName(),
						lecture.getLectureCurrentSTD()
						
				});
				
			}
			break;
			
		}
		
	}

	private void selectCouSnseling() {
		
		list();
		
		int seq = scan.nextInt("선택");
		
		cousnselingStList(seq);
		
		String input = scan.next("학생 코드");
		
		input = "ST" + input.substring(2);
		
		if(input != null) {
			
			while(true) {
			
			vwStudentDTO std = dao.vwStd(input);
			
			ArrayList<CourseRecordDTO> list = dao.courseRecordStd(std.getStdseq());
			
			for(CourseRecordDTO courseRecord : list) {
			System.out.println(String.format("[%s] \n\n[학생 코드]\t[학생명]\t[상담 날짜]\n%s\t\t%s\t%s\n[상담 내용]\n%s"
												,std.getLecturename()
												,std.getStdseq()
												,std.getStdname()
												,courseRecord.getCounseRegdate()
												,courseRecord.getCounseContents()
												));
			}
			break;
			}
			
		} else {
			System.out.println("잘못된 입력입니다.");
		}
		
		
	}


	private void cousnselingStList(int seq) {
		
		while(true) {
			ArrayList<courseRecoardListDTO> list = dao.cousnselingList(seq);
			
			out.header(new String[] {"학생 코드", "학생명", "날짜"});
			
			for(courseRecoardListDTO recoard : list) {
				out.data(new Object[] {
						
						"ST"+recoard.getCourseseq()+"\t",
						recoard.getStdName()+"\t",
						recoard.getCounseregdate()
						
				});
				
			}
			
			break;
		}
		
	}


	private void stdFind(String seq, String name) {
		
		
		int count = dao.find(seq,name);
		
		if(count > 0) {
		
		vwStudentDTO result = dao.vwfind(seq,name);
		
		System.out.printf("[이름] : %s(%s)\n",result.getStdname(),result.getStdseq());
		System.out.printf("[과정명] : %s\n",result.getLecturename());
		System.out.printf("[과정] : %s ~ %s\n",result.getLecturestartdate(),result.getLectureenddate());
		System.out.printf("[강의실] : %s강의실\n",result.getClassseq());	
		
		} else {
			System.out.println("해당 학생을 찾을 수 없습니다.");
		}
		
	}

//----------------------------------------------------------------------------------------------------------------------
//출결관리및출결조회----------------------------------------------------------------------------------------------------------------------
	/**
	 * 출결 수정하기 메소드
	 */
	private void updateAttendance() {
		out.bigTitle("출결 수정하기");
		
		//학생명 입력
		String stdName = scan.next("학생명");
		ArrayList<StudentDTO> slist = new ArrayList<StudentDTO>();
		slist = dao.getStudentDTO(stdName);
		//헤더 출력
		out.header(new String[] {
				"학생번호","학생명"
		});
		//데이터 출력
		for(int i =0 ; i<slist.size();i++) {
			out.data(new Object[] {
					slist.get(i).getSTDSeq(),slist.get(i).getSTDName()
			});
		}
		//학생코드입력
		String stdSeq = scan.next("학생번호");
		//날짜입력
		String year = scan.next("시작 년도");
		String month = scan.next("시작 월");
		if(month.length()==1) {
			month="0"+month;
		}
		String day = scan.next("시작 일");
		if(day.length()==1) {
			day="0"+day;
		}
		String date = String.format("%s-%s-%s",year,month,day);
		//출결상태입력
		out.bigTitle("변경할 사항");
		out.menu(new String[] {
				"정상","지각","조퇴","외출","병가","기타"
		});
		String situation = scan.next("출결");
		
		int result = dao.updateAttendance(stdSeq,date,situation);
		out.result(result, "출결사항을 성공적으로 수정하였습니다.");
		
		out.pause();
	}

	/**
	 * 학생별 출결조회 메소드
	 */
	private void showAttendanceByStudent() {
		out.bigTitle("학생별 출결조회");
		//데이터 입력(이름)
		String stdName = scan.next("학생명");
		//데이터 추출
		ArrayList<StudentDTO> slist = new ArrayList<StudentDTO>();
		slist = dao.getStudentDTO(stdName);
		if(slist.size()==0) {
			out.bar();
			System.out.println("\t\t학생 데이터가 없습니다.");
			
			out.bar();
			out.pause("※뒤로가시려면 엔터키를 눌러주세요");
			return;
		}
		//헤더 출력
		out.header(new String[] {
				"[학생코드]","[학생명]","[전화번호]","[주민번호]"
		});
		//데이터출력
		for(int i =0; i<slist.size();i++) {
			out.data(new Object[] {
					slist.get(i).getSTDSeq(),"\t"+slist.get(i).getSTDName(),slist.get(i).getSTDTel(),slist.get(i).getSTDSsn()
			});
		}
		//학생번호 입력(유일키)
		String stdSeq = scan.next("학생번호");
		ArrayList<Object[]> olist = dao.getAttendanceByStudent(stdSeq);
		//데이터 출력
		int page = 1;
		while (true) {
			int onePage = 10;
			int index = (page * onePage) - onePage;
			if (page >= olist.size()) {
				System.out.println("페이지수를 초과하였습니다. 범위 내의 숫자를 입력해주세요");
				continue;
			}
			out.header(new String[] { "[날짜]","  ","[출석시간]", "[퇴실시간]", "[출결]"});
			for (int i = index; i < index + onePage; i++) {
				if (i >= olist.size()) {
					break;
				}
				out.data(olist.get(i));
			}
			out.bar();
			System.out.println("(0:돌아가기)\t\t" + page + "/"
					+ (olist.size() % onePage == 0 ? olist.size() / onePage : olist.size() / onePage + 1));
			out.bar();
			try {
				page = scan.nextInt("페이지");
				if(page==0) {
					break;
				}
			} catch (Exception e) {
				System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}

	/**
	 * 기간별 출결조회 메소드
	 */
	private void showAttendanceByDay() {
		out.bigTitle("기간별 출결조회");
		// 헤더출력
		System.out.println("[기간 입력]");
		String year = scan.next("시작 년도");
		String month = scan.next("시작 월");
		if(month.length()==1) {
			month="0"+month;
		}
		String day = scan.next("시작 일");
		if(day.length()==1) {
			day="0"+day;
		}
		out.bar();
		// 헤더
		out.header(new String[] { "학생명", "과정명", "출석시간", "퇴근시간", "출결상황" });
		// 데이터
		ArrayList<Object[]> olist = dao.getAttendanceByDay(year, month, day);
		int page = 1;
		while (true) {
			int onePage = 10;
			int index = (page * onePage) - onePage;
			if (page <= olist.size()) {
				System.out.println("페이지수를 초과하였습니다. 범위 내의 숫자를 입력해주세요");
				continue;
			}
			out.header(new String[] { "[학생명]","[출석시간]", "[퇴실시간]", "[출결]", "[과정명]"});
			for (int i = index; i < index + onePage; i++) {
				if (i >= olist.size()) {
					break;
				}
				out.data(olist.get(i));
			}
			out.bar();
			System.out.println("(0:돌아가기)\t\t" + page + "/"
					+ (olist.size() % onePage == 0 ? olist.size() / onePage : olist.size() / onePage + 1));
			out.bar();
			try {
				page = scan.nextInt("페이지");
				if(page==0) {
					break;
				}
			} catch (Exception e) {
				System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
			}
		}

	}

	/**
	 * 과정별 출결조회 메소드
	 */
	private void showAttendanceByLecture() {
		out.bigTitle("과정별 출결조회");
		// 헤더출력
		out.header(new String[] { "과정코드", "기간", "\t", "과정명" });
		// 데이터 출력(현재 강의(진행)중인 과정)
		ArrayList<Object[]> olist = dao.getLecture();
		for (int i = 0; i < olist.size(); i++) {
			out.data(olist.get(i));
		}
		// 선택
		String sel = "";
		try {
			sel = scan.next("과정코드");
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		out.bar();
		olist = dao.getAttendance(sel);
		// 페이징
		int page = 1;
		while (true) {
			int onePage = 10;
			int index = (page * onePage) - onePage;
			if (page >= olist.size()) {
				System.out.println("페이지수를 초과하였습니다. 범위 내의 숫자를 입력해주세요");
				continue;
			}
			
			out.header(new String[] { "[학생코드]", "[학생명]", "[전화번호]", "", "[날짜]", "[출결]" });
			for (int i = index; i < index + onePage; i++) {
				if (i >= olist.size()) {
					break;
				}
				out.data(olist.get(i));
			}
			out.bar();
			System.out.println("(0:돌아가기)\t\t" + page + "/"
					+ (olist.size() % onePage == 0 ? olist.size() / onePage : olist.size() / onePage + 1));
			out.bar();
			try {
				page = scan.nextInt("페이지");
				if(page==0) {
					break;
				}
			} catch (Exception e) {
				System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}
//---------------------------------------------------------------------------------------------------------------------------
//교재관리---------------------------------------------------------------------------------------------------------------------------
	//교재관리
	private void textBookManagement() {
		while(true) {
			out.bigTitle("교재관리");
			out.menu(UtilPrint.TEXTBOOK_MANAGEMENT);
			
			int input = scan.nextInt("선택");
		
			if (input == 1) {
				out.bigTitle("교재현황");
				out.menu(UtilPrint.TEXTBOOK_STATUS);
				
				input = scan.nextInt("선택");

				if(input == 1) {
					MainClass.crumb.in("교재 사용 현황(전체조회)");
					
					out.bigTitle("전체조회");
					
					ArrayList<TextBookManagementDTO> list = dao.textBookManagement();

					int page = 1;
					while (true) {
						int onePage = 7;
						int index = (page * onePage) - onePage;

						out.header(new String[] { "[저자명]", "[가격]", "[과목명]", "[교재명]" });

						for (int i = index; i < index + onePage; i++) {
							if (i >= list.size()) {
								break;
							}

							out.data(new Object[] { list.get(i).getTextBookWriter(), list.get(i).getTextBookPrice(),
									list.get(i).getSubjectName(), list.get(i).getTextBookName() });
						}
						out.bar();
						System.out.println("(0:돌아가기)\t\t" + page + "/"
								+ (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
						out.bar();
						page = scan.nextInt("페이지");
						out.bar();
						if (page == 0) {
							break;
						}
					}
					MainClass.crumb.out();

				} else if(input == 2) {
					MainClass.crumb.in("과정조회");
					out.bigTitle("과정조회");
					
					ArrayList<LectureDTO> llist = dao.lectureNumber();
					
					out.header(new String[] { "[과정번호]", "[과정명]" });
					
					for(LectureDTO lDTO : llist) {
						out.data(new Object[] {lDTO.getLectureSeq(), lDTO.getLectuerName()});
					}
					
					String lec = scan.next("과정 번호");
					
					ArrayList<TextBookManagementDTO> list = dao.textBookLecture(lec);
					
					out.header(new String[] { "[저자명]", "[가격]", "[과목명]", "[교재명]" });
					
					for(TextBookManagementDTO tbmDTO : list) {
						out.data(new Object[] {tbmDTO.getTextBookWriter(),tbmDTO.getTextBookPrice()
								,tbmDTO.getSubjectName(), tbmDTO.getTextBookName()});
					}
					
					MainClass.crumb.out();
					out.pause();
				} else if(input == 3) {
					
				} else {
					out.result("잘못입력하였습니다.");
				}
			} else if(input == 2) {
				MainClass.crumb.in("교재 신청서 작성");
				out.bigTitle("교재등록");
				
				String textBookName = scan.next("교재명");
				String writer = scan.next("저자명");
				String price = scan.next("가격");
				
				ArrayList<PublisherDTO> plist = dao.publisher();
				
				int page = 1;
			      while (true) {
			         int onePage = 7;
			         int index = (page * onePage) - onePage;
			         // 헤더
			         out.header(new String[] { "[출판사코드]", "[출판사명]"});
			         for (int i = index; i < index + onePage; i++) {
			            if (i >= plist.size()) {
			               break;
			            }

			            out.data(new Object[] {plist.get(i).getPublisherSeq(), plist.get(i).getPublisherName()});
			         }
			         out.bar();
			         System.out.println("(0:돌아가기)\t\t" + page + "/"
			               + (plist.size() % onePage == 0 ? plist.size() / onePage : plist.size() / onePage + 1));
			         out.bar();
			         page = scan.nextInt("페이지");
			         out.bar();
			         if (page == 0) {
			            break;
			         }
			      }
				
				String publisher = scan.next("출판사");
				
				ArrayList<SubjectDTO> list = dao.subject();
				
				page = 1;
			      while (true) {
			         int onePage = 7;
			         int index = (page * onePage) - onePage;
			         // 헤더
			         out.header(new String[] { "[과정코드]", "[과목명]"});
			         for (int i = index; i < index + onePage; i++) {
			            if (i >= list.size()) {
			               break;
			            }

			            out.data(new Object[] {list.get(i).getSubjectSeq(), list.get(i).getSubjectName()});
			         }
			         out.bar();
			         System.out.println("(0:돌아가기)\t\t" + page + "/"
			               + (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
			         out.bar();
			         page = scan.nextInt("페이지");
			         out.bar();
			         if (page == 0) {
			            break;
			         }
			      }
			      
				String subjectCode = scan.next("과목코드");
				
				TextBookDTO tbDTO = new TextBookDTO();
				
				tbDTO.setTextBookName(textBookName);
				tbDTO.setTextBookWriter(writer);
				tbDTO.setTextBookPrice(price);
				tbDTO.setPublisherSeq(publisher);
				tbDTO.setSubjectSeq(subjectCode);
				
				int result = dao.textBookApplicationWrite(tbDTO);
				
				out.result(result,"교재를 등록하였습니다.");
				out.pause();
				MainClass.crumb.out();
			} else if(input == 3) {
				break;
			} else {
				out.result("잘못입력하였습니다.");
			}
		}
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 시험관리 및 성적 조회 > 성적 수정하기 main(필기점수수정, 실기점수수정, 출결점수수정)
	 */
	private void updateGrade() {

		out.bigTitle(">>성적 수정하기<<");
		out.menu(UtilPrint.ADMIN_UPDATEGRADE);

		// 성적 수정하기
		System.out.println();
		int input = scan.nextInt(">>선택");

		if (input == 1) {
			// 필기점수 수정
			out.bigTitle(">>필기점수 수정<<");

			// DAO 위임 > update

			String stdname = scan.next(">>점수 수정할 학생 이름");
			String STDseq = scan.next(">>점수 수정할 학생 코드");
			STDseq = "ST" + STDseq.substring(2);

			StudentGradeInfoDTO studentgradeinfo = new StudentGradeInfoDTO();

			studentgradeinfo.setSTDseq(STDseq);
			studentgradeinfo.setSTDName(stdname);

			// 과목코드, 과목명 목록 출력
			ArrayList<SubjectDTO> list = dao.studentSubjectList(studentgradeinfo.getSTDseq(),
					studentgradeinfo.getSTDName());

			out.bigTitle(">>현 수강중인 과목 목록<<");

			out.header(new String[] { "과목번호", "과목명" });

			for (SubjectDTO dto : list) {
				out.data(new Object[] { dto.getSubjectSeq() + "\t", dto.getSubjectName() });
			}

			System.out.println();
			String subjectseq = scan.next(">>수정할 과목 선택");
			String updateScore = scan.next("기존점수 >> 수정할 점수");

			studentgradeinfo.setSTDName(stdname); // 점수 수정할 학생이름
			studentgradeinfo.setSubjectSeq(subjectseq); // 수정할 과목 코드
			studentgradeinfo.setUpdateGradeNoteScore(updateScore); // 수정할 필기 점수

			int result = dao.updateNoteGrade(studentgradeinfo);

			out.result("필기점수를 수정하였습니다.");
			out.pause();

		} else if (input == 2) {
			// 실기점수 수정
			out.bigTitle(">>실기점수 수정<<");

			// DAO 위임 > update

			String stdname = scan.next(">>점수 수정할 학생 이름");
			String STDseq = scan.next(">>점수 수정할 학생 코드");
			STDseq = "ST" + STDseq.substring(2);

			StudentGradeInfoDTO studentgradeinfo = new StudentGradeInfoDTO();

			studentgradeinfo.setSTDseq(STDseq);
			studentgradeinfo.setSTDName(stdname);

			// 과목코드, 과목명 목록 출력
			ArrayList<SubjectDTO> list = dao.studentSubjectList(studentgradeinfo.getSTDseq(),
					studentgradeinfo.getSTDName());

			out.bigTitle(">>현 수강중인 과목 목록<<");

			out.header(new String[] { "과목번호", "과목명" });

			for (SubjectDTO dto : list) {
				out.data(new Object[] { dto.getSubjectSeq() + "\t", dto.getSubjectName() });
			}

			System.out.println();
			String subjectseq = scan.next(">>수정할 과목 선택");
			String updateScore = scan.next("기존점수 >> 수정할 점수");

			studentgradeinfo.setSTDName(stdname); // 점수 수정할 학생이름
			studentgradeinfo.setSubjectSeq(subjectseq); // 수정할 과목 코드
			studentgradeinfo.setUpdateGradeSkillScore(updateScore); // 수정할 필기 점수

			int result = dao.updateSkillGrade(studentgradeinfo);

			out.result("실기점수를 수정하였습니다.");
			out.pause();
		} else if (input == 3) {
			// 출석점수 수정
			out.bigTitle(">>출석점수 수정<<");

			// DAO 위임 > update

			String stdname = scan.next(">>점수 수정할 학생 이름");
			String STDseq = scan.next(">>점수 수정할 학생 코드");

			StudentGradeInfoDTO studentgradeinfo = new StudentGradeInfoDTO();

			studentgradeinfo.setSTDseq(STDseq);
			studentgradeinfo.setSTDName(stdname);

			// 과목코드, 과목명 목록 출력
			ArrayList<SubjectDTO> list = dao.studentSubjectList(studentgradeinfo.getSTDseq(),
					studentgradeinfo.getSTDName());

			out.bigTitle(">>현 수강중인 과목 목록<<");

			out.header(new String[] { "과목번호", "과목명" });

			for (SubjectDTO dto : list) {
				out.data(new Object[] { dto.getSubjectSeq() + "\t", dto.getSubjectName() });
			}

			System.out.println();
			String subjectseq = scan.next(">>수정할 과목 선택");
			String updateScore = scan.next("기존점수 >> 수정할 점수");

			studentgradeinfo.setSTDName(stdname); // 점수 수정할 학생이름
			studentgradeinfo.setSubjectSeq(subjectseq); // 수정할 과목 코드
			studentgradeinfo.setUpdateGradeAttendanceScore(updateScore); // 수정할 필기 점수

			int result = dao.updateAttendanceGrade(studentgradeinfo);

			out.result("출석점수를 수정하였습니다.");
			out.pause();

		} else {
			System.out.println("잘못된 입력입니다.");
			out.pause();
		}

	}

	/**
	 * 시험관리 및 성적 조회 > 학생별 성적조회
	 */
	private void studentTestInfo() {
		// 학생 별 성적조회

		out.bigTitle(">>학생별 성적조회<<");

		String studentName = scan.next("▶학생 명");
		String studentSeq = scan.next("▶학생 코드");

		StudentGradeInfoDTO studentgradeinfo = new StudentGradeInfoDTO();

		studentgradeinfo.setSTDseq(studentSeq);
		studentgradeinfo.setSTDName(studentName);

		// 과목코드, 과목명 목록 출력
		ArrayList<SubjectDTO> list = dao.studentSubjectList(studentgradeinfo.getSTDseq(),
				studentgradeinfo.getSTDName());

		out.bigTitle(">>현 수강중인 과목 목록<<");

		out.header(new String[] { "과목번호", "과목명" });

		for (SubjectDTO dto : list) {
			out.data(new Object[] { dto.getSubjectSeq() + "\t", dto.getSubjectName() });
		}

		System.out.println();
		String input = scan.next(">>선택");

		studentgradeinfo = dao.studentGradeInfo(studentgradeinfo.getSTDseq(), studentgradeinfo.getSTDName(), input);

		out.header(new String[] { "학생 코드", "학생 명", "필기점수", "실기점수", "출석점수", "과정명", "과목명" });
		out.data(new Object[] { studentgradeinfo.getSTDseq() + "\t", studentgradeinfo.getSTDName() + "\t",
				studentgradeinfo.getGradeNoteScore() + "\t", studentgradeinfo.getGradeSkillScore() + "\t",
				studentgradeinfo.getGradeAttendanceScore() + "\t", studentgradeinfo.getLectureName() + "\t",
				studentgradeinfo.getSubjectName() });

	}

	/**
	 * 시험 관리 및 성적 조회 > 시험 정보 수정하기 main(문제 수정, 배점 수정)
	 */
	private void updateTest() {

		out.bigTitle(">>시험정보 수정하기<<");
		out.menu(UtilPrint.ADMIN_UPDATETEST);

		// 시험정보 수정
		System.out.println();
		int input = scan.nextInt(">>선택");

		if (input == 1) {
			// 문제 수정
			out.bigTitle(">>문제 수정<<");
			out.menu(UtilPrint.ADMIN_UPDATETESTQUEST);

			input = scan.nextInt(">>선택");

			if (input == 1) {
				// 프로시저 호출(필기문제 수정)
				System.out.println();
				String subjectname = scan.next(">>과목명 입력"); // 조회할 시험의 과목 코드 입력

				// 입력할 정보 가지고 오기(조회)
				ArrayList<NoteTestDTO> list = dao.noteTestList(subjectname);

				out.header(new String[] { "문제번호", "문항배점", "문제" });

				for (NoteTestDTO dto : list) {
					out.data(new Object[] { dto.getNoteQueSeq() + "\t", dto.getNoteDistribution() + "\t",
							dto.getNoteQuestion() });
				}

				System.out.println();
				String questnum = scan.next(">>문제번호 입력"); // 수정할 문제의 문제번호 입력 받음
				String quest = scan.next(">>문제 입력"); // 문제를 입력받아 입력받은 문제로 수정
				String distribution = scan.next(">>문제배점 입력"); // 문제 배점 입력

				// 필기 시험문제 수정
				NoteTestDTO dto = dao.updateNoteTestQuest(questnum, quest, distribution);

			} else if (input == 2) {
				// 프로시저 호출(필기문제 수정)
				System.out.println();
				String subjectname = scan.next(">>과목명 입력"); // 조회할 시험의 과목 코드 입력

				// 입력할 정보 가지고 오기(조회)
				ArrayList<SkillTestDTO> list = dao.skillTestList(subjectname);

				out.header(new String[] { "문제번호", "문항배점", "문제" });

				for (SkillTestDTO dto : list) {
					out.data(new Object[] { dto.getSkillQueSeq() + "\t", dto.getSkillDistribution() + "\t",
							dto.getSkillQuestion() });
				}

				System.out.println();
				String questnum = scan.next(">>문제번호 입력"); // 수정할 문제의 문제번호 입력 받음
				String quest = scan.next(">>문제 입력"); // 문제를 입력받아 입력받은 문제로 수정
				String distribution = scan.next(">>문제배점 입력"); // 문제 배점 입력

				// 필기 시험문제 수정
				SkillTestDTO dto = dao.updateSkillTestQuest(questnum, quest, distribution);

			}

		} else if (input == 2) {
			// 배점 수정
			System.out.println();
			String noteDistribution = scan.next(">>수정할 필기배점 입력");
			String skillDistribution = scan.next(">>수정할 실기배점 입력");
			String attdDistribution = scan.next(">>수정할 출석배점 입력");

			DistributionDTO distribution = new DistributionDTO();

			// 입력받은 배점들을 DTO에 저장
			distribution.setDstrNote(noteDistribution);
			distribution.setDstrSkill(skillDistribution);
			distribution.setDstrAttendance(attdDistribution);

			// DAO에 위임(update문)
			int result = dao.updateDistribution(distribution);

			System.out.println("수정을 완료하였습니다.");

		} else {
			System.out.println("잘못된 입력입니다.");
			out.pause();
		}

	}
	
	/**
	 * 과정 별 시험 성적 조회
	 */
	private void Admin_ScoreList() {

		
		out.bigTitle("과정 별 시험 성적 조회");

		// 과정명 & 학생 명 리스트 출력
		ArrayList<LectureListDTO> lectureList = dao.LectureListDTO();

		// 과정 리스트 출력 -> 전체 출력
		out.header(new String[] { "과정 명\t\t\t\t", "학생 코드\t", "학생 명" });
		for (LectureListDTO lec : lectureList) {
			System.out.println(
					String.format("%-50s\t%-10s\t%s", lec.getLectureName(), lec.getSTDSeq(), lec.getSTDName()));
		} // for
		out.line(UtilPrint.LONG);


		String stdSeq = scan.next("▶세부 내용 보기(학생 코드)(0 : 돌아가기)");
		if (!stdSeq.equals("0")) {

			ArrayList<DetailScoreListDTO> detailDTO = dao.ScoreList(stdSeq);
			// DetailScoreListDTO lec = new DetailScoreListDTO();

			out.line(UtilPrint.LONG);
			out.header(new String[] { "과목명\t", "필기점수", "실기점수", "출결점수" });

			out.line(UtilPrint.LONG);
			String name = "";
			String seq = "";
			String lecture = "";
			for (DetailScoreListDTO lec : detailDTO) {

				seq = lec.getStdSeq();
				name = lec.getStdName();
				lecture = lec.getLectureName();

				System.out.println(String.format("%-10s\t%s\t\t%s\t\t%s", lec.getSubjectName(), lec.getGradeNoteScore(),
						lec.getGrageSkillScore(), lec.getGradeAttendanceScore()));
			}
			out.line(UtilPrint.LONG);
			System.out.println(String.format("\t\t[과정 : %s] %s(%s)의 성적입니다.", lecture, name, seq));
			out.line(UtilPrint.LONG);
		} // if
		else
			System.out.println("과정 과목 세부 보기 종료");
		out.pause();
	}
}
