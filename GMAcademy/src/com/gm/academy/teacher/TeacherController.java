package com.gm.academy.teacher;

import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.Util.UtilScanner;
import com.gm.academy.admin.DistributionDTO;
import com.gm.academy.exam.GradeDTO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.student.StudentDTO;

public class TeacherController {

	private UtilScanner scan;
	private UtilPrint out;
	private TeacherDAO tdao;

	public TeacherController() {
		this.scan = new UtilScanner();
		this.out = new UtilPrint();
		this.tdao = new TeacherDAO();
	}

	/**
	 * 아이디와 비밀번호를 받아 교사측의 주메소드
	 * 
	 * @param id 로그인아이디
	 * @param pw 로그인 비밀번호
	 */
	public void main(String id, String pw) {
		TeacherUtil.distribution = tdao.getDistribution();
		while (check(id, pw) == 1) {
			MainClass.isAuth = TeacherUtil.loginTeacher.getTCHName();
			out.bigTitle("교사");

			out.menu(UtilPrint.TEACHER_LOGIN);
			int input = 0;
			try {
				input = scan.nextInt("선택");
			} catch (NumberFormatException e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
			}
			out.bar();
			if (input == 1) {
				MainClass.crumb.in("강의스케줄 조회");// ---------------
				showSchedule();
				MainClass.crumb.out();
			} else if (input == 2) {
				MainClass.crumb.in("시험관리");
				examManagement();
				MainClass.crumb.out();
			} else if (input == 3) {
				MainClass.crumb.in("성적관리");// -------------------
				gradeManagement();
				MainClass.crumb.out();
			} else if (input == 4) {
				MainClass.crumb.in("출결현황조회");// ----------------
				Attendance();
				MainClass.crumb.out();
			} else if (input == 5) {
				MainClass.crumb.in("교사평가조회");// -----------------
				teacherEvaluation();
				MainClass.crumb.out();
			} else if (input == 6) {
				MainClass.crumb.in("상담일지");// ---------------------
				consulting();
				MainClass.crumb.out();
			} else if (input == 7) {
				break;
			}

			else {
				out.result("잘못입력하였습니다.");
			}

		}
		if (check(id, pw) != 1) {
			out.pause("아이디와 비밀번호가 일치하지 않습니다. 신중히 입력해주세요.");
		}

	}

	private int check(String id, String pw) {
		int result = tdao.login(id, pw);
		return result;
	}

	private void showSchedule() { // -----------------------------------------------------------강의스케줄조회
		out.bigTitle("강의스케줄 조회");

		// 과정header
		out.header(new String[] { " 과정명\t", "시작날짜   ", "종료날짜 ", "강의실", "수강인원" });
		// 과정data
		ArrayList<Object[]> olist = tdao.getLectureSchedule();
		for (int i = 0; i < olist.size(); i++) {
			out.data(olist.get(i));
		}
		System.out.println();
		// 과목 header
		ArrayList<LectureDTO> list = TeacherUtil.getLectureDTO();
		for (LectureDTO dto : list) {
			if (dto.getTCHSeq().equals(TeacherUtil.loginTeacher.getTCHSeq())) {
				out.header(new String[] { dto.getLectuerName() });
			}
		}
		// 과목 data
		olist = tdao.getSubjectList(TeacherUtil.loginTeacher.getTCHSeq());
		for (int i = 0; i < olist.size(); i++) {
			out.data(olist.get(i));
		}
		out.pause();
	}

	private void examManagement() { // ---------------------------------------------------시험관리
		while (true) {
			out.bigTitle("시험관리");
			out.menu(new String[] { "시험문제 등록", "시험문제 조회", "시험문제 삭제", "시험문제 수정", "돌아가기" });
			int input = 0;
			try {
				input = scan.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중하게 입력해주시기 바랍니다.");
				continue;
			}
			out.bar();
			if (input == 1) {
				// 시험문제 등록
				MainClass.crumb.in("시험유형");
				addExam();
				MainClass.crumb.out();
			} else if (input == 2) {
				// 시험문제 조회
				MainClass.crumb.in("시험유형");
				showExam();
				MainClass.crumb.out();
			} else if (input == 3) {
				// 시험문제 삭제
				MainClass.crumb.in("시험유형");
				removeExam();
				MainClass.crumb.out();
			} else if (input == 4) {
				// 시험문제 수정
				MainClass.crumb.in("시험유형");
				updateExam();
				MainClass.crumb.out();
			} else if (input == 5) {
				// 돌아가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 다시 입력해주세요.");
			}
		}
	}

