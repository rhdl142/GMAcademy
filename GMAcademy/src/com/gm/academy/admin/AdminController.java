package com.gm.academy.admin;

import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
import com.gm.academy.exam.NoteTestDTO;
import com.gm.academy.exam.SkillTestDTO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.LectureSubjectDTO;
import com.gm.academy.lecture.PublisherDTO;
import com.gm.academy.lecture.SubjectDTO;
import com.gm.academy.lecture.TextBookDTO;
import com.gm.academy.student.CourseRecordDTO;
import com.gm.academy.student.StudentDTO;
import com.gm.academy.student.StudentManageDTO;
import com.gm.academy.teacher.TeacherDTO;
import com.gm.academy.teacher.TeacherSelectDTO;

/**
 * 관리자 컨트롤러
 * @author 3조
 *
 */
public class AdminController {
	private UtilScanner scan;
	private UtilPrint out;
	private AdminDAO dao;

	public AdminController() {
		this.scan = new UtilScanner();
		this.out = new UtilPrint();
		this.dao = new AdminDAO();
	}

	/**
	 * 
	 * 관리자 부분 메인 분기문
	 * 
	 * @param 사용자가 입력한 id
	 * @param 사용자가 입력한 pw
	 */
	public void main(String id, String pw) {
		check(id, pw);
		while (true) {
			if (MainClass.isAuth == null) {
				break;
			} else {
				out.bigTitle(">>관리자_로그인<<");

				out.menu(UtilPrint.ADMIN_LOGIN);
				int input = scan.nextInt(">>선택");
				if (input == 1) {
					TeacherManagente(); // 교사 계정 관리 메소드
				} else if (input == 2) {
					// 개설 과정 관리///
					MainClass.crumb.in("개설 과정 관리");
					while (true) {
						out.bigTitle(">>개설 과정 관리<<");
						out.menu(UtilPrint.ADMIN_LECTURE_MANAGEMENT);

						input = scan.nextInt(">>선택");

						if (input == 1) {
							MainClass.crumb.in("과정 현황 조회");
							Admin_LectureCheck();
							MainClass.crumb.out();
						} else if (input == 2) {
							// 과정 등록
							MainClass.crumb.in("과정 등록");
							Admin_LectureRegist();
							MainClass.crumb.out();
						} else if (input == 3) {
							// 과정 삭제
							MainClass.crumb.in("과정 삭제");
							Admin_LectureRemove();
							MainClass.crumb.out();
						} else if (input == 4) {
							// 과정 수정
							MainClass.crumb.in("과정 수정");
							Admin_LectureUpdate();
							MainClass.crumb.out();
						} else if(input == 5) {
							MainClass.crumb.in("강의-과목 등록");
							Admin_LectureSubjectRegist();
							MainClass.crumb.out();
						} else if (input == 6) {
							break;
						} else {
							System.out.println("잘못 입력하였습니다. 다시 입력하세요");
							continue;
						}
					} // while
					MainClass.crumb.out();
				} else if (input == 3) { // 관리자_개설과목 관리
					while (true) {
						MainClass.crumb.in("개설과목 관리");

						out.bigTitle(">>개설과목 관리<<");
						out.menu(UtilPrint.ADMIN_SUBJECTMANAGEMENT);

						System.out.println();
						input = scan.nextInt(">>선택");

						// 선택
						if (input == 1) {

							MainClass.crumb.in("과목 현황 조회");
							subjectlist(); // 현재 강의중인 과정의 과목 리스트 출력 메소드
							MainClass.crumb.out();

						} else if (input == 2) {

							MainClass.crumb.in("과목 등록");
							addsubject();
							MainClass.crumb.out();

						} else if (input == 3) {

							MainClass.crumb.in("과목 삭제");
							deletesubject();
							MainClass.crumb.out();

						} else if (input == 4) {

							MainClass.crumb.in("과목 수정");
							updatesubject();
							MainClass.crumb.out();

						} else if (input == 5) {
							break;
						} else {
							out.result("잘못입력하였습니다.");
						}
						MainClass.crumb.out();
						out.pause();
					}
				} else if (input == 4) {
					studentMange();
				} else if (input == 5) {
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
				} else if (input == 6) {
					while (true) {
						out.bigTitle(">>출결관리 및 출결조회<<");
						out.menu(new String[] { "과정별 출결조회", "기간별 출결조회", "학생 출결조회", "출결 수정하기", "돌아가기" });
						input = 0;
						try {
							input = scan.nextInt(">>선택");
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
				} else if (input == 7) {
					MainClass.crumb.in("교재관리");
					textBookManagement();
					MainClass.crumb.out();
				} else if (input == 8) { 
					MainClass.crumb.in("추천회사 관리");
					RecommendedCompany();
					MainClass.crumb.out();
				} else if (input == 9) {
					MainClass.crumb.in("로그");
					totalLog();
					MainClass.crumb.out();
				} else if (input == 10) {
					dao.LogoutLog();
					logout();
					break;
				} else {
					out.result("잘못입력하였습니다.");
				}
			}
		}
	}
	
	/**
	 * 과정-과목 등록 
	 * '강의예정'과정-> 전체 과목 -> 과목 하위 교재 선택 -> 과정 과목 등록
	 */
	private void Admin_LectureSubjectRegist() {
		
		String lectureName = ""; //과정 명 저장 변수
		String subjectName = ""; //과목 명 저장 변수
		
		
		out.bigTitle(">>과정_과목 등록<<");
		
		// 강의예정인 과정 목록 출력
		ArrayList<LectureDTO> list = dao.showCurrentLecture();
		out.header(new String[] { "[과정 코드]", "[과정 명]\t\t\t", "[과정 여부]" });
		for (LectureDTO lec : list) {
			System.out.println(String.format("%s\t%-20s\t\t%s", lec.getLectureSeq(), lec.getLectuerName(),
					lec.getLectureProgress()));
		} // for
		out.line(UtilPrint.LONG);
		String lectureSeq = scan.next(">>과정 코드 선택");
		
		//선택 과정에 대한 과목 목록 출력
	      ArrayList<SubjectDTO> sublist = dao.subjectList(lectureSeq);
	      out.bigTitle(">>과정 [" + lectureName + "]의 현재 개설 과목 리스트<<");
	      out.header(new String[] { "[과정 명]\t\t", "[과목 코드]\t", "[과목 명]" });
	      for(SubjectDTO sub : sublist) {
	         
	         System.out.println(String.format("%-20s\t%s\t\t%s"
	               , sub.getLectureName()
	               ,sub.getSubjectSeq()
	               ,sub.getSubjectName()));
	      }
	      out.line(UtilPrint.LONG);
		
		//과목 전체 내역 출력하기
		ArrayList<SubjectDTO> detailList = dao.LecutureSubjectList(lectureSeq);
		
		int page = 1;
		while (true) {
			int onePage = 7;
			int index = (page * onePage) - onePage;
		
			out.header(new String[] {"[과목코드]","[과목명]"});
			
			for (int i = index; i < index + onePage; i++) {
				if (i >= detailList.size()) {
					break;
				}

				out.data(new Object[] {detailList.get(i).getSubjectSeq()+"\t",
						detailList.get(i).getSubjectName()});
			}
			
			out.bar();
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}
			System.out.println("(0:돌아가기)\t\t" + page + "/"
					+ (detailList.size() % onePage == 0 ? detailList.size() / onePage : detailList.size() / onePage + 1));
			out.bar();
			page = scan.nextInt(">>페이지");
			out.bar();
			if (page == 0) {
				break;
			}
		}
		
		String subjectSeq = scan.next(">>과목 코드 선택");
		
		//과목에 해당하는 교재 리스트
		out.bigTitle(">>과목 [" + subjectName + "]의 교재 리스트<<");
		
		ArrayList<TextBookDTO> textList = dao.textBookList(subjectSeq);
		out.header(new String[] {"[교재 코드]\t\t","[교재 명]"});
		
		for(TextBookDTO text : textList) {
			System.out.println(String.format("%s\t\t%s", 
					//text.getSubjectSeq(),
					text.getTextBookSeq(),
					text.getTextBookName()));
		}
		out.line(UtilPrint.LONG);
		
		String textSeq = scan.next(">>교재 코드 입력");
		String subjectStartDate = scan.next(">>과목 시작 날짜");
		String subjectEndDate = scan.next(">>과목 종료 날짜");
	

		//insert과정
		LectureSubjectDTO lecSubReg = new LectureSubjectDTO();
		
		lecSubReg.setLectureSeq(lectureSeq);
		lecSubReg.setSubjectSeq(subjectSeq);
		lecSubReg.setSubjectStartDate(subjectStartDate);
		lecSubReg.setSubjectEndDate(subjectEndDate);
		lecSubReg.setTextBookSeq(textSeq);
		
		int result = dao.registLectureSubject(lecSubReg);
		out.result(result,"과정_과목 등록 성공");
		out.pause();
	}
	
	/**
	 * 로그 main
	 */
	private void totalLog() {
		while(true) {
			out.bigTitle(">>로그<<");
			out.menu(UtilPrint.LOG_MENU);
			
			int input = scan.nextInt(">>선택");
			
			if(input == 1) {
				MainClass.crumb.in("에러로그");
				out.bigTitle(">>에러로그<<");
				
				ArrayList<ErrorLogDTO> list = dao.ErorrLog();
				
				int page = 1;
				while (true) {
					int onePage = 7;
					int index = (page * onePage) - onePage;

					out.header(new String[] { "[번호]", "[발생날짜]","[에러코드]","[발생지점]"});

					for (int i = index; i < index + onePage; i++) {
						if (i >= list.size()) {
							break;
						}

						out.data(new Object[] {list.get(i).getErrorSeq(),
											list.get(i).getErrorDate(),
											list.get(i).getErrorCode(),
											list.get(i).getPart()});
					}
					out.bar();
					for(int i =0; i<30; i++) {
						System.out.print(" ");
					}
					System.out.println("(0:돌아가기)\t\t" + page + "/"
							+ (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
					out.bar();
					page = scan.nextInt(">>페이지");
					out.bar();
					if (page == 0) {
						break;
					}
				}
				
				MainClass.crumb.out();
			} else if(input == 2) {
				MainClass.crumb.in("로그인로그");
				out.bigTitle(">>로그인로그<<");
				
				out.menu(UtilPrint.LOGIN_LOG);
				
				input = scan.nextInt(">>선택");
				ArrayList<LoginDTO> list = null;
				if(input == 1) {
					list = dao.AdminLoginLog();
				} else if(input == 2) {
					list = dao.TeacherLoginLog();
				} else if(input == 3) {
					list = dao.StudentLoginLog();
				} else if(input == 4) {
					break;
				} else {
					out.result("잘못입력하였습니다.");
				}
				
				int page = 1;
				while (true) {
					int onePage = 7;
					int index = (page * onePage) - onePage;

					out.header(new String[] { "[번호]", "[로그인코드]","[로그인시간]","[로그아웃시간]"});

					for (int i = index; i < index + onePage; i++) {
						if (i >= list.size()) {
							break;
						}

						out.data(new Object[] {list.get(i).getLogInSeq(),
										list.get(i).getLogInCode(),
										list.get(i).getLogInDate(),
										list.get(i).getLogOutDate()});
					}
					out.bar();
					for(int i =0 ; i<30; i++) {
						System.out.print(" ");
					}
					System.out.println("(0:돌아가기)\t\t" + page + "/"
							+ (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
					out.bar();
					page = scan.nextInt(">>페이지");
					out.bar();
					if (page == 0) {
						break;
					}
				}
				
				MainClass.crumb.out();
			} else if(input == 3) {
				MainClass.crumb.in("로그");
				out.bigTitle(">>로그<<");
				
				ArrayList<LogDTO> list = dao.Log();
				
				int page = 1;
				while (true) {
					int onePage = 7;
					int index = (page * onePage) - onePage;

					out.header(new String[] { "[번호]", "[발생날짜]","[로그코드]","[내용]"});

					for (int i = index; i < index + onePage; i++) {
						if (i >= list.size()) {
							break;
						}

						out.data(new Object[] {list.get(i).getLogSeq(),
												list.get(i).getLogDate(),
												list.get(i).getLogCode(),
												list.get(i).getLogContents()});
					}
					out.bar();
					for(int i =0 ; i<30; i++) {
						System.out.print(" ");
					}
					System.out.println("(0:돌아가기)\t\t" + page + "/"
							+ (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
					out.bar();
					page = scan.nextInt(">>페이지");
					out.bar();
					if (page == 0) {
						break;
					}
				}
				
				MainClass.crumb.out();
			} else if(input == 4) {
				break;
			} else {
				out.result("잘못입력하였습니다.");
			}
		}
	}

	/**
	 * 로그아웃
	 */
	private static void logout() {
		MainClass.isAuth = null;
		MainClass.tel = null;
		MainClass.name = null;
		MainClass.lectureName = null;
		MainClass.lectureDate = null;
		MainClass.lectureSeq = null;
		MainClass.tchSeq = null;
		MainClass.courseSeq = null;
	}

	/**
	 * 교재관리
	 */
	private void textBookManagement() {
		while (true) {
			out.bigTitle(">>교재관리<<");
			out.menu(UtilPrint.TEXTBOOK_MANAGEMENT);

			int input = scan.nextInt(">>선택");

			if (input == 1) {
				out.bigTitle(">>교재현황<<");
				out.menu(UtilPrint.TEXTBOOK_STATUS);

				input = scan.nextInt(">>선택");

				if (input == 1) {
					MainClass.crumb.in("교재 사용 현황(전체조회)");

					out.bigTitle(">>전체조회<<");

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

							out.data(new Object[] { list.get(i).getTextBookWriter() + "\t",
									list.get(i).getTextBookPrice(), list.get(i).getSubjectName(),
									"\t" + list.get(i).getTextBookName() });
						}
						out.bar();
						for(int i =0 ; i<30; i++) {
							System.out.print(" ");
						}
						System.out.println("(0:돌아가기)\t\t" + page + "/"
								+ (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
						out.bar();
						page = scan.nextInt(">>페이지");
						out.bar();
						if (page == 0) {
							break;
						}
					}
					MainClass.crumb.out();

				} else if (input == 2) {
					MainClass.crumb.in("과정조회");
					out.bigTitle(">>과정조회<<");

					ArrayList<LectureDTO> llist = dao.lectureNumber();

					out.header(new String[] { "[과정번호]", "[과정명]" });

					for (LectureDTO lDTO : llist) {
						out.data(new Object[] { lDTO.getLectureSeq(), "\t" + lDTO.getLectuerName() });
					}

					String lec = scan.next(">>과정 번호");

					ArrayList<TextBookManagementDTO> list = dao.textBookLecture(lec);

					out.header(new String[] { "[저자명]", "[가격]", "[과목명]", "[교재명]" });

					for (TextBookManagementDTO tbmDTO : list) {
						out.data(new Object[] { tbmDTO.getTextBookWriter(), "\t" + tbmDTO.getTextBookPrice(),
								tbmDTO.getSubjectName(), "\t" + tbmDTO.getTextBookName() });
					}

					MainClass.crumb.out();
					out.pause();
				} else if (input == 3) {

				} else {
					out.result("잘못입력하였습니다.");
				}
			} else if (input == 2) {
				MainClass.crumb.in("교재 신청서 작성");
				out.bigTitle(">>교재등록<<");

				String textBookName = scan.next(">>교재명");
				String writer = scan.next(">>저자명");
				String price = scan.next(">>가격");

				ArrayList<PublisherDTO> plist = dao.publisher();

				int page = 1;
				while (true) {
					int onePage = 7;
					int index = (page * onePage) - onePage;
					// 헤더
					out.header(new String[] { "[출판사코드]", "[출판사명]" });
					for (int i = index; i < index + onePage; i++) {
						if (i >= plist.size()) {
							break;
						}

						out.data(new Object[] { plist.get(i).getPublisherSeq(),
								"\t" + plist.get(i).getPublisherName() });
					}
					out.bar();
					for(int i =0 ; i<30; i++) {
						System.out.print(" ");
					}
					System.out.println("(0:돌아가기)\t\t" + page + "/"
							+ (plist.size() % onePage == 0 ? plist.size() / onePage : plist.size() / onePage + 1));
					out.bar();
					page = scan.nextInt(">>페이지");
					out.bar();
					if (page == 0) {
						break;
					}
				}

				String publisher = scan.next(">>출판사");

				ArrayList<SubjectDTO> list = dao.subject();

				page = 1;
				while (true) {
					int onePage = 7;
					int index = (page * onePage) - onePage;
					// 헤더
					out.header(new String[] { "[과정코드]", "[과목명]" });
					for (int i = index; i < index + onePage; i++) {
						if (i >= list.size()) {
							break;
						}

						out.data(new Object[] { list.get(i).getSubjectSeq(), "\t" + list.get(i).getSubjectName() });
					}
					out.bar();
					for(int i =0 ; i<30; i++) {
						System.out.print(" ");
					}
					System.out.println("(0:돌아가기)\t\t" + page + "/"
							+ (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
					out.bar();
					page = scan.nextInt(">>페이지");
					out.bar();
					if (page == 0) {
						break;
					}
				}

				String subjectCode = scan.next(">>과목코드");

				TextBookDTO tbDTO = new TextBookDTO();

				tbDTO.setTextBookName(textBookName);
				tbDTO.setTextBookWriter(writer);
				tbDTO.setTextBookPrice(price);
				tbDTO.setPublisherSeq(publisher);
				tbDTO.setSubjectSeq(subjectCode);

				int result = dao.textBookApplicationWrite(tbDTO);

				out.result(result, "교재를 등록하였습니다.");
				out.pause();
				MainClass.crumb.out();
			} else if (input == 3) {
				break;
			} else {
				out.result("잘못입력하였습니다.");
			}
		}
	}

	/**
	 * 관리자의 아이디와 비밀번호의 검사 메소드
	 * 
	 * @param 사용자가 입력한 id
	 * @param 사용자가 입력한 pw
	 */
	private void check(String id, String pw) {
		AdminLogInDTO alDTO = new AdminLogInDTO();

		alDTO.setAdminID(id);
		alDTO.setAdminPW(pw);

		int result = Integer.parseInt(dao.auth(alDTO));

		if (result == 0) {
			MainClass.isAuth = null;
		} else {
			MainClass.isAuth = alDTO.getAdminID();
			dao.LoginLog();
		}

		out.result(result, "인증에 성공했습니다.");
		out.pause();
	}

	/**
	 * 관리자 교사계정 관리 메소드
	 */
	private void TeacherManagente() {// 교사 계정 관리 메소드
		MainClass.crumb.in("교사 계정 관리");
		while (true) {
			int input;
			out.bigTitle(">>교사 계정 관리<<");
			out.menu(UtilPrint.ADMINTEACHERMANAGEMENT);
			input = scan.nextInt(">>선택");
			if (input == 1) {
				TeacherServed(); // 교사 재직 현황
			} else if (input == 2) {
				TeacherRegister();// 교사 등록
			} else if (input == 3) {
				TeacherRemove();// 교사 삭제
			} else if (input == 4) {
				TeacherUpdateMenu();// 교사 수정
			} else if (input == 5) {

				MainClass.crumb.out();
				break;
			}

		}
	}

	/**
	 * 관리자 교사수정 세부 메뉴 메소드
	 */
	private void TeacherUpdateMenu() { // 수정 세부 메뉴
		MainClass.crumb.in("교사 수정");
		while (true) {
			int input;
			out.bigTitle(">> 교사 수정 <<");
			out.menu(UtilPrint.ADMINTEACHERUPDATEMENU);
			input = scan.nextInt(">>선택");
			if (input == 1) { // 이름 수정
				TeacherUpdateName();
			} else if (input == 2) { // 전화번호 수정
				TeacherUpdateTel();
			} else if (input == 3) { // 돌아가기
				MainClass.crumb.out();
				break;
			}
		}
	}

	/**
	 * 관리자 교사전화번호를 수정하는 메소드
	 */
	private void TeacherUpdateTel() { // 전화번호 수정
		MainClass.crumb.in("전화번호 수정");
		out.bigTitle(">> 전화번호 수정 <<");
		out.bar();
		TeacherList();
		String tchTelBefor = scan.next(">>전화 번호");
		String tchTelAfter = scan.next(">>변경할 전화 번호");

		int result = dao.updateTel(tchTelBefor, tchTelAfter);

		out.bar();
	
		out.result(result, "수정이 완료되었습니다.");
		out.pause();
		MainClass.crumb.out();
	}

	/**
	 * 관리자 교사이름을 수정하는 메소드
	 */
	private void TeacherUpdateName() { // 이름 수정
		MainClass.crumb.in("이름 수정");
		out.bigTitle(">> 이름 수정 <<");
		out.bar();
		TeacherList();
		String tchNameBefor = scan.next(">>교사 이름");
		String tchNameAfter = scan.next(">>변경할 교사 이름");
		
		int result = dao.updateName(tchNameBefor,tchNameAfter);
		
			if(result > 0) {
				int result2 = dao.updateTchId(tchNameAfter);
				out.bar();
				
				out.result(result2,"수정이 완료되었습니다.");
				out.pause();
				MainClass.crumb.out();
			}
	}

	// 교사 삭제 메소드
	/**
	 * 관리자 교사삭제하는 메소드
	 */
	private void TeacherRemove() {

		MainClass.crumb.in("교사 삭제");
		out.bigTitle(">> 교사 삭제 <<");
		TeacherList();
		out.bar();

		String tchId = scan.next(">>교사ID");
		String tchssn = scan.next(">>교사PW");

		String sel = scan.next(">>정말 삭제 하시겠습니까? (y/n)");

		if (sel.equals("y")) {
			int result = dao.Remove(tchId, tchssn);
			out.result(result, "삭제가 완료되었습니다.");
		} else if (sel.equals("n")) {
			out.result("삭제가 취소 되었습니다.");
		}

		out.bar();
		out.pause();
		MainClass.crumb.out();

	}

	   /**
	    * 관리자 교사정보를 등록하는 메소드
	    */
	   private void TeacherRegister() {
	      MainClass.crumb.in("교사 등록");
	      out.bigTitle(">> 교사 등록 <<");
	      TeacherList();
	      out.bar();
	      
	      String tchname = scan.next(">>이름");
	      String tchssn = scan.next(">>주민번호(뒷자리)");
	      String tchtel = scan.next(">>전화번호");
	      
	      TeacherDTO register = new TeacherDTO();
	      
	      register.setTCHName(tchname);//교사 이름
	      register.setTCHSsn(tchssn);
	      register.setTCHTel(tchtel);
	      
	      
	      int result = dao.TeacherRegister(register);
	      
	      out.bar();

	      out.result(result,"등록이 완료되었습니다.");
	      out.pause();
	      
	      
	   }

	// 교사 정보 출력
	/**
	 * 관리자 교사정보를 출력하는 메소드
	 */
	private void TeacherList() {
		out.bar();
		ArrayList<TeacherSelectDTO> list = dao.list();

		out.header(new String[] { "[교사명]", "[전화번호]", "[아이디]" });

		for (TeacherSelectDTO teacher : list) {
			out.data(new Object[] { teacher.getTCHName() , teacher.getTCHTel(), teacher.getTCHId() + "\t"
					// teacher.getTCHRegdate()
			});
		}
		out.bar();

	}

	// 교사 재직 현황 출력 메소드
	/**
	 * 관리자 교사 재직현황을 출력하는 메소드
	 */
	private void TeacherServed() {
		MainClass.crumb.in("교사 조회");
		out.bigTitle(">> 교사 재직 현황 <<");
		out.bar();
		ArrayList<TeacherSelectDTO> list = dao.list();

		out.header(new String[] { "[교사명]", "[주민번호]", "[전화번호]", "[아이디]", "[등록일자]" });

		for (TeacherSelectDTO teacher : list) {
			out.data(new Object[] {

					teacher.getTCHName() , teacher.getTCHSsn() + "\t", teacher.getTCHTel(), teacher.getTCHId(),
					teacher.getTCHRegdate() });
		}
		out.bar();

		out.pause();
		MainClass.crumb.out();

	}

	/**
	 * 관리자 과목수정하는 메소드
	 */
	private void updatesubject() {

		out.bigTitle(">>과목 수정<<");

		// 과목 수정
		String updatesubjectname = scan.next(">>수정할 과목명");
		String subjectname = scan.next(">>기존 과목명");

		// DAO 위임 > update
		SubjectDTO subject = new SubjectDTO();

		subject.setUpdatesubjectname(updatesubjectname);
		subject.setSubjectName(subjectname);

		int result = dao.updatesubject(subject);

		out.result("과목을 수정하였습니다.");
	}

	/**
	 * 관리자 과목을 삭제하는 메소드
	 */
	private void deletesubject() {

		out.bigTitle(">>과목 삭제<<");

		// 과목 삭제
		String subjectname = scan.next(">>삭제할 과목명");

		// DAO 위임 > delete
		SubjectDTO subject = new SubjectDTO();

		subject.setSubjectName(subjectname);

		int result = dao.deletesubject(subject);

		out.result(result, "과목을 삭제하였습니다.");
	}

	/**
	 * 관리자 과목을 등록하는 메소드
	 */
	private void addsubject() {

		out.bigTitle(">>과목 등록<<");

		// 과목 등록
		String subjectname = scan.next(">>등록할 과목명");

		// DAO 위임 > insert
		SubjectDTO subject = new SubjectDTO();

		subject.setSubjectName(subjectname);

		int result = dao.addsubject(subject);

		out.result(result, "과목을 등록하였습니다.");

	}

	/**
	 * 관리자 과목현황을 조회하는 메소드
	 */
	private void subjectlist() {

		out.bigTitle(">>과목현황 조회<<");

		// 과목코드, 과목명 목록 출력
		ArrayList<SubjectDTO> list = dao.subjectlist();

		out.header(new String[] { "[과목번호]", "[과목명]" });

		for (SubjectDTO dto : list) {
			out.data(new Object[] { dto.getSubjectSeq() + "\t", dto.getSubjectName() });
		}

	}// 과목현황 조회

	/**
	 * 출결 수정하기 메소드
	 */
	private void updateAttendance() {
		out.bigTitle(">>출결 수정하기<<");

		// 학생명 입력
		String stdName = scan.next(">>학생명");
		ArrayList<StudentDTO> slist = new ArrayList<StudentDTO>();
		slist = dao.getStudentDTO(stdName);
		// 헤더 출력
		out.header(new String[] { "[학생번호]", "[학생명]" });
		// 데이터 출력
		for (int i = 0; i < slist.size(); i++) {
			out.data(new Object[] { slist.get(i).getSTDSeq(), "/t" + slist.get(i).getSTDName() });
		}
		// 학생코드입력
		String stdSeq = scan.next(">>학생번호");
		// 날짜입력
		String year = scan.next(">>시작 년도");
		String month = scan.next(">>시작 월");
		if (month.length() == 1) {
			month = "0" + month;
		}
		String day = scan.next(">>시작 일");
		if (day.length() == 1) {
			day = "0" + day;
		}
		String date = String.format("%s-%s-%s", year, month, day);
		// 출결상태입력
		out.bigTitle(">>변경할 사항<<");
		out.menu(new String[] { "정상", "지각", "조퇴", "외출", "병가", "기타" });
		String situation = scan.next(">>출결");

		int result = dao.updateAttendance(stdSeq, date, situation);
		out.result(result, "출결사항을 성공적으로 수정하였습니다.");

		out.pause();
	}

	/**
	 * 학생별 출결조회 메소드
	 */
	private void showAttendanceByStudent() {
		out.bigTitle(">>학생별 출결조회<<");
		// 데이터 입력(이름)
		String stdName = scan.next(">>학생명");
		// 데이터 추출
		ArrayList<StudentDTO> slist = new ArrayList<StudentDTO>();
		slist = dao.getStudentDTO(stdName);
		if (slist.size() == 0) {
			out.bar();
			System.out.println("\t\t학생 데이터가 없습니다.");

			out.bar();
			out.pause("※뒤로가시려면 엔터키를 눌러주세요");
			return;
		}
		// 헤더 출력
		out.header(new String[] { "[학생코드]", "[학생명]", "[전화번호]", "[주민번호]" });
		// 데이터출력
		for (int i = 0; i < slist.size(); i++) {
			out.data(new Object[] { slist.get(i).getSTDSeq(), "\t" + slist.get(i).getSTDName(),
					"\t" + slist.get(i).getSTDTel(), slist.get(i).getSTDSsn() });
		}
		// 학생번호 입력(유일키)
		String stdSeq = scan.next(">>학생번호");
		ArrayList<Object[]> olist = dao.getAttendanceByStudent(stdSeq);
		// 데이터 출력
		int page = 1;
		while (true) {
			int onePage = 10;
			int index = (page * onePage) - onePage;
			if (page >= olist.size()) {
				System.out.println("페이지수를 초과하였습니다. 범위 내의 숫자를 입력해주세요");
				continue;
			}
			out.header(new String[] { "[날짜]", "  ", "[출석시간]", "[퇴실시간]", "[출결]" });
			for (int i = index; i < index + onePage; i++) {
				if (i >= olist.size()) {
					break;
				}
				out.data(olist.get(i));
			}
			out.bar();
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}
			System.out.println("(0:돌아가기)\t\t" + page + "/"
					+ (olist.size() % onePage == 0 ? olist.size() / onePage : olist.size() / onePage + 1));
			out.bar();
			try {
				page = scan.nextInt(">>페이지");
				if (page == 0) {
					break;
				}
			} catch (Exception e) {
				for(int i =0 ; i<30; i++) {
					System.out.print(" ");
				}
				System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}

	/**
	 * 기간별 출결조회 메소드
	 */
	private void showAttendanceByDay() {
		out.bigTitle(">>기간별 출결조회<<");
		// 헤더출력
		System.out.println("[기간 입력]");
		String year = scan.next(">>시작 년도");
		String month = scan.next(">>시작 월");
		if (month.length() == 1) {
			month = "0" + month;
		}
		String day = scan.next(">>시작 일");
		if (day.length() == 1) {
			day = "0" + day;
		}
		out.bar();
		// 헤더
		out.header(new String[] { "[학생명]", "[과정명]", "[출석시간]", "[퇴근시간]", "[출결상황]" });
		// 데이터
		ArrayList<Object[]> olist = dao.getAttendanceByDay(year, month, day);
		if (olist.size() == 0) {
			out.bar();
			System.out.println("\t\t학생 데이터가 없습니다.");

			out.bar();
			out.pause("※뒤로가시려면 엔터키를 눌러주세요");
			return;
		}
		int page = 1;
		while (true) {
			try {
				page = scan.nextInt(">>페이지");
				if (page == 0) {
					break;
				}
			} catch (Exception e) {
				for(int i =0 ; i<30; i++) {
					System.out.print(" ");
				}
				System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
			}
			int onePage = 10;
			int index = (page * onePage) - onePage;
			if (page >= olist.size()) {
				System.out.println("페이지수를 초과하였습니다. 범위 내의 숫자를 입력해주세요");
				continue;
			}
			out.header(new String[] { "[학생명]", "[출석시간]", "[퇴실시간]", "[출결]", "[과정명]" });
			for (int i = index; i < index + onePage; i++) {
				if (i >= olist.size()) {
					break;
				}
				out.data(olist.get(i));
			}
			out.bar();
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}
			System.out.println("(0:돌아가기)\t\t" + page + "/"
					+ (olist.size() % onePage == 0 ? olist.size() / onePage : olist.size() / onePage + 1));
			out.bar();

		}

	}

	/**
	 * 과정별 출결조회 메소드
	 */
	private void showAttendanceByLecture() {
		out.bigTitle(">>과정별 출결조회<<");
		// 헤더출력
		out.header(new String[] { "[과정코드]", "[기간]", " ", "[과정명]" });
		// 데이터 출력(현재 강의(진행)중인 과정)
		ArrayList<Object[]> olist = dao.getLecture();
		for (int i = 0; i < olist.size(); i++) {
			out.data(olist.get(i));
		}
		// 선택
		String sel = "";
		try {
			sel = scan.next(">>과정코드");
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

			out.header(new String[] { "[학생코드]", "[학생명]", "[전화번호]", "", "[날짜]", "", "[출결]" });
			for (int i = index; i < index + onePage; i++) {
				if (i >= olist.size()) {
					break;
				}
				out.data(olist.get(i));
			}
			out.bar();
			for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}
			System.out.println("(0:돌아가기)\t\t" + page + "/"
					+ (olist.size() % onePage == 0 ? olist.size() / onePage : olist.size() / onePage + 1));
			out.bar();
			try {
				page = scan.nextInt(">>페이지");
				if (page == 0) {
					break;
				}
			} catch (Exception e) {
				for(int i =0 ; i<30; i++) {
					System.out.print(" ");
				}
				System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
			}
		}
	}

	/**
	 * @예지 관리자_과정 수정 메소드
	 */
	private void Admin_LectureUpdate() {

		out.bigTitle(">>과정 수정<<");

		ArrayList<LectureDTO> lecList = dao.LectureList();

		out.header(new String[] { "[과정코드]", "[시작날짜]", "[종료날짜]", "[진행여부]", "[과정 명]" });
		for (LectureDTO lec : lecList) {
			out.data(new Object[] { lec.getLectureSeq() + "\t", lec.getLectureStartDate(), lec.getLectureEndDate(),
					lec.getLectureProgress() + "\t", lec.getLectuerName() });
		} // for
		out.line(UtilPrint.LONG);

		while (true) {

			out.bigTitle(">>과정 수정<<");
			out.menu(UtilPrint.ADMIM_LETUREUPDATE);
			int select = scan.nextInt(">>선택");

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

	/**
	 * 교사코드를 수정하는 메소드
	 */
	private void LectureTeacherUpdate() {

		while (true) {
			out.bigTitle(">>교사 코드 수정<<");

			String lectureSeq = scan.next(">>수정할 과정 코드 입력 (0 : 돌아가기)");

			if (!lectureSeq.equals("0")) {
				String TCHSeq = scan.next(">>수정할 교사 코드 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setTCHSeq(TCHSeq);

				int result = dao.updateTeacher(lecDTO);

				out.result(result, " ■교사 코드 수정 성공■");
				out.pause();
			} else
				break;
		}
	}

	/**
	 * 강의실을 수정하는 메소드
	 */
	private void LectureClassRoomUpdate() {

		while (true) {
			out.bigTitle(">>강의실 수정<<");

			String lectureSeq = scan.next(">>수정할 과정 코드 입력 (0 : 돌아가기)");
			if (!lectureSeq.equals("0")) {

				String classSeq = scan.next(">>수정할 강의실 번호 입력");
				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setClassSeq(classSeq);

				int result = dao.updateClassRoom(lecDTO);

				out.result(result, " ■강의실 수정 성공■");
				out.pause();
			} else
				break;
		}
	}

	/**
	 * 학생인원을 수정하는메소드
	 */
	private void LetureStudentUpdate() {

		while (true) {
			out.bigTitle(">>학생 인원 수정<<");

			String lectureSeq = scan.next(">>수정할 과정 코드 입력 (0 : 돌아가기)");

			if (!lectureSeq.equals("0")) {
				String lectureAcceptSTD = scan.next(">>수정할 총 인원 입력");
				String lectureCurrentSTD = scan.next(">>수정할 현 수강 인원 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setLectureAcceptSTD(lectureAcceptSTD);
				lecDTO.setLectureCurrentSTD(lectureCurrentSTD);

				int result = dao.updateStuedent(lecDTO);
				out.result(result, " ■학생 인원 수정 성공■");
				out.pause();
			} else
				break;
		}
	}

	/**
	 * @예지 강의 진행 여부 수정
	 */
	private void LectureProgressUpdate() {

		while (true) {

			out.bigTitle(">>강의 진행 여부 수정<<");

			String lectureSeq = scan.next(">>수정할 과정 코드 입력 (0 : 돌아가기)");
			if (!lectureSeq.equals("0")) {
				String lectureProgress = scan.next(">>강의 진행 여부 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setLectureProgress(lectureProgress);

				int result = dao.updateLectureProgress(lecDTO);

				out.result(result, " ■강의 진행 여부 수정 성공■");
				out.pause();
			} else
				break;
		}
	}

	/**
	 * @예지 과정 날짜 수정
	 */
	private void LectureDateUpdate() {

		while (true) {
			out.bigTitle(">>과정 날짜 수정<<");
			String lectureSeq = scan.next(">>수정할 과정 코드 입력(0 : 돌아가기)");
			if (!lectureSeq.equals("0")) {
				String lectureStartDate = scan.next(">>과정 시작 날짜 입력");
				String lectureEndDate = scan.next(">>과정 종료 날짜 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setLectureStartDate(lectureStartDate);
				lecDTO.setLectureEndDate(lectureEndDate);

				int result = dao.updateLectureDate(lecDTO);

				out.result(result, " ■과정 날짜 수정 성공■");
				out.pause();
			} else
				break;
		}

	}

	/**
	 * @예지 과정 명 수정
	 */
	private void LectureNameUpdate() {

		while (true) {
			out.bigTitle(">>과정 명 수정<<");

			String lectureSeq = scan.next(">>수정할 과정 코드 입력(0 : 돌아가기)");
			if (!lectureSeq.equals("0")) {
				String lectureName = scan.next(">>수정 할 과목 명 입력");

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(lectureSeq);
				lecDTO.setLectuerName(lectureName);

				int result = dao.updateLectureName(lecDTO);

				out.result(result, " ■과정 명 수정 성공■");
				out.pause();
			} else
				break;
		}
	}

	/**
	 * @예지 관리자_과정 삭제 메소드
	 */
	private void Admin_LectureRemove() {

	      ArrayList<LectureDTO> lecList = dao.LectureList();

	      out.bigTitle(">>과정 삭제<<");
	      out.header(new String[] { "[과정코드]", "[시작날짜]", "[종료날짜]", "[진행여부]", "[과정 명]" });
	      for (LectureDTO lec : lecList) {
	         if(lec.getLectureProgress().equals("강의중")) {
	         out.data(new Object[] { lec.getLectureSeq() + "\t", lec.getLectureStartDate(), lec.getLectureEndDate(),
	               lec.getLectureProgress() + "\t", lec.getLectuerName() });
	         } else {
	            out.data(new Object[] { lec.getLectureSeq() + "\t", lec.getLectureStartDate(), lec.getLectureEndDate(),
	                  lec.getLectureProgress() +"  ", lec.getLectuerName() });
	         }
	      } // for
	      out.line(UtilPrint.LONG);

	      String seq = scan.next(">>삭제할 과정 코드 입력(0 : 돌아가기)");
	      if (!seq.equals("0")) {
	         LectureDTO lecDTO = new LectureDTO();
	         lecDTO.setLectureSeq(seq);

	         int result = dao.LectureRemove(lecDTO);

	         out.result(result, " ■과정 삭제 성공■");
	         out.pause();
	      } else {
	         System.out.println("과정 삭제 종료");
	         
	      }
	   }

	/**
	 * @예지 관리자_과정 등록 메소드
	 */
	private void Admin_LectureRegist() {

		out.bigTitle(">>과정 등록<<");

		String lectureName = scan.next(">>과정 명 (0 : 돌아가기)");
		if (!lectureName.equals("0")) {
			String lectureStartDate = scan.next(">>과정 시작 날짜");
			String lectureEndDate = scan.next(">>과정 종료 날짜");
			String classSeq = scan.next(">>강의실 번호");
			String lectureProgress = scan.next(">>강의 진행 여부");
			String lectureAcceptSTD = scan.next(">>수강 가능 인원");
			String lectureCurrentSTD = scan.next(">>현재 수강 인원");
			String tchSeq = scan.next(">>교사 코드");

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
		} else {
			System.out.println("과정 등록 종료");
		}
	}

	/**
	 * @예지 관리자_과정 현황 조회 메소드
	 */
	private void Admin_LectureCheck() {

		// 리스트 출력
		out.bigTitle(">>과정 현황 조회<<");
		ArrayList<LectureDTO> lecList = dao.LectureList();

		out.header(new String[] { "[과정코드]", "[시작날짜]", "[종료날짜]", "[진행여부]", "[과정 명]" });
		for (LectureDTO lec : lecList) {
			out.data(new Object[] { lec.getLectureSeq() + "\t", lec.getLectureStartDate(), lec.getLectureEndDate(),
					lec.getLectureProgress() + "      ", lec.getLectuerName() });
		} // for
		out.line(UtilPrint.LONG);

		/**
		 * 관리자_과정 내용 상세보기
		 */
		// 삭제 코드 입력받기
		String lecSeq = scan.next(">>과정 세부 내역 보기(코드 입력)(0 : 돌아가기)");
		if (!lecSeq.equals("0")) {

			ArrayList<LectureDTO> lecDetail = dao.LectureDetail(lecSeq);
			out.header(new String[] { "[과정코드]", "[진행여부]", "[교사이름]", "[수강인원]", "[강의실]", "[과정 명]" });
			for (LectureDTO lec : lecDetail) {
				out.data(new Object[] { lec.getLectureSeq() + "\t", lec.getLectureProgress() ,
						lec.getTeacherName() + "  \t", lec.getLectureCurrentSTD() + "\t", lec.getClassSeq(),
						lec.getLectuerName() });// for
				out.line(UtilPrint.LONG);
			}

		} else {
			System.out.println("과정 현황 보기 종료");
		}

		out.pause();

	}// LectureCheck..

	/**
	 * 추천회사 메인 분기문
	 */
	private void RecommendedCompany() {
		while (true) {
			out.bigTitle(">>추천회사 관리<<");
			out.menu(new String[] { "추천회사 추가하기", "추천회사 삭제하기", "추천회사 조회하기", "추천회사 수정하기", "돌아가기" });
			int input = scan.nextInt(">>선택");
			if (input == 1) {// 추천회사 추가하기
				MainClass.crumb.in("추천회사 추가하기");
				addRecommendCompany();
				MainClass.crumb.out();
			} else if (input == 2) {// 추천회사 삭제하기
				MainClass.crumb.in("추천회사 삭제하기");
				removeRecommendCompany();
				MainClass.crumb.out();
			} else if (input == 3) {// 추천회사 조회하기
				MainClass.crumb.in("추천회사 조회하기");
				MainClass.showRecommendedCompany();
				MainClass.crumb.out();
			} else if (input == 4) {// 추천회사 수정하기
				MainClass.crumb.in("추천회사 수정하기");
				updateRecommendedCompany();
				MainClass.crumb.out();
			} else if (input == 5) {// 돌아가기
				break;
			} else {
				System.out.println("잘못입력하셨습니다. 다시 입력해주시기 바랍니다.");
				continue;
			}

		}
	}

	/**
	 * 추천회사 목록에서 추천회사의 내용을 수정하는 메소드
	 */
	private void updateRecommendedCompany() {
		out.bigTitle(">>추천회사 수정하기<<");
		// 페이징
		ArrayList<Object[]> olist = dao.getRecommendedCompany();
		int page = 1;
		int count = 0;
		while (true) {
			try {
				if (count != 0) {
					page = scan.nextInt(">>페이지");
				}
				if (page == 0) {
					break;
				}
			} catch (Exception e) {
				
				out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
			int onePage = 7;
			int index = (page * onePage) - onePage;
			if (index >= olist.size()) {
				System.out.println("페이지수를 초과하였습니다. 다시입력해주세요");
				continue;
			}
			// 데이터출력
			out.header(new String[] { "[번호]", "[회사명]", "[위치]", "[급여]" });
			for (int i = index; i < index + onePage; i++) {
				if (i >= olist.size()) {
					break;
				}
				out.data(olist.get(i));
			}
			out.bar();
			for(int i =0; i<30; i++) {
				System.out.print(" ");
			}
			System.out.println("(0:회사번호 입력)\t\t" + page + "/"
					+ (olist.size() % onePage == 0 ? olist.size() / onePage : olist.size() / onePage + 1));
			out.bar();
			count++;
		}
		// 회사선택
		int seq = scan.nextInt(">>회사번호");
		String name = scan.next(">>회사명");
		String location = scan.next(">>위치");
		String payment = scan.next(">>급여");
		int result = dao.updateRecommendedCompany(name, location, payment, seq);
		out.result(result, "성공적으로 수정하였습니다.");
	}

	/**
	 * 추천회사 목록에서 추천회사를 삭제하는 메소드
	 */
	private void removeRecommendCompany() {
		out.bigTitle(">>추천회사 삭제하기<<");
		// 페이징
		ArrayList<Object[]> olist = dao.getRecommendedCompany();
		int page = 1;
		int count = 0;
		while (true) {
			try {
				if (count != 0) {
					page = scan.nextInt(">>페이지");
				}
				if (page == 0) {
					break;
				}
			} catch (Exception e) {
				
				out.result("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
			int onePage = 7;
			int index = (page * onePage) - onePage;
			if (index >= olist.size()) {
				System.out.println("페이지수를 초과하였습니다. 다시입력해주세요");
				continue;
			}
			// 데이터출력
			out.header(new String[] { "[번호]", "[회사명]", "[위치]", "[급여]" });
			for (int i = index; i < index + onePage; i++) {
				if (i >= olist.size()) {
					break;
				}
				out.data(olist.get(i));
			}
			out.bar();
			for(int i =0; i<30; i++) {
				System.out.print(" ");
			}
			System.out.println("(0:회사번호 입력)\t\t" + page + "/"
					+ (olist.size() % onePage == 0 ? olist.size() / onePage : olist.size() / onePage + 1));
			out.bar();
			count++;
		}
		int seq = scan.nextInt(">>선택");
		int result = dao.removeRecommendCompany(seq);
		out.result(result, "성공적으로 삭제하였습니다.");
	}

	/**
	 * 추천회사 목록에 추천회사를 추가하는 메소드
	 */
	private void addRecommendCompany() {
		out.bigTitle(">>추천회사 추가하기<<");
		out.bar();
		String name = scan.next(">>회사명");
		out.bar();
		String location = scan.next(">>회사위치");
		out.bar();
		String payment = scan.next(">>급여");
		out.bar();
		int result = dao.addRecommendCompany(name, location, payment);
		out.result(result, "성공적으로 추가하였습니다.");
	}
	// --------------------------------------------------------------------------------------교육생
	// 관리
	/**
	 * 교육생 관리 main
	 */
	private void studentMange() { // 교육생 관리 페이지

		while (true) {

			int input;

			MainClass.crumb.in("교육생관리");

			out.bigTitle(">>교육생 관리<<");
			out.menu(UtilPrint.ADMINSTUDENT);

			input = scan.nextInt(">>선택");
			MainClass.crumb.out();
			if (input == 1) {

				MainClass.crumb.in("교육생조회");
				out.bigTitle(">>교육생 조회<<");
				Llist();
				String seq = scan.next(">>과정 선택");
				stdList(seq);
				if (seq != "0") {
					String name = scan.next(">>이름");

					stdFind(seq, name);// 해당 과정 교육생 찾기
				}

				out.pause();
				MainClass.crumb.out();

			} else if (input == 2) {

				MainClass.crumb.in("교육생등록");
				out.bigTitle(">>교육생 등록<<");
				insertList(); // 수강 가능한 과정 선택
				String seq = scan.next(">>과정 선택");
				stdInsert(seq); // 교육생 등록
				MainClass.crumb.out();

			} else if (input == 3) {
				MainClass.crumb.in("교육생삭제");
				out.bigTitle(">>교육생 삭제<<");
				Llist(); // 과정 선택
				String seq = scan.next(">>과정 선택");

				stdList(seq); // 해당 과정 듣는 교육생 목록
				if (seq != "0") {
					String name = scan.next(">>이름");

					stdFind(seq, name);// 해당 과정 교육생 정보 출력
					stdDelete(seq, name);// 해당 과정 교육생 삭제
				}
				MainClass.crumb.out();

			} else if (input == 4) {
				MainClass.crumb.in("교육생수정");
				out.bigTitle(">>교육생 수정<<");
				stdUpdate();
				MainClass.crumb.out();
			} else if (input == 5) {
				MainClass.crumb.in("상담일지 관리");
				counseling();
				MainClass.crumb.out();
			} else if (input == 6) {
				MainClass.crumb.in("사후 관리");

				oversight();
				MainClass.crumb.out();
			} else if (input == 7) {
				MainClass.crumb.out();
				break;

			}

		}

	}
	
	/**
	 * 교육생 관리 > 사후관리
	 */
	private void oversight() { // 사후 관리

		while (true) {
			out.bigTitle(">>사후 관리<<");
			out.menu(out.OVERSIGHT);

			System.out.println();
			int input = scan.nextInt(">>선택");
			System.out.println();

			if (input == 1) {
				selectOversight();
			} else if (input == 2) {
				insertOversight();
			} else if (input == 3) {
				deleteOversight();
			} else if (input == 4) {
				updateOversight();
			} else if (input == 5) {
				MainClass.crumb.out();
				break;
			}
		}

	}
	
	/**
	 * 교육생관리 > 사후관리 > 사후처리 수정
	 */
	private void updateOversight() {

		Llist();

		String input = scan.next(">>선택");

		stdList(input);

		String seq = scan.next(">>학생 코드 입력");

		seq = "ST" + seq.substring(2);

		VwStudentDTO std = dao.vwfind2(input, seq);

		int cnt = dao.OversightCnt(std.getLectureseq(), std.getStdseq());

		if (cnt == 1) {
			StudentManageDTO dto = dao.selectStdManage(std.getLectureseq(), std.getStdseq());

			System.out.printf("[%s]\n\n[학생 코드]\t[학생명]\n\n%s\t\t%s\n\n[회사명]\n\n%s\n\n", std.getLecturename(),
					std.getStdseq(), std.getStdname(), dto.getCompanyName());

			String company = scan.next(">>수정 회사명");

			if (!company.equals(dto.getCompanyName())) {

				int result = dao.updateOversight(std.getStdseq(), company);

				if (result > 0) {
					System.out.println("\n-> 수정 완료");
				} else {
					System.out.println("\n-> 수정 실패");
				}

			}

		} else if (cnt == 0) {
			System.out.println("찾으시는 자료는 없습니다.");
			out.pause();
		}

	}

	/**
	 * 교육생 관리 > 사후관리 > 사후처리 조회
	 */
	private void selectOversight() {

		Llist();

		String input = scan.next(">>선택");

		stdList(input);

		String seq = scan.next(">>학생 코드 입력");

		seq = "ST" + seq.substring(2);

		VwStudentDTO std = dao.vwfind2(input, seq);

		int cnt = dao.OversightCnt(std.getLectureseq(), std.getStdseq());

		if (cnt == 1) {
			StudentManageDTO dto = dao.selectStdManage(std.getLectureseq(), std.getStdseq());

			System.out.printf("[%s]\n\n[학생 코드]\t[학생명]\n\n%s\t\t%s\n\n[회사명]\n\n%s\n\n", std.getLecturename(),
					std.getStdseq(), std.getStdname(), dto.getCompanyName());

		} else if (cnt == 0) {
			System.out.println("찾으시는 자료는 없습니다.");
			out.pause();
		}

	}

	/**
	 * 교육생 관리 > 사후 관리 > 사후처리 등록
	 */
	private void insertOversight() {

		Llist();

		String input = scan.next(">>선택");

		stdList(input);

		String seq = scan.next(">>학생 코드 입력");

		seq = "ST" + seq.substring(2);

		VwStudentDTO std = dao.vwfind2(input, seq);

		int cnt = dao.OversightCnt(std.getLectureseq(), std.getStdseq());

		if (cnt == 0) {
			StudentManageDTO dto = new StudentManageDTO();

			String company = scan.next(">>회사 이름");

			dto.setCompanyName(company);
			dto.setCourseSeq(seq.substring(2));

			int result = dao.insertOversight(dto);

			if (result > 0) {

				System.out.println("\n-> 입력 성공\n\n");

				System.out.printf("[%s]\n\n[학생 코드]\t[학생명]\n\n%s\t\t%s\n\n[회사명]\n\n%s\n\n", std.getLecturename(),
						std.getStdseq(), std.getStdname(), dto.getCompanyName());

				out.pause();

			} else {
				System.out.println("\n-> 입력 실패");

				out.pause();
			}

		} else if (cnt == 1) {
			System.out.println("이미 처리된 정보 입니다.");
			out.pause();
		}

	}

	/**
	 * 교육생 관리 > 사후 관리 > 사후 처리 삭제
	 */
	private void deleteOversight() {

		Llist();

		String input = scan.next(">>선택");

		stdList(input);

		String seq = scan.next(">>학생 코드 입력");

		seq = "ST" + seq.substring(2);

		VwStudentDTO std = dao.vwfind2(input, seq);

		int cnt = dao.OversightCnt(std.getLectureseq(), std.getStdseq());

		if (cnt == 1) {
			StudentManageDTO dto = dao.selectStdManage(std.getLectureseq(), std.getStdseq());

			System.out.printf("[%s]\n\n[학생 코드]\t[학생명]\n\n%s\t\t%s\n\n[회사명]\n\n%s\n\n", std.getLecturename(),
					std.getStdseq(), std.getStdname(), dto.getCompanyName());

			out.pause();

			String answer = scan.next(">>해당 정보를 삭제 하시겠습니까?(Y/N)");

			answer = answer.toUpperCase();

			if (answer.equals("Y")) {

				int result = dao.deleteOversight(std.getStdseq());

				if (result > 0) {

					System.out.println("\n-> 삭제 완료\n");
					out.pause();

				} else {

					System.out.println("\n-> 삭제 실패\n");
					out.pause();

				}

			}

		} else if (cnt == 0) {
			System.out.println("찾으시는 자료는 없습니다.");
			out.pause();
		}

	}

	/**
	 * 상담일지 관리 main
	 */
	private void counseling() {

		while (true) {

			out.bigTitle(">>상담일지 관리<<");
			out.menu(out.COUNSELING);

			int input = scan.nextInt(">>선택");

			if (input == 1) {
				selectCouSnseling();
			} else if (input == 2) {
				insertCouSnseling();
			} else if (input == 3) {
				deleteCouSnseling();
			} else if (input == 4) {
				updateCouSnseling();
			} else if (input == 5) {

				MainClass.crumb.out();
				break;

			}

		}

	}

	/**
	 * 상담일지 관리 > 상담일지 수정
	 */
	private void updateCouSnseling() {

		Llist();

		int seq = scan.nextInt(">>선택");

		cousnselingStList(seq);

		String input = scan.next(">>학생 코드");

		input = "ST" + input.substring(2);

		if (input != null) {

			while (true) {

				VwStudentDTO std = dao.vwStd(input);

				ArrayList<CourseRecordDTO> list = dao.courseRecordStd(std.getStdseq());

				for (CourseRecordDTO courseRecord : list) {
					System.out.println(String.format("[%s] \n\n[학생 코드]\t[학생명]\t[상담 날짜]\n%s\t\t%s\t%s\n[상담 내용]\n%s",
							std.getLecturename(), std.getStdseq(), std.getStdname() + "\t",
							courseRecord.getCounseRegdate(), courseRecord.getCounseContents()));
				}
				break;

			}

		} else {
			System.out.println("잘못된 입력입니다.");
		}

		System.out.println();
		System.out.println("수정할 날짜를 입력해주세요(취소-> 0)");
		System.out.println();

		String data = scan.next(">>날짜 입력");

		if (!data.equals("0")) {

			String contents = scan.next(">>상담 내용");

			int result = dao.updateCourseRecord(input, data, contents);

			if (result > 0) {

				System.out.println("\n-> 수정 완료");
				System.out.println();
				out.pause();

			}

		} else {

			System.out.println("-> 취소");

		}

	}

	/**
	 * 상담일지 관리 > 상담일지 삭제
	 */
	private void deleteCouSnseling() {

		Llist();

		int seq = scan.nextInt(">>선택");

		cousnselingStList(seq);

		String input = scan.next(">>학생 코드");

		input = "ST" + input.substring(2);

		if (input != null) {

			while (true) {

				VwStudentDTO std = dao.vwStd(input);

				ArrayList<CourseRecordDTO> list = dao.courseRecordStd(std.getStdseq());

				for (CourseRecordDTO courseRecord : list) {
					System.out.println(String.format("[%s] \n\n[학생 코드]\t[학생명]\t[상담 날짜]\n%s\t\t%s\t%s\n[상담 내용]\n%s",
							std.getLecturename(), std.getStdseq(), std.getStdname(), courseRecord.getCounseRegdate(),
							courseRecord.getCounseContents()));
				}
				break;

			}

		} else {
			System.out.println("잘못된 입력입니다.");
		}

		System.out.println();
		String data = scan.next(">>삭제할 날짜 입력(2000-01-01");

		String answer = scan.next(">>[" + data + "] 해당 날짜 상담 기록을 삭제하시겠습까?(Y/N)");

		answer = answer.toUpperCase();

		if (answer.equals("Y")) {

			int result = dao.deleteCourseRecord(data, input);

			if (result > 0) {
				System.out.println("-> 삭제 완료");
				out.pause();
			} else {
				System.out.println("-> 삭제 실패");
				out.pause();
			}

		}

	}

	/**
	 * 상담일지 관리 > 상담일지 등록
	 */
	private void insertCouSnseling() {

		System.out.println("상담일지 입력할 과정 선택해주세요.");

		Llist();

		String seq = scan.next(">>선택");

		stdList(seq);

		CourseRecord();

	}

	/**
	 * 교육생 수정
	 */
	private void stdUpdate() { // 교육생 수정

		StudentDTO std = new StudentDTO();
		while (true) {
			out.menu(out.STDUPDATE);

			int input = scan.nextInt(">>선택");

			if (input == 1) {

				Llist();

				String seq = scan.next(">>선택");

				stdList(seq);

				String name = scan.next(">>이름");

				std.setSTDName(name);

				int nameCheck = dao.stdName(name);// 교육생 존재 확인

				if (nameCheck > 0) {

					System.out.println("변경할 이름을 입력해주세요.");
					System.out.println();
					String nameUpdate = scan.next(">>이름");

					int result = dao.nameUpdate(name, nameUpdate);// 교육생 이름 수정

					if (result > 0) {
						System.out.println();
						System.out.println("->성공");
					}

				}
			} else if (input == 2) {
				String ssn = scan.next(">>주민번호");

				std.setSTDSsn(ssn);

				int ssnCheck = dao.stdSsn(ssn);

				if (ssnCheck > 0) {

					System.out.println("변경할 주민번호를 입력해주세요.");
					String ssnUpdate = scan.next(">>주민번호");

					int result = dao.ssnUpdate(ssn, ssnUpdate);// 교육생 주민번호 수정

					if (result > 0) {
						System.out.println("성공");
					}

				}
			} else if (input == 3) {
				String tel = scan.next(">>전화번호");

				std.setSTDTel(tel);

				int telCheck = dao.stdTel(tel); // 전화번호 존재 여부

				if (telCheck > 0) {

					System.out.println("변경할 전화번호를 입력해주세요.");
					String telUpdate = scan.next(">>전화번호");

					int result = dao.telUpdate(tel, telUpdate);// 교육생 전화번호 수정

					if (result > 0) {
						System.out.println("성공");
					}

				}
			} else if (input == 4) {
				MainClass.crumb.out();// 나가기
				break;

			}

		}

	}

	/**
	 * 교육생 삭제
	 * 
	 * @param seq 과정 코드
	 * @param name 교육생 이름
	 */
	private void stdDelete(String seq, String name) { // 교육생 삭제

		VwStudentDTO std = new VwStudentDTO();

		std.setStdname(name);

		int result = dao.stdCheck(seq, name);
		if (result > 0) {

			String input = scan.next(">>[" + name + "]" + "학생을 삭제 하시겠습니까?(y/n)");

			if (input.equals("y")) {

				int delete = dao.delete(seq, name);

				if (delete > 0) {

					System.out.println();
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

	/**
	 * 교육생 등록
	 * 
	 * @param seq 과정 코드
	 */
	private void stdInsert(String seq) {// 교육생 등록

		while (true) {
			String stdName = scan.next(">>이름");
			String stdSsn = scan.next(">>주민번호(뒷자리)");
			String stdTel = scan.next(">>전화번호");

			if (stdName != null || stdSsn != null || stdTel != null) {

				StudentDTO std = new StudentDTO();

				std.setSTDName(stdName);
				std.setSTDSsn(stdSsn);
				std.setSTDTel(stdTel);

				int result = dao.insert(std);// 교육생 등록

				if (result > 0) {

					int result2 = dao.add(seq);

					if (result2 > 0) {
						System.out.println();
						System.out.println("-> 등록완료");
						System.out.println();
						out.pause();
						MainClass.crumb.out();
						break;
					}

				}

			} else if (stdName == "0" || stdSsn == "0" || stdTel == "0") {
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

	/**
	 * 교육생 관리 전체 과정 출력
	 */
	private void Llist() { // 교육생 > 과정
		while (true) {

			ArrayList<LectureDTO> list = dao.Llist(MainClass.isAuth != null ? true : false);

			for (LectureDTO lecture : list) {
				out.data(new Object[] {

						lecture.getLectureSeq(), lecture.getLectuerName()

				});

			}
			break;

		}

	}
	
	/**
	 * 과정에 속한 학생 코드, 학생명 출력
	 * 
	 * @param seq 과정 코드
	 */
	private void stdList(String seq) {
	      
	      ArrayList<VwStudentDTO> list = dao.stdList(seq);
	      
	      int page = 1;
	      while (true) {
	         int onePage = 7;
	         int index = (page * onePage) - onePage;
	         out.header(new String[] { "[학생 코드]", "[학생명]" });
	         for (int i = index; i < index + onePage; i++) {
	            if (i >= list.size()) {
	               break;
	            }
	            out.data(new Object[] {
	                  list.get(i).getStdseq()+"\t",
	                  list.get(i).getStdname()
	            });
	         }
	         out.bar();
	     	for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}
	         System.out.println("(0:돌아가기)\t\t" + page + "/"
	               + (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
	         out.bar();
	         page = scan.nextInt(">>페이지");
	         out.bar();
	         if (page == 0) {
	            break;
	         }
	      }
	}

	/**
	 * 해당 과정에 속한 학생 상담 일지 등록
	 * 
	 * @param input 학생 코드
	 * 
	 */
	private void CourseRecord() {

		String input = scan.next(">>학생 코드");

		input = "ST" + input.substring(2);

		if (input != null) {
			VwStudentDTO std = dao.vwStd(input);

			System.out.println(String.format("[%s] \n\n[학생 코드]\t[학생명]\n%s\t\t%s", std.getLecturename(), std.getStdseq(),
					std.getStdname()));

			String date = scan.next(">>등록날짜(2001-01-01)");
			String content = scan.next(">>내용");

			CourseRecordDTO courseRecord = new CourseRecordDTO();

			courseRecord.setCounseRegdate(date);
			courseRecord.setCounseContents(content);
			courseRecord.setCourseSeq(std.getStdseq());

			int result = dao.insetCourseRecord(courseRecord);

			if (result > 0) {
				System.out.println("-> 입력 완료");
			} else {
				System.out.println("-> 입력 실패");
			}

		} else {
			System.out.println("잘못 입력되었습니다.");
		}

	}

	/**
	 *  현재 과정 진행중(강의중)인 과정을 제외한 등록 가능한 과정 출력
	 */
	private void insertList() {

		while (true) {
			ArrayList<LectureDTO> list = dao.insertList(MainClass.isAuth != null ? true : false);

			out.header(new String[] { "[번호]", "[과정]" });

			for (LectureDTO lecture : list) {
				out.data(new Object[] {

						lecture.getLectureSeq(), lecture.getLectuerName()

				});

			}
			break;

		}

	}
	
	/**
	 * 	해당 과정에 속해있는 학생 상담일지 조회
	 * 
	 * @param seq 과정 코드
	 * @param input 학생 코드
	 * 
	 */
	
	private void selectCouSnseling() {

	      Llist();

	      int seq = scan.nextInt(">>선택");

	      cousnselingStList(seq);

	      String input = scan.next(">>학생 코드");

	      input = "ST" + input.substring(2);

	      if (input != null) {

	         while (true) {

	            VwStudentDTO std = dao.vwStd(input);

	            ArrayList<CourseRecordDTO> list = dao.courseRecordStd(std.getStdseq());

	            for (CourseRecordDTO courseRecord : list) {
	               System.out.println(String.format("[%s] \n\n[학생 코드]\t[학생명]\t[상담 날짜]\n%s\t\t%s\t%s\n[상담 내용]\n%s",
	                     std.getLecturename(), std.getStdseq(), std.getStdname(), courseRecord.getCounseRegdate(),
	                     courseRecord.getCounseContents()));
	            }

	            out.pause();
	            break;
	         }

	      } else {
	         System.out.println("잘못된 입력입니다.");
	      }

	   }

	/**
	 * 상담일지에 등록된 해당 과정에 속한 학생 출력
	 * 
	 * @param seq 과정 코드
	 */
	
	private void cousnselingStList(int seq) {
	      
	      ArrayList<courseRecoardListDTO> list = dao.cousnselingList(seq);
	      
	      int page = 1;
	      while (true) {
	         int onePage = 7;
	         int index = (page * onePage) - onePage;
	         out.header(new String[] { "[학생 코드]", "[학생명]", "[날짜]" });
	         for (int i = index; i < index + onePage; i++) {
	            if (i >= list.size()) {
	               break;
	            }
	            out.data(new Object[] {
	                  list.get(i).getStdSeq()+"\t",
	                  list.get(i).getStdName()+"\t",
	                  list.get(i).getCounseregdate()
	            });
	         }
	         out.bar();
	     	for(int i =0 ; i<30; i++) {
				System.out.print(" ");
			}
	         System.out.println("(0:돌아가기)\t\t" + page + "/"
	               + (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
	         out.bar();
	         page = scan.nextInt(">>페이지");
	         out.bar();
	         if (page == 0) {
	            break;
	         }
	      }

	}

	/**
	 * 해당 과정에 학생 존재하는지 조회
	 * 
	 * @param seq 과정 코드
	 * @param name 학생 명
	 */
	
	private void stdFind(String seq, String name) {

		int count = dao.find(seq, name);

		if (count > 0) {

			VwStudentDTO result = dao.vwfind(seq, name);

			System.out.printf("[이름] : %s(%s)\n", result.getStdname(), result.getStdseq());
			System.out.printf("[과정명] : %s\n", result.getLecturename());
			System.out.printf("[과정] : %s ~ %s\n", result.getLecturestartdate(), result.getLectureenddate());
			System.out.printf("[강의실] : %s강의실\n", result.getClassseq());

		} else {
			System.out.println("해당 학생을 찾을 수 없습니다.");
		}

	}

	// ----------------------------------------------------------------------------------------------------교육생
	// 관리 끝
	// -------------------------------------------------------------------- ↓5. 시험관리

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

			out.header(new String[] { "[과목번호]", "[과목명]" });

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

			out.header(new String[] { "[과목번호]", "[과목명]" });

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

			out.header(new String[] { "[과목번호]", "[과목명]" });

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

		String studentName = scan.next(">>학생 명");
		String studentSeq = scan.next(">>학생 코드");

		StudentGradeInfoDTO studentgradeinfo = new StudentGradeInfoDTO();

		studentgradeinfo.setSTDseq(studentSeq);
		studentgradeinfo.setSTDName(studentName);

		// 과목코드, 과목명 목록 출력
		ArrayList<SubjectDTO> list = dao.studentSubjectList(studentgradeinfo.getSTDseq(),
				studentgradeinfo.getSTDName());

		out.bigTitle(">>현 수강중인 과목 목록<<");

		out.header(new String[] { "[과목번호]", "[과목명]" });

		for (SubjectDTO dto : list) {
			out.data(new Object[] { dto.getSubjectSeq() + "\t", dto.getSubjectName() });
		}

		System.out.println();
		String input = scan.next(">>선택");

		studentgradeinfo = dao.studentGradeInfo(studentgradeinfo.getSTDseq(), studentgradeinfo.getSTDName(), input);

		out.header(new String[] { "[학생 코드]", "[학생 명]", "[필기점수]", "[실기점수]", "[출석점수]", "[과정명]", "[과목명]" });
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

				out.header(new String[] { "[문제번호]", "[문항배점]", "[문제]" });

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

				out.header(new String[] { "[문제번호]", "[문항배점]", "[문제]" });

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
	      
	      int page = 1;
	      while (true) {
	         int onePage = 7;
	         int index = (page * onePage) - onePage;
	      
	         // 과정 리스트 출력 -> 전체 출력
	         out.header(new String[] { "과정 명\t\t", "학생 코드\t", "학생 명" });
	         
	         for (int i = index; i < index + onePage; i++) {
	            if (i >= lectureList.size()) {
	               break;
	            }

	            out.data(new Object[] {lectureList.get(i).getLectureName()+"\t",
	                  lectureList.get(i).getSTDSeq(),
	                  lectureList.get(i).getSTDName()});
	         }
	         
	         out.bar();
	         System.out.println("(0:돌아가기)\t\t" + page + "/"
	               + (lectureList.size() % onePage == 0 ? lectureList.size() / onePage : lectureList.size() / onePage + 1));
	         out.bar();
	         page = scan.nextInt("페이지");
	         out.bar();
	         if (page == 0) {
	            break;
	         }
	      }
	   }
}
