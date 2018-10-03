package com.gm.academy.admin;



import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
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

			}else if(input == 3) {
				
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

}
