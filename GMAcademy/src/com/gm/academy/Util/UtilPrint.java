package com.gm.academy.Util;

import java.util.Scanner;
import com.gm.academy.MainClass;
import com.gm.academy.teacher.TeacherController;

/**
 * 유틸프린트
 * @author sist
 *
 */
public class UtilPrint {
	/**
	 * 롱
	 */
	public final static int LONG = 80;
	/**
	 * 숏
	 */
	public final static int SHORT = 40;
	/**
	 * 메인
	 */
	public final static String[] MAIN = {"로그인","찾기(ID/PW)","학원현황","종료하기"};
	/**
	 * 학생로그인
	 */
	public final static String[] STUDENT_LOGIN = {"성적조회","출결","교사평가","상담일지","로그아웃"};
	/**
	 * 학생성적
	 */
	public final static String[] STUDNET_GRADE = {"필기 성적 조회","실기 성적 조회","출결 성적 조회","전체 성적 조회","돌아가기"};
	/**
	 *  학생출결
	 */
	public final static String[] STUDENT_ATTENDACE = {"입실등록","퇴실등록","출결현황","돌아가기"};
	/**
	 * 교사평가
	 */
	public final static String[] TEACHER_EVALUATION = {"교사평가등록","돌아가기"};
	/**
	 * 평가
	 */
	public final static String[] TEREGISTER = {"과정평가","과목평가"};
	/**
	 * 상담일지
	 */
	public final static String[] CUNSULTING = {"상담일지 조회","돌아가기"};
	
	//teacher
	/**
	 * 교사로그인
	 */
	public final static String[] TEACHER_LOGIN= {"강의스케줄 조회","시험관리","성적관리","출결 현황 조회","교사 평가 조회","상담일지","로그아웃"};
	/**
	 * 교사 의 출결조회
	 */
	public final static String[] TEACHER_ATTENDANCE = {
		"기간별 조회","과정별 조회","돌아가기"	
	};
	
	//관리자
	/**
	 * 관리자로그인
	 */
	public final static String[] ADMIN_LOGIN = {"교사 계정 관리","개설 과정 관리","개설 과목 관리","교육생 관리","시험관리 및 성적조회","출결관리 및 출결조회","교재관리","추천회사 관리","로그","로그아웃"};
	/**
	 * 
	 */
	public final static String[] TEXTBOOK_MANAGEMENT = {"교재 사용 현황","교재 신청서 작성","돌아가기"};
	/**
	 * 
	 */
	public final static String[] TEXTBOOK_STATUS = {"전체조회","과정조회","돌아가기"};
	/**
	 * 
	 */
	public final static String[] SEARCH_ID_PW = {"ID찾기","PW찾기","돌아가기"};
	/**
	 * 
	 */
	public final static String[] USER_CHOICE = {"교사","교육생","돌아가기"};
	/**
	 * 
	 */
	public final static String[] ADMINTEACHERMANAGEMENT = {"교사 재직 현황", "교사 등록","교사 삭제","교사 정보 수정", "돌아가기"};
	/**
	 * 
	 */
	public final static String[] ADMINTLECTUREMANAGEMENT = {"과정 현황 조회", "과정 등록", "과정 삭제", "과정 수정", "과정 검색", "돌아가기"};
	/**
	 * 
	 */
	public final static String[] ADMINTEACHERUPDATEMENU = {"이름 수정","전화번호 수정","돌아가기"};
	
	/**
	 * 
	 */
	public final static String[] ADMIN_TEACHERMANAGEMENT = {"교사 재직 현황", "교사 등록", "교사 삭제", "교사 정보 수정", "돌아가기"};   //관리자_교사계정 관리
	/**
	 * 
	 */
	public final static String[] ADMIN_LECTUREMANEGEMENT = {"과정 현황 조회", "과정 등록", "과정 삭제", "과정 수정", "과정 검색", "돌아가기"};   //관리자_개설과정 관리
	/**
	 * 
	 */
	public final static String[] ADMIN_SUBJECTMANAGEMENT = {"과목 현황 조회", "과목 등록", "과목 삭제", "과목 수정", "돌아가기"};      //관리자_개설과목 관리
	/**
	 * 
	 */
	public final static String[] ADMIN_STUDENTMANAGEMENT = {"교육생 조회", "교육생 등록", "교육생 삭제", "교육생 수정", "상담일지 관리", "사후관리", "돌아가기"};   //관리자_교육생 관리
	/**
	 * 
	 */
	public final static String[] ADMIN_TESTMANAGEMENT = {"시험 정보 조회", "시험 정보 수정하기", "학생별 성적 조회", "성적 수정하기", "돌아가기"};      //관리자_시험관리 및 성적조회
	/**
	 * 
	 */
	public final static String[] ADMIN_ATTENDMANAGEMENT = {"과정별 출결조회", "기간별 출결조회", "학생 출결조회", "출결 수정하기", "돌아가기"};      //관리자_출결관리 및 출결조회
	/**
	 * 
	 */
	public final static String[] ADMIN_TEXTBOOKMANAGEMENR = {"교재 사용현황", "교재 신청서 작성", "돌아가기"};      //관리자_교재관리
	/**
	 * 
	 */
	public final static String[] ADMIN_UPDATETEST = {"문제 수정", "배점 수정"};

