package com.gm.academy.Util;

import java.util.Scanner;

import com.gm.academy.admin.AdminDAO;

/**
 * 유틸스캐너
 * @author 3조
 *
 */
public class UtilScanner {
	private Scanner scan;
	private UtilPrint out; 
	private AdminDAO dao;
	
	public UtilScanner() {
		 scan = new Scanner(System.in);
		 out = new UtilPrint();
		 dao = new AdminDAO();
	}
	
	//String입력
	/**
	 * 입력제목을 받아 스캐너를 실행하는 메소드
	 * @param label 입력제목
	 * @return
	 */
	public String next(String label) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		try {
			System.out.printf(label+" :");
			return scan.nextLine();	
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("입력에러  ","UtilPrint.next()");
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("알수없는에러","UtilPrint.next()");
		}
		return null;	
	}
	
	//String 다음칸 입력	
	/**
	 * 입력제목을 받아 라인을 띄우고 스캐너를 실행하는 메소드
	 * @param label 입력제목
	 * @return
	 */
	public String nextln(String label) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		try {
			System.out.println(label+" :");
			return scan.nextLine();	
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("입력에러  ","UtilPrint.nextln()");
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("알수없는에러","UtilPrint.nextln()");
		}
		return null;		
	}
	
	//Int입력
	/**
	 * 인트입력
	 * @param label 라벨
	 * @return
	 */
	public int nextInt(String label) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		try {
			System.out.print(label+" :");
			return Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("입력에러  ","UtilPrint.nextInt()");
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("알수없는에러","UtilPrint.nextInt()");
		}
		return 0;	
	}
	
	//Int 다음칸 입력
	/**
	 * 인트 다음칸 입력
	 * @param label 라벨
	 * @return 스캐너
	 */
	public int nextIntln(String label) {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		try {
			System.out.println(label+" :");
			return Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("입력에러  ","UtilPrint.nextIntln()");
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("알수없는에러","UtilPrint.nextIntln()");
		}
		return 0;			
	}
	
	/**
	 * 다음칸 STring 입력
	 * @return 스캐너
	 */
	public String nextLine() {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		try {
			return scan.nextLine();
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("입력에러  ","UtilPrint.nextLine()");
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("알수없는에러","UtilPrint.nextLine()");
		}
		return null;	
	}
	
	/**
	 * 인트 다음칸 입력
	 * @return 스캐너
	 */
	public int nextInt() {
		for(int i =0 ; i<30; i++) {
			System.out.print(" ");
		}
		try {
			return Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException ne) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("입력에러  ","UtilPrint.nextInt()");
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			dao.systemError("알수없는에러","UtilPrint.nextInt()");
		}
		return 0;
	}
}
