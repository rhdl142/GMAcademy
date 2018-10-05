package com.gm.academy;

import java.util.ArrayList;

import com.gm.academy.Util.BreadCrumb;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
import com.gm.academy.admin.AdminController;
import com.gm.academy.admin.AdminDAO;
import com.gm.academy.exam.ExamController;
import com.gm.academy.student.StudentController;
import com.gm.academy.teacher.TeacherController;

/*
변수명
Controller  > AdminController - adminC
DTO > ErrorLogDTO - elDTO
DAO > StudentDAO - sDAO
*/

public class MainClass {
	private static UtilScanner scan;
	private static UtilPrint out;
	private static AdminController adminC;
	private static StudentController studentC;
	private static TeacherController teacherC;

	private static AdminDAO dao;

	//프로그램 실행 중 어디서든 항상 사용할 자원 필요 > main() 소유 클래스의 정적 public 변수 생성

	public static BreadCrumb crumb;

	public static String isAuth;  //seq (ST1)

	public static String tel;
	public static String name;
	public static String lectureName;
	public static String lectureDate;
	public static String lectureSeq;
	public static String tchSeq;
	public static String courseSeq;


	static {
		adminC = new AdminController();
		studentC = new StudentController();
		teacherC = new TeacherController();
		scan = new UtilScanner();
		out = new UtilPrint();
		crumb = new BreadCrumb();
		dao = new AdminDAO();
		isAuth = null;
		tel = null;
		name = null;
		lectureName = null;
		lectureDate = null;
		lectureSeq = null;
		tchSeq = null;
		courseSeq = null;
	}

	public static void main(String[] args) {
		MainClass.crumb.in("메인");

		while (true) {
			out.bigTitle("GMAcademy 시작");
			out.menu(UtilPrint.MAIN); 
			int input = 0;
			try {
				input = scan.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다 .신중히 입력해주시기 바랍니다.");
			}

			if (input == 1) {
				MainClass.crumb.in("회원");
				login();
				MainClass.crumb.out();
			} else if (input == 2) {
				MainClass.crumb.in("찾기(ID/PW)");

				MainClass.crumb.out();
			} else if (input == 3) {
				MainClass.crumb.in("학원현황");
				academyStatus();
				MainClass.crumb.out();
			} else if (input == 5) {
				break;
			} else {
				out.result("잘못입력하셨습니다. 다시 입력해주시기 바랍니다.");
				continue;
			}
		}

		MainClass.crumb.out();

		out.result("GMAcademy 종료");
	}


	// 로그아웃
	private static void academyStatus() {
		while (true) {
			out.bigTitle("학원 현황");
			out.menu(new String[] { "수강 현황 조회", "수료율 조회", "취업률 조회", "강의 과목 안내", "추천 회사 조회", "돌아가기" });
			out.bar();
			int input = scan.nextInt("선택");
			if (input == 1) {// 수강현황조회
				MainClass.crumb.in("수강현황조회");
				showCourseStatus();
				MainClass.crumb.out();
			} else if (input == 2) {// 수료율 조회
				MainClass.crumb.in("수료율 조회");
				completionRate();
				MainClass.crumb.out();
			} else if (input == 3) {// 취업률 조회
				MainClass.crumb.in("취업률 조회");
				showEmploymentRate();
				MainClass.crumb.out();
			} else if (input == 4) {// 강의 과목 안내
				MainClass.crumb.in("강의 과목 안내");
				subjectInformation();
				MainClass.crumb.out();
			} else if (input == 5) { // 추천회사 조회
				MainClass.crumb.in("추천 회사 조회");
				showRecommendedCompany();
				MainClass.crumb.out();
			} else if (input == 6) {
				break;
			} else {
				out.result("잘못입력하셨습니다. 다시 입력해주시기 바랍니다.");
				continue;
			}
		}
	}

	private static void search() {
		MainClass.crumb.in("ID/PW 찾기");
		
		String name = null;
		String tel = null;
		
		while(true) {
			out.bigTitle("ID/PW 찾기");
			
			out.menu(UtilPrint.SEARCH_ID_PW);
			int input = scan.nextInt("선택");
			
			if(input == 1) {
				out.bigTitle("ID 찾기");
				
				MainClass.crumb.in("ID찾기");
				
				out.menu(UtilPrint.USER_CHOICE);
				input = scan.nextInt("선택");
				
				name = scan.next("이름");
				tel = scan.next("전화번호");
				
				if(input == 1) {
					teacherC.search(name,tel,1);
				} else if(input == 2) {
					
				}
				
				MainClass.crumb.out();
				
				out.pause();
			} else if(input == 2) {
				out.bigTitle("PW 찾기");
				
				MainClass.crumb.in("PW찾기");
				
				out.menu(UtilPrint.USER_CHOICE);
				input = scan.nextInt("선택");

				name = scan.next("이름");
				tel = scan.next("코드");
				
				if(input == 1) {
					teacherC.search(name,tel,2);
				} else if(input == 2) {
					studentC.search(name, tel, 1);
				}
				
				MainClass.crumb.out();
				
				out.pause();
			} else if(input == 3) {
				break;
			} else {
				out.result("잘못입력하였습니다.");
			}
		}
		
		MainClass.crumb.out();		
	}