	private void updateExam() {
		while (true) {
			int k = 0;
			out.bigTitle("시험문제 수정");
			out.menu(new String[] { "필기", "실기", "돌아가기" });
			int input = 0;
			try {
				input = scan.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			if (input == 1) {
				k = 1;
				MainClass.crumb.in("필기");
				out.bigTitle("필기");

				String subjectSeq = scan.next("과목번호");
				olist = tdao.getExamList(subjectSeq, k);
				out.header(new String[] { "문제번호", "배점", "\t문제\t" });
				// 과목의 문제 받아오기
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				// 문제 선택
				String examSeq = scan.next("문제번호 선택");

				// 문제수정
				String examContent = scan.next("문제입력");
				int result = tdao.updateExam(examSeq, examContent, k);
				out.result(result, "문제를 수정하였습니다.");
				out.pause();
				MainClass.crumb.out();
			} else if (input == 2) {
				k = 2;
				MainClass.crumb.in("필기");
				out.bigTitle("필기");

				String subjectSeq = scan.next("과목번호");
				olist = tdao.getExamList(subjectSeq, k);
				out.header(new String[] { "문제번호", "배점", "\t문제\t" });
				// 과목의 문제 받아오기
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				// 문제 선택
				String examSeq = scan.next("문제번호 선택");

				// 문제수정
				String examContent = scan.next("문제입력");
				int result = tdao.updateExam(examSeq, examContent, k);
				out.result(result, "문제를 수정하였습니다.");
				out.pause();
				MainClass.crumb.out();
			} else if (input == 3) {
				// 돌아가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 신중히입력해주시기 바랍니다.");
				continue;
			}
		}

	}

	private void removeExam() {
		while (true) {
			int k = 0;
			out.bigTitle("시험문제 삭제");
			out.menu(new String[] { "필기", "실기", "돌아가기" });
			int input = 0;
			try {
				input = scan.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주세요.");
				continue;
			}
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			if (input == 1) {
				// 필기
				k = 1;
				MainClass.crumb.in("필기");
				out.bigTitle("필기");

				String subjectSeq = scan.next("과목번호");
				olist = tdao.getExamList(subjectSeq, k);
				out.header(new String[] { "문제번호", "배점", "\t문제\t" });
				// 과목의 문제 받아오기
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				// 문제 선택
				String examSeq = scan.next("문제번호 선택");

				// 문제삭제
				int result = tdao.removeExam(examSeq, k);
				out.result(result, "해당 문제를 삭제하였습니다.");
				out.pause();

				MainClass.crumb.out();
			} else if (input == 2) {
				// 실기
				k = 2;
				MainClass.crumb.in("실기");
				out.bigTitle("실기");

				String subjectSeq = scan.next("과목번호");
				olist = tdao.getExamList(subjectSeq, k);
				out.header(new String[] { "문제번호", "배점", "\t문제\t" });
				// 과목의 문제 받아오기
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				// 문제 선택
				String examSeq = scan.next("문제번호 선택");

				// 문제삭제
				int result = tdao.removeExam(examSeq, k);
				out.result(result, "해당 문제를 삭제하였습니다.");
				out.pause();

				MainClass.crumb.out();
			} else if (input == 3) {
				// 뒤로가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 다시입력해주세요.");
			}

		}

	}

	private void addExam() {
		while (true) {
			int k = 0;
			out.bigTitle("시험문제 등록");
			out.menu(new String[] { "필기", "실기", "돌아가기" });
			int input = 0;
			try {
				input = scan.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주세요.");
				continue;
			}

			if (input == 1) {
				// 필기
				k = 1;
				MainClass.crumb.in("필기");
				out.bigTitle("필기");
				String question = scan.next("문제입력");
				String distribution = scan.next("배점입력");
				String subjectSeq = scan.next("과목번호");
				int result = tdao.addExam(question, distribution, subjectSeq, k);
				out.result(result, "시험문제를 등록하였습니다.");
				out.pause();
				MainClass.crumb.out();
				break;
			} else if (input == 2) {
				// 실기
				k = 2;
				MainClass.crumb.in("실기");
				out.bigTitle("실기");
				String question = scan.next("문제입력");
				String distribution = scan.next("배점입력");
				String subjectSeq = scan.next("과목번호");
				int result = tdao.addExam(question, distribution, subjectSeq, k);
				out.result(result, "시험문제를 등록하였습니다.");
				out.pause();
				MainClass.crumb.out();
			} else if (input == 3) {
				// 뒤로가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 다시입력해주세요.");
			}

		}
	}

	private void showExam() {
		while (true) {
			int k = 0;
			out.bigTitle("시험문제 조회");
			out.menu(new String[] { "필기", "실기", "돌아가기" });
			int input = 0;
			try {
				input = scan.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주세요.");
				continue;
			}
			if (input == 1) { // 필기
				k = 1;
				MainClass.crumb.in("필기");
				out.bigTitle("필기");
				String subjectSeq = scan.next("과목번호");

				ArrayList<Object[]> olist = tdao.getQuestionlist(subjectSeq, k);
				out.header(new String[] { "배점", "과목", "문제" });
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				out.pause();
				MainClass.crumb.out();
				break;
			} else if (input == 2) { // 실기
				k = 2;
				MainClass.crumb.in("필기");
				out.bigTitle("필기");
				String subjectSeq = scan.next("과목번호");

				ArrayList<Object[]> olist = tdao.getQuestionlist(subjectSeq, k);
				out.header(new String[] { "배점", "과목", "문제" });
				for (int i = 0; i < olist.size(); i++) {
					out.data(olist.get(i));
				}
				out.pause();
				MainClass.crumb.out();
			} else if (input == 3) { // 뒤로가기
				break;
			} else {
				out.result("잘못입력하셨습니다. 다시 입력해주세요.");
			}
		}

	}

	private void gradeManagement() {// ----------------------------------------성적
		while (true) {
			out.bigTitle("성적관리");
			out.menu(new String[] { "배점 관리", "성적 입력", "성적 조회", "돌아가기" });
			int input = 0;
			try {
				input = scan.nextInt("선택");
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
				continue;
			}
			if (input == 1) {
				// 배점관리
				MainClass.crumb.in("배점관리");
				distributionManagement();
				MainClass.crumb.out();
			} else if (input == 2) {
				// 성적입력
				MainClass.crumb.in("성적입력");
				addGrade();
				MainClass.crumb.out();
			} else if (input == 3) {
				// 성적조회
				MainClass.crumb.in("성적조회");
				showGrade();
				MainClass.crumb.out();
			} else if (input == 4) {
				// 돌아가기
				break;
			} else {
				System.out.println("잘못입력하셨습니다. 다시입력해주시기 바랍니다.");
				continue;
			}
		}

	}

	private void showGrade() {
		ArrayList<LectureDTO> list = TeacherUtil.getLectureDTO();
		while (true) {
			out.bigTitle("성적조회");
			out.menu(new String[] { "과정별 성적조회", "개인별 성적조회", "돌아가기" });
			int input = scan.nextInt("선택");
			out.bar();
			if (input == 1) {
				MainClass.crumb.in("과정선택");
				out.middleTitle("과정선택");
				out.header(new String[] { "[과정코드]", "[기간]", "\t", "[과정명]" });
				for (int i = 0; i < list.size(); i++) {
					out.data(new Object[] { list.get(i).getLectureSeq(),
							list.get(i).getLectureStartDate() + " ~ " + list.get(i).getLectureEndDate(),
							list.get(i).getLectuerName() });
				}
				out.bar();
				String selectedLectureSeq = scan.next("과정코드 입력");

				String selectedStdSeq = scan.next("학생번호 입력");

				ArrayList<GradeDTO> glist = tdao.getGradeDTO(selectedLectureSeq, selectedStdSeq);
				out.bar();
				out.header(new String[] { "[\t]", "[학생명]", "[필기]", "[실기]", "[출석]" });
				for (int i = 0; i < glist.size(); i++) {
					out.data(new Object[] { "\t", glist.get(i).getCourseSeq(), glist.get(i).getGradeNoteScore(),
							glist.get(i).getGradeSkillScore(), glist.get(i).getGradeAttendanceScore() });

				}
				out.bar();
				out.pause();
				MainClass.crumb.out();
			} else if (input == 2) {
				MainClass.crumb.in("학생선택");
				out.bigTitle("학생선택");
				out.bar();
				String stdSeq = scan.next("학생번호");
				out.bar();
				ArrayList<GradeDTO> glist = tdao.getGradeDTO(stdSeq);
				out.header(new String[] { "[\t]", "[학생명]", "[필기]", "[실기]", "[출석]" });
				for (int i = 0; i < glist.size(); i++) {
					out.data(new Object[] { "\t", glist.get(i).getCourseSeq(), glist.get(i).getGradeNoteScore(),
							glist.get(i).getGradeSkillScore(), glist.get(i).getGradeAttendanceScore() });
				}
				out.bar();
				out.pause();
				MainClass.crumb.out();
			} else if (input == 3) {
				// 돌아가기
				return;
			} else {
				out.result("잘못입력하셨습니다. 다시 입력해주세요.");
				continue;
			}
		}

	}

	private void addGrade() {
		out.bigTitle("성적입력");
		ArrayList<StudentDTO> list = tdao.getStudentDTO(TeacherUtil.loginTeacher.getTCHSeq());
		int page = 1;
		while (true) {
			int onePage = 7;
			int index = (page * onePage) - onePage;
			for (int i = index; i < index + onePage; i++) {
				if (i >= list.size()) {
					break;
				}
				out.data(new Object[] { list.get(i).getSTDName(), "[" + list.get(i).getSTDSeq() + "]" });
			}
			out.bar();
			System.out.println("(0:학생번호 입력하기)\t\t" + page + "/"
					+ (list.size() % onePage == 0 ? list.size() / onePage : list.size() / onePage + 1));
			out.bar();
			page = scan.nextInt("페이지");
			out.bar();
			if (page == 0) {
				break;
			}
		}

		String stdSeq = scan.next("▶ 학생코드 입력");
		out.bar();
		TeacherUtil.myPage();
		out.bar();
		int note = scan.nextInt("필기 점수");
		int skill = scan.nextInt("실기 점수");
		int attendance = scan.nextInt("출석 점수");
		String subjectSeq = scan.next("과목번호");

		int result = tdao.addGrade(stdSeq, note, skill, attendance, subjectSeq);
		out.result(result, "성적입력을 완료하였습니다.");
	}

	private void distributionManagement() {
		out.middleTitle("배점관리");
		DistributionDTO dto = tdao.getDistribution();
		out.result("현재 배점은 다음과 같습니다.");
		System.out.println("필기 : " + dto.getDstrNote());
		System.out.println("실기 : " + dto.getDstrSkill());
		System.out.println("출석 : " + dto.getDstrAttendance());
		while (true) {
			String sel = scan.next("변경하시겠습니까?(y/n)");
			if (sel.equals("y")) {
				out.middleTitle("배점입력");
				int note = scan.nextInt("필기배점");
				int skill = scan.nextInt("실기배점");
				int attendance = scan.nextInt("출석배점");
				int result = tdao.setDistribution(note, skill, attendance);
				out.result(result, "배점입력을 완료하였습니다.");
				break;
			} else if (sel.equals("n")) {
				break;
			} else {
				out.result("잘못입력하셨습니다. 다시입력해주세요.");
				continue;
			}
		}
	}

	// ------------------------------------------------------------출결
	private void Attendance() {
		out.bigTitle("출결현황조회");
		out.menu(UtilPrint.TEACHER_ATTENDANCE);
		int input = 0;
		try {
			input = scan.nextInt("선택");
		} catch (Exception e) {
			out.result("입력오류가 발생하였습니다. 신중히 입력해주세요.");
		}
		out.bar();

		if (input == 1) {
			MainClass.crumb.in("기간별 조회");
			showAttendanceByDay();
			MainClass.crumb.out();
		} else if (input == 2) {
			MainClass.crumb.in("과정별 조회");
			showAttendanceByLecture();
			MainClass.crumb.out();
		} else if (input == 3) {
			return;
		} else {
			out.result("잘못입력하셨습니다. 다시 입력해주세요.");
		}
	}

	private void showAttendanceByLecture() {
		LectureDTO selectedLecture = null;
		out.bigTitle("과정별 조회");
		// 현재 진행중인 과정이름을 알아야함
		ArrayList<LectureDTO> arr = TeacherUtil.getLectureDTO();
		String[] list = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			LectureDTO dto = arr.get(i);
			list[i] = dto.getLectuerName();
		}

		out.menu(list);
		int input = scan.nextInt("선택");
		selectedLecture = arr.get(input - 1);// 과정 선택...
		if (input == 6) {
			return;
		}

		// 과정별 전체 출석
		ArrayList<Object[]> olist = tdao.showAttendanceByLecture(selectedLecture);
		int count = 0;
		while (true) {
			int page = 0;
			int onePage = 10;
			if (count == 0) { // 처음만 1페이지로 고정.
				page = 1;
			} else {
				try {
					page = scan.nextInt("페이지");
				} catch (Exception e) {
					System.out.println("입력오류가 발생하였습니다. 신중히 입력해주시기 바랍니다.");
				}
			}
			if (page == 0) { // 0입력하면 뒤로가기
				break;
			}
			count++;
			int index = 0;
			try {
				index = (page - 1) * onePage;
			} catch (Exception e) {
				System.out.println("입력오류입니다. 신중하게 입력해주세요.");
			}
			if (page > (olist.size() / onePage) + 1) {
				System.out.println("페이지 범위내 숫자를 입력해주세요.");
				break;
			}
			out.header(new String[] { "학생코드", "학생명", "전화번호", "날짜", "출결" });
			int result = index + onePage;
			if (page == (olist.size() / onePage) + 1) {
				result = olist.size();
			}
			for (;;) {

				out.data(olist.get(index));
				index++;
				if (index == result) {
					break;
				}

			}
			out.bar();
			System.out.println("(0:뒤로가기)\t\t" + page + "/" + ((olist.size() / onePage) + 1));
			out.bar();
		} // while
	}

