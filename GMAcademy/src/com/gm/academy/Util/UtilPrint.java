package com.gm.academy.Util;

import java.util.Scanner;
import com.gm.academy.MainClass;

public class UtilPrint {
	
	public final static int LONG = 80;
	public final static int SHORT = 40;
	public final static String[] MAIN = {"로그인","찾기(ID/PW)","로그아웃","종료하기"};
	public final static String[] STUDENT_LOGIN = {"성적조회","출결","교사평가","상담일지","돌아가기"};
	public final static String[] STUDNET_GRADE = {"필기 성적 조회","실기 성적 조회","출결 성적 조회","전체 성적 조회","돌아가기"};
	public final static String[] STUDENT_ATTENDACE = {"입실등록","퇴실등록","출결현황","돌아가기"};
	public final static String[] TEACHER_EVALUATION = {"교사평가등록","돌아가기"};
	public final static String[] TEREGISTER = {"과정평가","과목평가"};
	public final static String[] CUNSULTING = {"상담일지 조회","돌아가기"};
	public final static String[] SEARCH_ID_PW = {"ID찾기","PW찾기","돌아가기"};
	public final static String[] USER_CHOICE = {"교사","교육생","돌아가기"};
	
	//일자 menu
	public void menu(String[] list) {
		for(int i=0; i<list.length; i++) {
			System.out.printf("%d. %s\n",(i+1),list[i]);
		}
	}
	
	// - 선긋기
	public void bar() {
		for(int i=0; i<80; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	//일시정지 label 입력
	public void pause(String label) {
		System.out.println(label);
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	
	//일시정지 엔터키 입력
	public void pause() {
		System.out.println("★계속하려면 엔터키를 입력하세요.★");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	
	//제일 큰 타이틀
	public void bigTitle(String label) {
		MainClass.crumb.now();
		
		line(UtilPrint.LONG);
		for(int i=0; i<(UtilPrint.LONG/2)-label.length(); i++) {
			System.out.printf(" ");
		}
		System.out.println(label);
		line(UtilPrint.LONG);
	}
	
	//중타이틀
	public void middleTitle(String label) {
		MainClass.crumb.now();
		
		line(UtilPrint.SHORT);
		for(int i=0; i<(UtilPrint.SHORT/2)-label.length(); i++) {
			System.out.printf(" ");
		}
		System.out.println(label);
		line(UtilPrint.SHORT);
	}
	
	//사용자 접속중
	public void header(String[] labels) {
		for(String label : labels) {
			System.out.printf("[%s]\t",label);
		}
		System.out.println();
	}
	
	//리스트 프린트 할떄
	public void data(Object[] datas) {
		for(Object data : datas) {
			System.out.printf("%s\t",data);
		}
		System.out.println();
	}
	
	//결과 원하는 msg입력
	public void result(String msg) {
		System.out.println("[결과]"+ msg);
		System.out.println();
	}
	
	//1일때 결과 출력 0일때 결과 미출력
	public void result(int result, String msg) {
		if(result > 0) {
			System.out.println("[결과]"+ msg);
			System.out.println();
		} else {
			System.out.println("★실패했습니다.  관리자에게 문의해주세요★");
		}
	}
	
	public void line(int size) {
		for(int i=0; i<size; i++) {
			System.out.print("=");
		}
		System.out.println();
	}
}
