	private static void login() {
		AdminController adminC = new AdminController();
		StudentController studentC = new StudentController();
		TeacherController teacherC = new TeacherController();

		String id = scan.next("아이디 : ");
		String pw = scan.next("비밀번호 : ");
		
		if(id.contains("ST")) {
			studentC.main(id,pw);
		} else if(id.contains("TC")) {
			teacherC.main(id,pw);
		} else {
			adminC.main(id,pw);
		}
	}

	// 로그인
	private static void completionRate() {
		while (true) {
			out.bigTitle("수료율 조회");
			out.header(new String[] { "[과정번호]", "[강사명]", "[시작일]", "  ", "[종료일]", "  ", "[과정명]" });
			ArrayList<Object[]> olist = dao.getAllLecture();
			for (int i = 0; i < olist.size(); i++) {
				out.data(olist.get(i));
			}
			out.bar();
			String sel = scan.next("과정번호 선택");
			olist = dao.getCompletionRate(sel);
			out.bar();

			try {
				out.header(new String[] { "[과정명]", "\t", "[수료율]", "[중도탈락]" });
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
			} catch (Exception e) {
				System.out.println("데이터가 없는 과정입니다.");
			}
			out.pause();
			break;
		}
	}

	private static void showEmploymentRate() {
		while (true) {
			out.bigTitle("취업률 조회");
			out.header(new String[] { "[과정번호]", "[강사명]", "[시작일]", "  ", "[종료일]", "  ", "[과정명]" });
			ArrayList<Object[]> olist = dao.getAllLecture();
			for (int i = 0; i < olist.size(); i++) {
				out.data(olist.get(i));
			}
			out.bar();

			String sel = scan.next("과정번호 선택");
			olist = dao.getEmploymentRate(sel);
			out.bar();

			try {
				out.header(new String[] { "[과정명]", "\t", "[취업률]" });
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
			} catch (Exception e) {
				System.out.println("데이터가 없는 과정입니다.");
			}

			out.pause();
			break;
		}
	}

	private static void subjectInformation() {
		while (true) {
			out.bigTitle("강의 과목 안내");
			out.menu(new String[] { "과정 별 안내", "교사 별 안내", "돌아가기" });
			int input = scan.nextInt("선택");
			if (input == 1) { // 과정 별 안내
				MainClass.crumb.in("과정 별 안내");
				out.bigTitle("과정별 안내");
				out.header(new String[] { "[번호]", "[강사명]", "[과정명]" });
				ArrayList<Object[]> olist = dao.getLectureAndTeacher();
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				String seq = scan.next("과정번호 선택");
				out.bar();
				olist = dao.getSubject(seq);
				out.header(new String[] { "[과정명]", "\t", "[시작일]", "  ", "[종료일]", "  ", "[과목명]" });
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				out.pause();
				MainClass.crumb.out();
			} else if (input == 2) { // 교사 별안내
				MainClass.crumb.in("교사별 안내");
				out.bigTitle("교사별 안내");
				out.header(new String[] { "[번호]", "[교사명]" });
				ArrayList<Object[]> olist = dao.getAllTeacher();
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				String seq = scan.next("교사번호 선택");
				out.bar();
				out.header(new String[] { "[강사명]", "[과정명]", "\t", "[현황]", "[과목명]" });
				olist = dao.getSubject(seq);
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				out.pause();
				MainClass.crumb.out();
			} else if (input == 3) { // 돌아가기
				break;
			} else {
				System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
				continue;
			}
		}
	}

	/**
	 * 추천회사 조회 메소드
	 */
	public static void showRecommendedCompany() {
		out.bigTitle("추천회사 조회하기");
		ArrayList<Object[]> olist = dao.getRecommendedCompany();
		int page = 1;
		int count = 0;
		while (true) {
			try {
				if (count != 0) {
					page = scan.nextInt("페이지");
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
			out.header(new String[] { "[번호]", "[회사명]", "[급여]", "[위치]" });
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
			count++;
		}
	}

	/**
	 * 수강현황을 조회하는 메소드
	 */
	private static void showCourseStatus() {
		while (true) {
			out.bigTitle("수강현황 조회");
			out.menu(new String[] { "지난 과정 보기", "현재 과정 보기", "예정 과정 보기", "돌아가기" });
			out.bar();
			int input = scan.nextInt("선택");
			if (input == 1) {// 지난과정보기
				out.bigTitle("지난과정보기");
				out.header(new String[] { "[시작일]", "  ", "[종료일]", "  ", "[교사명]", "[과정명]" });
				ArrayList<Object[]> olist = dao.getLecture("강의종료");
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				out.pause();
				break;
			} else if (input == 2) {// 현재과정 보기
				out.bigTitle("현재과정보기");
				out.header(new String[] { "[시작일]", "  ", "[종료일]", "  ", "[교사명]", "[과정명]" });
				ArrayList<Object[]> olist = dao.getLecture("강의중");
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				out.pause();
				break;
			} else if (input == 3) {// 예정 과정보기
				out.bigTitle("예정과정보기");
				out.header(new String[] { "[시작일]", "  ", "[종료일]", "  ", "[교사명]", "[과정명]" });
				ArrayList<Object[]> olist = dao.getLecture("강의예정");
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				out.pause();
				break;
			} else if (input == 4) {// 돌아가기
				// 돌아가기
				break;
			} else {
				out.result("잘못입력하였습니다. 다시 입력해주시기 바랍니다.");
			}
		}

	}
}
