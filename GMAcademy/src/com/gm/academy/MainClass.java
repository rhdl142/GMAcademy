package com.gm.academy;

import com.gm.academy.Util.BreadCrumb;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
import com.gm.academy.admin.AdminController;
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

	//프로그램 실행 중 어디서든 항상 사용할 자원 필요 > main() 소유 클래스의 정적 public 변수 생성
	public static BreadCrumb crumb;
	public static String isAuth;
	public static String name;
	public static String seq;
	public static boolean isAdmin;//관리자? 유저?
	
	static {
		scan = new UtilScanner();
		out = new UtilPrint();
		crumb = new BreadCrumb();
		isAuth = null;
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



