	private void showAttendanceByDay() {
		out.bigTitle("기간별 조회");
		String year = "";
		String month = "";
		String day = "";
		while (true) {
			try {
				year = scan.next("년도");
				month = scan.next("월");
				if (month.length() == 1) {
					month = "0" + month;
				}
				day = scan.next("일");
				if (day.length() == 1) {
					day = "0" + day;
				}
				break;
			} catch (NumberFormatException e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주세요.");
				continue;
			} catch (Exception e) {
				out.result("입력오류가 발생하였습니다. 신중히 입력해주세요.");
				continue;
			}
		}
		ArrayList<Object[]> list = tdao.showAttendanceByDay(year, month, day);

		int page = 1;
		while (true) {
			int onePage = 7;
			int index = (page * onePage) - onePage;
			for (int i = index; i < index + onePage; i++) {
				if (i >= list.size()) {
					break;
				}
				out.header(new String[] { "학생", "\t과정명\t", " 출석시간  ", "퇴실시간", "출결상황" });
				out.data(list.get(i));
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
	}

	// -------------------------------------------------------------------------------교사평가
	private void teacherEvaluation() {
		out.bigTitle("교사 평가 조회");

		// 데이터
		ArrayList<Object[]> list = tdao.showTeacherEvaluation();
		int page = 1;
		while (true) {
			int onePage = 7;
			int index = (page * onePage) - onePage;
			// 헤더
			out.header(new String[] { "[번호]", "[교사명]", "  [과정명]  ", " [점수] ", " [코멘트] " });
			for (int i = index; i < index + onePage; i++) {
				if (i >= list.size()) {
					break;
				}

				out.data(list.get(i));
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
	}

	// -------------------------------------------------------------------------------상담일지
	private void consulting() {
		out.bigTitle("상담일지");
		out.menu(new String[] { "상담일지 작성", "상담일지 조회", "돌아가기" });
		int input = scan.nextInt("선택");
		out.bar();
		String stdSeq = "";
		String lectureSeq = "";
		String content = "";
		if (input == 1) {
			while (true) {
				try {
					// 상담일지 작성
					lectureSeq = scan.next("과정코드");
					stdSeq = scan.next("학생 번호");				
					content = scan.next("상담내용");
					break;
				} catch (NumberFormatException e) {
					System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
					continue;
				} catch (Exception e) {
					System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
					continue;
				}
			}
			int result = tdao.addCourseRecord(stdSeq, lectureSeq, content);
			out.result(result, "상담내용을 추가하였습니다.");
			out.pause();
		} else if (input == 2) {
			// 상담일지 조회
			out.bigTitle("상담일지 조회");
			while (true) {
				try {
					stdSeq = scan.next("학생번호");
					lectureSeq = scan.next("과정번호");
					break;
				} catch (Exception e) {
					System.out.println("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
					continue;
				}
			}
			ArrayList<Object[]> olist = tdao.showCourseRecord(stdSeq, lectureSeq);
			out.header(new String[] { "[상담번호]", "[상담내용]" });
			for (int i = 0; i < olist.size(); i++) {
				out.data(olist.get(i));
			}
			out.pause();
		} else if (input == 3) {
			// 돌아가기
			return;
		} else {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
		}
	}
}