	/**
	 * 
	 */
	public final static String[] ADMINSTUDENT = {"교육생 조회","교육생 등록","교육생 삭제","교육생 수정","상담일지 관리","사후관리","돌아가기"};//교육생관리
	/**
	 * 
	 */
	public final static String[] STDUPDATE = {"학생명","주민번호","전화번호","돌아가기"};
	/**
	 * 
	 */
	public final static String[] COUNSELING = {"상담일지 조회","상담일지 등록","상담일지 삭제","상담일지 수정","돌아가기"};
	/**
	 * 
	 */
	public final static String[] OVERSIGHT = {"사후처리 조회","사후처리 등록","사후처리 삭제","사후처리 수정","돌아가기"};
    
	/**
	 * 
	 */
	public final static String[] ADMIN_LECTURE_MANAGEMENT = {"과정 현황 조회","과정 등록","과정 삭제","과정 수정","강의_과목 등록","돌아가기"};
	/**
	 * 
	 */
	public final static String[] ADMIM_LETUREUPDATE = {"과정 명 수정","과정 날짜 수정","과정 상태 수정","인원수 수정","강의실 수정","교사 코드 수정","돌아아기"}; //관리자_과정 수정
	/**
	 * 
	 */
	public final static String[] ADMIN_UPDATEGRADE = {"필기점수 수정", "실기점수 수정", "출석점수 수정"};
	/**
	 * 
	 */
	public final static String[] ADMIN_UPDATETESTQUEST = {"필기문제 수정","실기문제 수정"};
	/**
	 * 
	 */
	public final static String[] LOG_MENU = {"에러로그","로그인로그","로그","돌아가기"};
	
	/**
	 * 
	 */
	public final static String[] LOGIN_LOG = {"관리자 로그인 로그","교사 로그인 로그","교육생 로그인 로그","돌아가기"};
	//일자 menu
	 /**
	  * 메뉴 출력메소드
	  * @param list 출력할 메뉴의 배열
	  */
	public void menu(String[] list) {
		
		for(int i=0; i<list.length; i++) {
			for(int j =0 ; j<30; j++) {
				System.out.print(" ");
			}
			System.out.printf("%d. %s\n",(i+1),list[i]);
		}
	}
	
	// - 선긋기
	/**
	 * 선긋기 메소드
	 */
	public void bar() {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		for(int i=0; i<80; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	// - 선긋기
	/**
	 * 길이만큼 선긋는 메소드
	 * @param size 길이
	 */
	public void bar(int size) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		for(int i=0; i<size; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	// - 선긋기
	/**
	 * 길이만큼 선 긋는 메소드
	 * @param size 길이
	 */
	public void bar2(int size) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		for(int i=0; i<size; i++) {
			System.out.print("=");
		}
		System.out.println();
	}
	
	//일시정지 label 입력
	/**
	 * 멘트를 넣고 일시정지하는 메소드
	 * @param label 멘트
	 */
	public void pause(String label) {
		for(int i =0 ;i<10; i++) {
			System.out.println();
		}
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		System.out.println(label);
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	
	//일시정지 엔터키 입력
	/**
	 * 일시정지 엔터키 입력하는 메서드
	 */
	public void pause() {
		for(int i =0 ;i<10; i++) {
			System.out.println();
		}
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		System.out.println("★계속하려면 엔터키를 입력하세요.★");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	
	//제일 큰 타이틀
	/**
	 * 제목을 출력하는 메소드
	 * @param label 제목
	 */
	public void bigTitle(String label) {
		for(int i =0; i<50; i++) {
			System.out.println();
		}
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		MainClass.crumb.now();
		
		
		line(UtilPrint.LONG);
		for(int i=0; i<(UtilPrint.LONG/2)-label.length(); i++) {
			System.out.printf(" ");
		}
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		System.out.println(label);
		line(UtilPrint.LONG);
		if(TeacherUtil.loginTeacher!=null&&MainClass.crumb.size()>=2) {
			TeacherUtil.myPage();
			bar();
		}
	}
	
	//사용자 접속중
	/**
	 * 데이터의 헤더를 출력하는 메서드
	 * @param labels 헤더 배열
	 */
	public void header(String[] labels) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		
		for(String label : labels) {
			if(labels.equals("\t")) {
				System.out.printf("%s\t",label);
				continue;
			}
			System.out.printf("%s\t",label);
		}
		System.out.println();
	}
	
	//리스트 프린트 할떄
	/**
	 * 데이터를 출력하는 메소드
	 * @param datas 오브젝트 배열(거의 스트링)
	 */
	public void data(Object[] datas) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		for(Object data : datas) {
			System.out.printf("%s\t",data);
		}
		System.out.println();
	}
	
	//결과 원하는 msg입력
	/**
	 * 결과메시지를 받아 출력하는 메소드
	 * @param msg 결과메시지
	 */
	public void result(String msg) {
		
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		System.out.println("[결과]"+ msg);
		System.out.println();
	}
	
	//1일때 결과 출력 0일때 결과 미출력
	/**
	 * 결과를 받아 출력하는 메소드
	 * @param result 0 : 실패 1 : 결과
	 * @param msg
	 */
	public void result(int result, String msg) {
		
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		if(result > 0) {
			System.out.println("[결과]"+ msg);
			System.out.println();
		} else {
			System.out.println("★실패했습니다.  관리자에게 문의해주세요★");
		}
	}
	
	/**
	 * 길이만큼 라인을 출력하는 메소드
	 * @param size 길이
	 */
	public void line(int size) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		for(int i=0; i<size; i++) {
			System.out.print("=");
		}
		System.out.println();
	}
}
















