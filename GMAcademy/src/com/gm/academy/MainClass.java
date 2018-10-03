package com.gm.academy;

import com.gm.academy.Util.BreadCrumb;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
import com.gm.academy.admin.AdminController;
import com.gm.academy.admin.AdminDAO;
import com.gm.academy.exam.ExamController;
import com.gm.academy.lecture.LectureController;
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
	private static ExamController examC;
	private static LectureController lectureC;
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
		ExamController examC = new ExamController();
		LectureController lectureC = new LectureController();
		
		MainClass.crumb.in("메인");
		
		while(true) {
			out.bigTitle("GMAcademy 시작");
			out.menu(UtilPrint.MAIN);
			int input = scan.nextInt("선택");
			
			if(input == 1) {
				MainClass.crumb.in("로그인");
				login();
				MainClass.crumb.out();
			} else if(input == 2) {
				MainClass.crumb.in("찾기(ID/PW)");
				search();
				MainClass.crumb.out();
			} else if(input == 3) {
				MainClass.crumb.in("로그아웃");
				logout();
				MainClass.crumb.out();
			} else {
				break;
			}
		}
		
		MainClass.crumb.out();
		
		out.result("GMAcademy 종료");
	}

	private static void logout() {
		
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
}



















