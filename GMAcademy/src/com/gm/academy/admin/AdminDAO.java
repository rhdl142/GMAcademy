package com.gm.academy.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.Util.DBUtil;
import com.gm.academy.Util.UtilPrint;
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

import oracle.jdbc.internal.OracleTypes;

public class AdminDAO {
	private Connection conn;
	private PreparedStatement stat;
	private static UtilPrint out;

	public AdminDAO() {
		this.conn = DBUtil.getConnection("localhost", "Project", "JAVA1234");
		out = new UtilPrint();
	}

	/**
	 * errorLog 코드 발생 등록
	 * 
	 * @param logCode 로그 코드
	 * @param part    에러 발생한곳
	 * 
	 */
	public void systemError(String logCode, String part) {
		try {
			String sql = "insert into tblErrorLog values(errorSeq.nextval,?,default,?)";
			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, logCode);
			stat.setString(2, part);

			stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("AdminDAO.systemError :" + e.toString());
		}

	}

	/**
	 * 관리자 > 로그인 인증 메서드
	 * 
	 * @param alDTO 로그인 정보
	 * @return 로그인 인증 유무
	 */
	public String auth(AdminLogInDTO alDTO) {
		try {
			String sql = "select count(*) as cnt from tblAdminLogIn where adminid = ? and adminpw = ?";

			stat = conn.prepareStatement(sql);

			stat.setString(1, alDTO.getAdminID());
			stat.setString(2, alDTO.getAdminPW());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				return rs.getString("cnt");
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.auth()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.auth()");
		}

		return null;
	}

	// 과목명,교재명,저자,가격 출력 교재관리 전체조회
	/**
	 * 교재 관리 (전체 조회) 메서드
	 * 
	 * @return 교재 정보
	 */
	public ArrayList<TextBookManagementDTO> textBookManagement() {
		ArrayList<TextBookManagementDTO> list = new ArrayList<>();

		try {
			String sql = "select " + "    s.subjectName, " + "    t.textbookwriter, " + "    textBookName, "
					+ "    t.textbookprice " + " from tblTextBook t " + "    inner join tblSubject s "
					+ "        on t.subjectSeq = s.subjectSeq " + "            order by length(s.subjectName) ";

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				TextBookManagementDTO tbmDTO = new TextBookManagementDTO();

				tbmDTO.setSubjectName(rs.getString("subjectName"));
				tbmDTO.setTextBookWriter(rs.getString("textbookwriter"));
				tbmDTO.setTextBookName(rs.getString("textBookName"));
				tbmDTO.setTextBookPrice(rs.getString("textbookprice"));

				list.add(tbmDTO);
			}

			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.textBookManagement()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.textBookManagement()");
		}
		return null;
	}

	// 과목명,교재명,저자,가격 출력 교재관리 과정조회
	/**
	 * 교재 관리 (과정 조회) 메서드
	 * 
	 * @return 교재 정보
	 */
	public ArrayList<TextBookManagementDTO> textBookLecture(String lec) {
		ArrayList<TextBookManagementDTO> list = new ArrayList<>();

		try {
			String sql = "select s.subjectName, tb.textBookname, tb.textBookWriter, tb.textBookprice from tblLectureSubject ls "
					+ "    left outer join tblTextBook tb " + "        on tb.textBookSeq = ls.textBookSeq "
					+ "            inner join tblSubject s " + "                on ls.subjectSeq = s.subjectSeq "
					+ "                    where ls.lectureSeq = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lec);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				TextBookManagementDTO tbmDTO = new TextBookManagementDTO();

				tbmDTO.setSubjectName(rs.getString("subjectName"));
				tbmDTO.setTextBookName(rs.getString("textBookname"));
				tbmDTO.setTextBookWriter(rs.getString("textBookWriter"));
				tbmDTO.setTextBookPrice(rs.getString("textBookprice"));

				list.add(tbmDTO);
			}

			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.textBookLecture()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.textBookLecture()");
		}
		return null;
	}

	// 과정명 과정번호 출력, 교재관리 과정조회
	/**
	 * 교재 관리 > 현재 과정 조회(과정 조회) 메서드
	 * 
	 * @return 교재 정보
	 */
	public ArrayList<LectureDTO> lectureNumber() {
		ArrayList<LectureDTO> list = new ArrayList<>();

		try {
			String sql = "select lectureSeq, lectureName from tblLecture";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				LectureDTO lDTO = new LectureDTO();

				lDTO.setLectureSeq(rs.getString("lectureSeq"));
				lDTO.setLectuerName(rs.getString("lectureName"));

				list.add(lDTO);
			}

			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.lectureNumber()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.lectureNumber()");
		}
		return null;
	}

	// 과목명, 과목코드 출력 교재관리_교재등록
	/**
	 * 교재 관리 > 교재 등록 메서드
	 * 
	 * @return 교재 등록시 필요한 과목 코드, 과목명
	 */
	public ArrayList<SubjectDTO> subject() {
		ArrayList<SubjectDTO> list = new ArrayList<>();

		try {
			String sql = "select * from tblSubject";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				SubjectDTO sDTO = new SubjectDTO();

				sDTO.setSubjectSeq(rs.getString("subjectSeq"));
				sDTO.setSubjectName(rs.getString("subjectName"));

				list.add(sDTO);
			}

			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.subject()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.subject()");
		}
		return null;
	}

	// 출판사명, 출판사코드 출력 교재관리_교재등록
	/**
	 * 교재 관리 > 교재 등록 메서드
	 * 
	 * @return 교재 등록시 필요한 출판사 코드, 출판사명
	 */
	public ArrayList<PublisherDTO> publisher() {
		ArrayList<PublisherDTO> list = new ArrayList<>();

		try {
			String sql = "select * from tblPublisher";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				PublisherDTO pDTO = new PublisherDTO();

				pDTO.setPublisherName(rs.getString("publisherName"));
				pDTO.setPublisherSeq(rs.getString("publisherSeq"));

				list.add(pDTO);
			}

			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.publisher()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.publisher()");
		}
		return null;
	}

	// 교재관리_교재신청하기
	/**
	 * 교재 관리 > 교재 등록 메서드
	 * 
	 * @return 교재 등록시 성공 유무
	 */
	public int textBookApplicationWrite(TextBookDTO tbDTO) {
		try {
			String sql = "insert into tblTextBook values (textBookSeq.nextval,?,?,?,?,?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, tbDTO.getTextBookName());
			stat.setString(2, tbDTO.getTextBookWriter());
			stat.setString(3, tbDTO.getTextBookPrice());
			stat.setString(4, tbDTO.getPublisherSeq());
			stat.setString(5, tbDTO.getSubjectSeq());

			return stat.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.textBookApplicationWrite()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.textBookApplicationWrite()");
		}
		return 0;
	}

	/**
	 * 교사 계정 관리 > 교사 재직 현황 메서드
	 * 
	 * @return 교사 정보를 담은 TeacherSelectDTO
	 */
	public ArrayList<TeacherSelectDTO> list() { // 교사 조회

		ArrayList<TeacherSelectDTO> list = new ArrayList<TeacherSelectDTO>();

		try {

			String sql = "select t.tchseq, t.tchname, t.tchssn, t.tchtel,l.tchid, l.tchregdate "
					+ "from tblTeacher t, tblTeacherLogin l " + "where t.tchseq = l.tchseq(+)";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				TeacherSelectDTO dto = new TeacherSelectDTO();

				dto.setTCHSeq(rs.getString("tchseq"));
				dto.setTCHName(rs.getString("tchname"));
				dto.setTCHSsn(rs.getString("tchssn"));
				dto.setTCHTel(rs.getString("tchtel"));
				dto.setTCHId(rs.getString("tchid"));
				dto.setTCHRegdate(rs.getString("tchregdate"));
				list.add(dto);

			}

			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.list()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.list()");
		}

		return null;
	}

	/**
	    * 교사 등록 DAO
	    * @param register
	    * @return
	    */
	   public int TeacherRegister(TeacherDTO register) { 
	      
	      
	      try {
	         
	         String sql = "insert into tblTeacher (tchSeq, tchName, tchSsn, tchTel)"
	                     +" values ('TC' || tchseq.nextval, ?, ?, ?)";
	         
	         PreparedStatement stat = conn.prepareStatement(sql);
	         
	         stat.setString(1, register.getTCHName());
	         stat.setString(2, register.getTCHSsn());
	         stat.setString(3, register.getTCHTel());
	         
	         return stat.executeUpdate();
	         
	      } catch (Exception e) {
	         System.out.println("AdminDAO.TeacherRegister()" + e.toString());
	      }
	      
	      return 0;
	   }
	   

	/**
	 * 교사 계정 관리 > 교사 삭제 메서드
	 * 
	 * @return 교사 삭제 성공 유무
	 */
	public int Remove(String tchId, String tchName) {// 교사 삭제 기능

		try {
			String sql = "select count(*) as cnt from tblTeacher where tchseq = "
					+ "(select tchseq from tblTeacherLogin where TCHseq = ?)" + "and tchname = ?";

			stat = conn.prepareStatement(sql);

			stat.setString(1, tchId);
			stat.setString(2, tchName);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				int result = rs.getInt("cnt");

				if (result > 0) {

					sql = "delete from tblTeacher where tchseq = "
							+ "(select tchseq from tblTeacherLogin where tchseq = ?) " + " and tchname = ?";

					stat = conn.prepareStatement(sql);

					stat.setString(1, tchId);
					stat.setString(2, tchName);

					return stat.executeUpdate();

				} else {
					System.out.println("해당 교사는 존재하지 않습니다.");
				}

			}
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.Remove()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.Remove()");
		}
		return 0;
	}

	/**
	 * 교사 계정 관리 > 교사 정보 수정 메서드
	 * 
	 * @return 교사 정보 수정 성공 유무
	 */
	public int updateName(String tchNameBefor, String tchNameAfter) { // 교사 수정 이름

		try {
			String sql = "update tblTeacher set tchname = ? where tchname = ?";

			stat = conn.prepareStatement(sql);

			stat.setString(1, tchNameAfter);
			stat.setString(2, tchNameBefor);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateName()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateName()");
		}

		return 0;
	}

	/**
	 * 교사 정보 수정 메서드
	 * 
	 * @param tchTelBefor 교사 번호
	 * @param tchTelAfter 교사 새로운 번호
	 * @return 교사 정보 수정 성공 유무
	 */
	public int updateTel(String tchTelBefor, String tchTelAfter) { // 교사 수정 전화번호
		try {
			String sql = "update tblTeacher set tchtel = ? where tchtel = ?";

			stat = conn.prepareStatement(sql);

			stat.setString(1, tchTelAfter);
			stat.setString(2, tchTelBefor);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateTel()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateTel()");
		}

		return 0;
	}

	/**
	 * 교사 계정 관리 > 교사 등록 (주민번호 유효성 검사) 메서드
	 * 
	 * @param tchssn 교사 주민번호
	 * @return 교사 주민번호 확인 유무
	 */
	public int checkSsn(String tchssn) {

		try {

			String sql = "select count(*) as cnt from tblTeacher where tchssn = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, tchssn);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				int disSsn = rs.getInt("cnt");

				return disSsn;

			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.checkSsn()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.checkSsn()");
		}

		return 0;
	}

	/**
	 * 교사 계정 관리 > 교사 등록 (전화번호 유효성 검사) 메서드
	 * 
	 * @param tchssn 교사 전화번호
	 * @return 교사 전화번호 확인 유무
	 */
	public int checkTel(String tchtel) {

		try {
			String sql = "select count(*) as cnt from tblTeacher where tchtel = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, tchtel);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				int disTel = rs.getInt("cnt");

				return disTel;

			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.checkTel()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.checkTel()");
		}

		return 0;
	}

	/**
	 * 개설 과목 관리 > 과목 현황 조회 메서드
	 * 
	 * @return 강의 중인 과목 리스트 담은 SubjectDTO
	 */
	public ArrayList<SubjectDTO> subjectlist() { // 과목 목록(강의중인 과정에 속한)

		ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();

		try {

			String sql = "select A.subjectseq,  A.subjectname from "
					+ "(select s.subjectseq as subjectseq,  s.subjectname as subjectname from tbllecturesubject ls "
					+ "    inner join tblLecture l " + "        on l.lectureseq = ls.lectureseq "
					+ "            inner join tblSubject s " + "                on s.subjectseq = ls.subjectseq "
					+ "                    where l.lectureprogress = '강의중' "
					+ "                        order by s.subjectname) A "
					+ "                            group by A.subjectseq, A.subjectname "
					+ "								order by A.subjectseq";
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				// 레코드 1줄 > DTO 1개 > list
				SubjectDTO dto = new SubjectDTO();
				dto.setSubjectSeq(rs.getString("subjectseq")); // 과목 번호
				dto.setSubjectName(rs.getString("subjectname")); // 과목 명
				list.add(dto);
			}

			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.subjectlist()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.subjectlist()");
		}

		return null;

	}

	/**
	 * 개설 과목 관리 > 과목 등록 메서드
	 * 
	 * @param subject 과목명
	 * @return 과목 등록 성공 유무
	 */
	public int addsubject(SubjectDTO subject) { // 과목 추가

		try {

			String sql = "insert into tblSubject values('S'||subjectSeq.nextval, ?)";
			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, subject.getSubjectName());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.addsubject()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.addsubject()");
		}

		return 0;
	}

	/**
	 * 개설과목 관리 > 과목 삭제 메서드
	 * 
	 * @param subject 과목 명
	 * @return 과목 삭제 성공 유무
	 */
	public int deletesubject(SubjectDTO subject) { // 과목 삭제

		try {

			String sql = "delete from tblSubject where subjectname = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, subject.getSubjectName());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.deletesubject()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.deletesubject()");
		}
		return 0;
	}

	/**
	 * 개설 과목 관리 > 과목 수정 메서드
	 * 
	 * @param subject 과목명
	 * @return 과목 수정 성공 여부
	 */
	public int updatesubject(SubjectDTO subject) { // 과목 수정

		try {

			String sql = "update tblsubject set subjectname = ? "
					+ "where subjectseq = (select subjectseq from tblSubject where subjectname = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, subject.getUpdatesubjectname());
			stat.setString(2, subject.getSubjectName());

			stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updatesubject()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updatesubject()");
		}

		return 0;
	}
	
	

	/**
	 * 성적 조회(필기, 실기, 출결) 메서드
	 * 
	 * @param studentSeq  교육생 코드
	 * @param studentName 교육생 명
	 * @return 과목에 따른 필기 실기 출결 점수 List에 반환
	 */
	public ArrayList<SubjectDTO> studentSubjectList(String studentSeq, String studentName) {

		ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();

		try {

			String sql = "select sb.subjectseq, sb.subjectname from tblCourse c " + "    inner join tblStudent s "
					+ "        on c.stdseq = s.stdseq " + "            inner join tblLecture l "
					+ "                on c.lectureseq = l.lectureseq " + "                    inner join tblGrade g "
					+ "                        on c.courseseq = g.courseseq "
					+ "                            inner join tbllecturesubject ls "
					+ "                                on ls.lectureseq = l.lectureseq "
					+ "                                    inner join tblSubject sb "
					+ "                                        on ls.subjectseq = sb.subjectseq "
					+ "                                                where s.stdseq = ? and s.stdname = ? "
					+ "                                                     and g.gradenotescore <> 0 "
					+ "                                                        order by sb.subjectseq desc";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, studentSeq);
			stat.setString(2, studentName);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				SubjectDTO dto = new SubjectDTO();
				dto.setSubjectSeq(rs.getString("subjectSeq"));
				dto.setSubjectName(rs.getString("subjectname"));
				list.add(dto);
			}

			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.studentSubjectList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.studentSubjectList()");
		}
		return null;
	}

	/**
	 * 학생 과목별 성적 출력 정보 메서드
	 * 
	 * @param studentSeq  학생코드
	 * @param studentName 과목명
	 * @param input       과목코드
	 * @return
	 */
	public StudentGradeInfoDTO studentGradeInfo(String studentSeq, String studentName, String input) {

		StudentGradeInfoDTO dto = new StudentGradeInfoDTO();

		// 선택한 과목코드 입력받은 뒤 해당 학생의 성적 출력 메소드
		try {

			String sql = "select s.stdseq, s.stdname, g.gradenotescore , g.gradeskillscore , g.gradeattendancescore , l.lecturename, sb.subjectname from tblCourse c "
					+ "    inner join tblStudent s " + "        on c.stdseq = s.stdseq "
					+ "            inner join tblLecture l " + "                on c.lectureseq = l.lectureseq "
					+ "                    inner join tblGrade g "
					+ "                        on c.courseseq = g.courseseq "
					+ "                            inner join tbllecturesubject ls "
					+ "                                on ls.lectureseq = l.lectureseq "
					+ "                                    inner join tblSubject sb "
					+ "                                        on ls.subjectseq = sb.subjectseq "
					+ "                                                where s.stdseq = ? and s.stdname = ? and sb.subjectseq = ? "
					+ "                                                     and g.gradenotescore <> 0";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, studentSeq);
			stat.setString(2, studentName);
			stat.setString(3, input);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {

				dto.setSTDseq(rs.getString("stdseq"));
				dto.setSTDName(rs.getString("stdname"));
				dto.setGradeNoteScore(rs.getString("gradenotescore"));
				dto.setGradeSkillScore(rs.getString("gradeskillscore"));
				dto.setGradeAttendanceScore(rs.getString("gradeattendancescore"));
				dto.setLectureName(rs.getString("lecturename"));
				dto.setSubjectName(rs.getString("subjectname"));

			}
			return dto;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.studentGradeInfo()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.studentGradeInfo()");
		}
		return null;
	}

	/**
	 * 과정별 출결조회시 진행중인 과정 정보 메서드
	 * 
	 * @return 현재 진행중인 과정 정보 List 반환
	 */
	public ArrayList<Object[]> getLecture() {
		String sql = "select lectureseq as 과정번호, lectureName as 과정명, "
				+ "        lectureenddate-lecturestartdate||'일' as 기간 "
				+ "            from tblLecture where lectureprogress = '강의중'";
		ArrayList<Object[]> olist = new ArrayList<Object[]>();
		try {
			stat = conn.prepareStatement(sql);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("과정번호") + "\t", rs.getString("기간"), "\t" + rs.getString("과정명") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getLecture()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getLecture()");
		}
		return null;
	}

	/**
	 * 과정별 출결조회 메서드
	 * 
	 * @param sel 과정 코드
	 * @return 과정에 따른 학생의 출결정보 반환
	 */
	public ArrayList<Object[]> getAttendance(String sel) {
		String sql = "select s.stdSeq as 학생번호,s.stdName as 학생명, s.stdtel as 전화번호, "
				+ "    to_char(ad.ontime,'yyyy-mm-dd') as 날짜,ab.absencesituation as 출결 "
				+ "        from tblattendance ad  "
				+ "            inner join tblCourse c on c.courseseq = ad.courseseq "
				+ "                inner join tblStudent s on s.stdseq = c.stdseq "
				+ "                    inner join tblabsencerecord  ab on ab.absenceSeq = ad.absenceSeq "
				+ "                        where lectureSeq = ?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, sel);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("학생번호") + "\t", rs.getString("학생명") + "\t",
						rs.getString("전화번호") + "\t", rs.getString("날짜"), rs.getString("출결") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getAttendance()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getAttendance()");
		}

		return null;
	}

	/**
	 * 기간별 출결 조회 메서드
	 * 
	 * @param year  시작년도
	 * @param month 시작 월
	 * @param day   시작 일
	 * @return 기간에 출결 조회
	 */
	public ArrayList<Object[]> getAttendanceByDay(String year, String month, String day) {
		String sql = "select s.stdName as 학생명, l.lectureName as 과정명, to_char(ontime,'hh24:mi:ss') as 출석시간, to_char(offtime,'hh24:mi:ss') as 퇴실시간, "
				+ "    ab.absenceSituation as 출결 "
				+ "        from tblattendance ad inner join tblabsencerecord ab on ad.absenceseq = ab.absenceseq "
				+ "            inner join tblCourse c on c.courseSeq = ad.courseseq "
				+ "                inner join tblStudent s on s.stdSeq = c.stdSeq "
				+ "                    inner join tblLecture l on l.lectureSeq = c.lectureSeq "
				+ "                        where to_char(ontime,'yyyy-mm-dd') = ? and to_char(offtime,'yyyy-mm-dd') = ?";
		ArrayList<Object[]> olist = new ArrayList<Object[]>();
		try {
			String date = year + "-" + month + "-" + day;
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, date);
			stat.setString(2, date);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("학생명"), rs.getString("출석시간"), rs.getString("퇴실시간"),
						rs.getString("출결"), rs.getString("과정명") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getAttendanceByDay()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getAttendanceByDay()");
		}

		return null;
	}

	/**
	 * 출결 수정시 학생명을 정보 확인 메서드
	 * 
	 * @param stdName 교육생 명
	 * @return 교육생 정보 반환
	 */
	public ArrayList<StudentDTO> getStudentDTO(String stdName) {
		String sql = "select * from tblStudent where stdName = ?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, stdName);
			ResultSet rs = stat.executeQuery();
			ArrayList<StudentDTO> slist = new ArrayList<StudentDTO>();
			while (rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setSTDSeq(rs.getString("stdSeq"));
				dto.setSTDName(rs.getString("stdName"));
				dto.setSTDSsn(rs.getString("stdSsn"));
				dto.setSTDTel(rs.getString("stdTel"));
				slist.add(dto);
			}
			return slist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getStudentDTO()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getStudentDTO()");
		}
		return null;
	}

	/**
	 * 특정 교육생 출결 정보 데이터 메서드
	 * 
	 * @param stdSeq 교육생 코드
	 * @return 특정 교육생 출결정보
	 */
	public ArrayList<Object[]> getAttendanceByStudent(String stdSeq) {
		String sql = "select to_char(ontime,'yyyy-mm-dd') as 날짜 ,  ab.absencesituation as 출결, "
				+ "    to_char(ontime,'hh24:mi:ss') as 출입시간,to_char(offtime,'hh24:mi:ss') as 퇴실시간 "
				+ "        from tblattendance a inner join tblCourse c on c.courseseq = a.courseseq "
				+ "            inner join tblabsencerecord ab on ab.absenceseq = a.absenceseq "
				+ "                inner join tblStudent s on s.stdSeq = c.stdSeq "
				+ "                    where s.stdSeq = ? ";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, stdSeq);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("날짜"), rs.getString("출입시간"), rs.getString("퇴실시간"),
						rs.getString("출결") });

			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getAttendanceByStudent()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getAttendanceByStudent()");
		}

		return null;
	}

	/**
	 * 출결 정보 수정 메서드
	 * 
	 * @param stdSeq    교육생 코드
	 * @param date      날짜
	 * @param situation 근태
	 * @return 출결 정보 수정 성공 여부
	 */
	public int updateAttendance(String stdSeq, String date, String situation) {
		String sql = "update tblAttendance set absenceSeq = ? where TO_CHAR(ONTIME,'YYYY-MM-DD') = ? and courseSeq="
				+ "    (select courseSeq from tblCourse c inner join tblLecture l on c.lectureSeq = l.lectureSeq "
				+ "        where c.stdSeq = ? and l.lectureProgress = '강의중')";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, situation);
			stat.setString(2, date);
			stat.setString(3, stdSeq);
			return stat.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateAttendance()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateAttendance()");
		}
		return 0;
	}

	/**
	 * 전체 과정 정보 반환 메서드
	 * 
	 * @return 과정 정보 반환 리스트
	 */
	public ArrayList<LectureDTO> LectureList() {
		ArrayList<LectureDTO> lecList = new ArrayList<LectureDTO>();

		try {

			String sql = "select lectureSeq as 과정코드, " + "        lectureName as 과정명, "
					+ "        to_char(lectureStartDate,'yyyy-mm-dd') as 과정시작일, "
					+ "        to_char(lectureEndDate,'yyyy-mm-dd') as 과정종료일, " + "        lectureProgress as 강의진행여부 "
					+ "        from tblLecture";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(rs.getString("과정코드"));
				lecDTO.setLectuerName(rs.getString("과정명"));
				lecDTO.setLectureStartDate(rs.getString("과정시작일"));
				lecDTO.setLectureEndDate(rs.getString("과정종료일"));
				lecDTO.setLectureProgress(rs.getString("강의진행여부"));

				lecList.add(lecDTO);
			}

			return lecList;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.LectureList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.LectureList()");
		}

		return null;
	}

	/**
	 * 과정 내용 상세보기 메서드
	 * 
	 * @param lecSeq 과정 코드
	 * @return 특정 과정 정보 출력
	 */
	public ArrayList<LectureDTO> LectureDetail(String lecSeq) {

		ArrayList<LectureDTO> lecList = new ArrayList<LectureDTO>();

		try {
			// System.out.printf("lecSeq = %s\n",lecSeq);
			String sql = "select l.lectureSeq as lectureSeq, "
					+ "l.lectureProgress as lectureProgress, t.tchName as tchname, "
					+ " l.lectureCurrentSTD as lectureCurrentSTD, l.classSEQ as classSeq, l.lectureName as lectureName from tblLecture L "
					+ "    inner join tblTeacher T " + "        on T.TCHseq = l.tchseq "
					+ "         where lectureSeq = ? ";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, lecSeq);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(rs.getString("lectureSeq"));
				lecDTO.setLectureProgress(rs.getString("lectureProgress"));
				lecDTO.setTeacherName(rs.getString("tchname"));
				lecDTO.setLectureCurrentSTD(rs.getString("lectureCurrentSTD"));
				lecDTO.setClassSeq(rs.getString("classSeq"));
				lecDTO.setLectuerName(rs.getString("lectureName"));

				lecList.add(lecDTO);
			}

			return lecList;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.LectureDetail()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.LectureDetail()");
		}
		return null;
	}

	/**
	 * 과정 등록 메서드
	 * 
	 * @param lecDTO 과정정보 클래스DTO
	 * @return 과정 등록 성공 여부
	 */
	public int lectureRegister(LectureDTO lecDTO) {
		try {

			String sql = "insert into tblLecture values "
					+ " ('L'||lectureSeq.nextval, ? , to_date(?,'yyyy-mm-dd'), to_date(?,'yyyy-mm-dd'), ?, ?, ?, ?, ?) ";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecDTO.getLectuerName());
			stat.setString(2, lecDTO.getLectureStartDate());
			stat.setString(3, lecDTO.getLectureEndDate());
			stat.setString(4, lecDTO.getLectureProgress());
			stat.setString(5, lecDTO.getLectureAcceptSTD());
			stat.setString(6, lecDTO.getLectureCurrentSTD());
			stat.setString(7, lecDTO.getClassSeq());
			stat.setString(8, lecDTO.getTCHSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.lectureRegister()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.lectureRegister()");
		}

		return 0;
	}

	/**
	 * 과정 삭제 메서드
	 * 
	 * @param lecDTO 과정정보 클래스DTO
	 * @return 과정 삭제 성공 여부
	 */
	public int LectureRemove(LectureDTO lecDTO) {

		try {

			String sql = "delete from tblLecture where lectureSeq = ? ";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecDTO.getLectureSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.LectureRemove()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.LectureRemove()");
		}

		return 0;
	}

	/**
	 * 과정 정보 수정 메서드
	 * 
	 * @param lecDTO 과정 정보 클래스 DTO
	 * @return 과정명 수정 성공 여부
	 */
	public int updateLectureName(LectureDTO lecDTO) {

		try {

			String sql = "update tblLecture set lecturename = ? where lectureseq = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecDTO.getLectuerName());
			stat.setString(2, lecDTO.getLectureSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateLectureName()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateLectureName()");
		}

		return 0;
	}

	/**
	 * 과정 정보 수정 메서드
	 * 
	 * @param lecDTO 과정 정보 클래스 DTO
	 * @return 과정기간 수정 성공 여부
	 */
	public int updateLectureDate(LectureDTO lecDTO) {

		try {

			String sql = "update tblLecture "
					+ "    set lectureStartDate = to_date(?,'yyyy-mm-dd'),lectureEndDate = to_date(?,'yyyy-mm-dd') "
					+ "        where lectureSeq = ? ";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecDTO.getLectureStartDate());
			stat.setString(2, lecDTO.getLectureEndDate());
			stat.setString(3, lecDTO.getLectureSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateLectureDate()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateLectureDate()");
		}
		return 0;
	}

	/**
	 * 과정 정보 수정 메서드
	 * 
	 * @param lecDTO 과정 정보 클래스 DTO
	 * @return 과정진행여부 수정 성공 여부
	 */
	public int updateLectureProgress(LectureDTO lecDTO) {
		try {

			String sql = "update tblLecture set lectureProgress  = ? where lectureSeq = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecDTO.getLectureProgress());
			stat.setString(2, lecDTO.getLectureSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateLectureProgress()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateLectureProgress()");
		}
		return 0;
	}

	/**
	 * 과정 정보 수정 메서드
	 * 
	 * @param lecDTO 과정 정보 클래스 DTO
	 * @return 학생 인원 수정 성공 여부
	 */
	public int updateStuedent(LectureDTO lecDTO) {

		try {

			String sql = "update tblLecture set lectureAcceptSTD = ?, lectureCurrentSTD = ? where lectureSeq = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecDTO.getLectureAcceptSTD());
			stat.setString(2, lecDTO.getLectureCurrentSTD());
			stat.setString(3, lecDTO.getLectureSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateStuedent()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateStuedent()");
		}
		return 0;
	}

	/**
	 * 과정 정보 수정 메서드
	 * 
	 * @param lecDTO 과정 정보 클래스 DTO
	 * @return 강의실 수정 성공 여부
	 */
	public int updateClassRoom(LectureDTO lecDTO) {
		try {

			String sql = "update tblLecture set classSeq = ? where lectureSeq = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecDTO.getClassSeq());
			stat.setString(2, lecDTO.getLectureSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateClassRoom()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateClassRoom()");
		}
		return 0;
	}

	/**
	 * 과정 정보 수정 메서드
	 * 
	 * @param lecDTO 과정 정보 클래스 DTO
	 * @return 교사코드 수정 성공 여부
	 */
	public int updateTeacher(LectureDTO lecDTO) {

		try {

			String sql = "update tblLecture set TCHseq = (select TCHSeq from tblTeacher where TCHSeq = ?) where lectureseq = ?";
			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecDTO.getTCHSeq());
			stat.setString(2, lecDTO.getLectureSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateTeacher()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateTeacher()");
		}
		return 0;
	}

	/**
	 * 교사 수정 메서드
	 * 
	 * @param tchNameAfter 변경할 교사명
	 * @return 교사아이디 수정 성공 여부
	 */
	public int updateTchId(String tchNameAfter) {
		try {
			String sql = "update tblTeacherLogin set tchId = 'TC'||? where tchId = ?";

			stat = conn.prepareStatement(sql);
			stat.setString(1, tchNameAfter);
			stat.setString(2, tchNameAfter);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateTchId()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateTchId()");
		}

		return 0;
	}

	/**
	 * 과정 정보 메서드
	 * 
	 * @param str 강의 현황(강의중,강의예정,강의종료)
	 * @return 과정 정보 반환
	 */
	public ArrayList<Object[]> getLecture(String str) {
		String sql = "select l.lectureName as 과정명,to_char(l.lecturestartDate,'yyyy-mm-dd' ) as 시작일, "
				+ " to_char(l.lectureenddate,'yyyy-mm-dd') as 종료일, t.tchName as 교사명 "
				+ "    from tblLecture l inner join tblTeacher t on l.tchSeq = t.tchSeq "
				+ "            where lectureProgress = ? ";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, str);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("시작일"), rs.getString("종료일"), rs.getString("교사명") + "\t",
						rs.getString("과정명") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getLecture()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getLecture()");
		}

		return null;
	}

	/**
	 * 추천회사 정보 메서드
	 * 
	 * @return 추천회사 리스트 반환
	 */
	public ArrayList<Object[]> getRecommendedCompany() {
		String sql = "select * from tblRecommendCompany ";
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("RECCOMPANYSEQ"), rs.getString("RECCOMPANYNAME"),
						rs.getString("RECCOMPANYPAYMENT"), rs.getString("RECCOMPANYLOCATION") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getRecommendedCompany()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getRecommendedCompany()");
		}

		return null;
	}

	/**
	 * 추천회사 등록 메서드
	 * 
	 * @param name     회사명
	 * @param location 위치
	 * @param payment  급여
	 * @return 추천회사 등록 성공 여부
	 */
	public int addRecommendCompany(String name, String location, String payment) {
		String sql = "insert into tblRecommendCompany values" + "(RecCompanySeq.nextval,?,?,?)";

		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setString(2, location);
			stat.setString(3, payment);
			return stat.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.addRecommendCompany()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.addRecommendCompany()");
		}
		return 0;
	}

	/**
	 * 추천회사 삭제 메서드
	 * 
	 * @param seq 추천회사 코드
	 * @return 추천회사 삭제 성공 여부
	 */
	public int removeRecommendCompany(int seq) {
		String sql = "delete from tblRecommendCompany where RecCompanySeq =?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setInt(1, seq);
			return stat.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.removeRecommendCompany()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.removeRecommendCompany()");
		}
		return 0;
	}

	/**
	 * 추천회사 수정 메서드
	 * 
	 * @param name     회사명
	 * @param location 위치
	 * @param payment  급여
	 * @param seq      추천회사 코드
	 * @return
	 */
	public int updateRecommendedCompany(String name, String location, String payment, int seq) {
		String sql = "update tblRecommendCompany set RecCompanyName = ? ,RecCompanyLocation = ? ,"
				+ " RecCompanyPayment= ? where RecCompanySeq = ?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setString(2, location);
			stat.setString(3, payment);
			stat.setInt(4, seq);
			return stat.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateRecommendedCompany()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateRecommendedCompany()");
		}

		return 0;
	}

	/**
	 * 과정 교사 정보 메서드
	 * 
	 * @return 과정 교사 정보 반환
	 */
	public ArrayList<Object[]> getLectureAndTeacher() {
		String sql = "select l.lectureseq as 번호, t.tchName as 교사명, l.lectureName as 과정명 "
				+ "    from tblLecture l inner join tblTeacher t on t.tchseq = l.tchseq ";
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("번호"), rs.getString("교사명"), "\t" + rs.getString("과정명") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getLectureAndTeacher()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getLectureAndTeacher()");
		}
		return null;
	}

	/**
	 * 교사 및 과목 정보 메서드
	 * 
	 * @param seq 교사 코드
	 * @return 교사 및 과목 정보 반환
	 */
	public ArrayList<Object[]> getSubject(String seq) {
		String sql = "";
		if (seq.indexOf("TC") > -1) {
			sql = "select t.tchName as 교사명, l.lectureName as 과정명, s.subjectName as 과목명, l.lectureprogress as 현황  "
					+ "    from tblLectureSubject ls inner join tblSubject s on s.subjectSeq = ls.subjectSeq "
					+ "        inner join tblLecture l on l.lectureSeq = ls.lectureSeq "
					+ "            inner join tblTeacher t on l.tchseq = t.tchseq "
					+ "                where t.tchSeq = ?";
		} else {
			sql = "select * from "
					+ "(select l.lectureName as 과정명, to_char(ls.subjectstartdate,'yyyy-mm-dd') as 시작일,to_char(ls.subjectenddate,'yyyy-mm-dd') as 종료일, "
					+ "    s.subjectName as 과목명 "
					+ "        from tblLectureSubject ls inner join tblLecture l on l.lectureSeq = ls.lectureSeq "
					+ "            inner join tblSubject s on s.subjectSeq = ls.subjectSeq "
					+ "                where l.lectureSeq = ? ) order by 시작일 ";
		}
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, seq);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			if (seq.indexOf("TC") > -1) {
				while (rs.next()) {
					olist.add(new Object[] { rs.getString("교사명") + "\t", rs.getString("과정명") + "", rs.getString("현황"),
							rs.getString("과목명") });
				}
				return olist;
			} else {
				while (rs.next()) {
					olist.add(new Object[] { rs.getString("과정명") + "  ", rs.getString("시작일"), rs.getString("종료일"),
							rs.getString("과목명")

					});
				}
				return olist;
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getSubject()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getSubject()");
		}

		return null;
	}

	/**
	 * 모든 교사 정보 메서드
	 * 
	 * @return 모든 교사 리스트 반환
	 */
	public ArrayList<Object[]> getAllTeacher() {
		String sql = "select tchseq as 교사번호, tchName as 교사명 from tblTeacher";
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("교사번호"), rs.getString("교사명") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getAllTeacher()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getAllTeacher()");
		}

		return null;
	}

	/**
	 * 과정 정보 메서드
	 * 
	 * @return 모든 과정 정보 반환
	 */
	public ArrayList<Object[]> getAllLecture() {
		String sql = "select l.lectureSeq as 과정변호,to_char(l.lecturestartdate,'yyyy-mm-dd') as 시작일, to_char(l.lectureenddate,'yyyy-mm-dd') as 종료일 "
				+ "    ,t.tchName as 교사명, l.lectureName as 과정명 "
				+ "        from tblLecture l inner join tblTeacher t on t.tchSeq = l.tchSeq";
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("과정변호"), "\t" + rs.getString("교사명") + "\t", rs.getString("시작일"),
						rs.getString("종료일"), rs.getString("과정명") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getAllLecture()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getAllLecture()");
		}
		return null;
	}

	/**
	 * 과정 취업률 정보
	 * 
	 * @param sel 과정 코드
	 * @return 과정에따른 취업률 반환
	 */
	public ArrayList<Object[]> getEmploymentRate(String sel) {
		String sql = "select (select lectureName from tblLecture where lectureSeq = ?) as 과정명, "
				+ " (select count(*) from tblStudent s inner join tblCourse c on s.stdSeq = c.stdSeq "
				+ "    inner join tblLecture l on l.lectureSeq = c.lectureSeq "
				+ "        inner join tblStudentManage sm on sm.courseseq = c.courseseq "
				+ "            where l.lectureSeq = ?)/(select count(*) from tblCourse c inner join tblStudent s on s.stdseq = c.stdSeq "
				+ "    inner join tblLecture l on l.lectureSeq = c.lectureSeq "
				+ "        where l.lectureSeq = ?)*100 as 취업률 from dual";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, sel);
			stat.setString(2, sel);
			stat.setString(3, sel);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();

			if (rs.next()) {
				olist.add(new Object[] { rs.getString("과정명"), "  " + rs.getString("취업률") + "%" });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getEmploymentRate()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getEmploymentRate()");
		}

		return null;
	}

	/**
	 * 과정 정보 수료율 중도탈락 정보 메서드
	 * 
	 * @param sel 과정 코드
	 * @return 과정 정보 수료율 중도탈락 정보 반환
	 */
	public ArrayList<Object[]> getCompletionRate(String sel) {
		String sql = "select (select lectureName from tblLecture where lectureSeq = ? ) as 과정명, "
				+ "    (select count(*) from tblLectureComplete lc inner join tblCourse c on lc.courseSeq = c.courseSeq "
				+ "    inner join tblLecture l on l.lectureSeq = c.lectureSeq where l.lectureSeq = ? )/(select count(*) from tblLecture l inner join tblCourse c on c.lectureSeq = l.lectureSeq "
				+ "    where l.lectureSeq = ? )*100 as 수료율, (select count(*) from tblDropOut do inner join tblCourse c on c.courseseq = do.courseseq "
				+ "    inner join tblLecture l on l.lectureSeq = c.lectureSeq where l.lectureSeq = ?) as 중도탈락 "
				+ "        from dual";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, sel);
			stat.setString(2, sel);
			stat.setString(3, sel);
			stat.setString(4, sel);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("과정명"), " " + rs.getString("수료율") + "%" + "\t",
						rs.getString("중도탈락") + "명" });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.getCompletionRate()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.getCompletionRate()");
		}

		return null;
	}

	// ---------------------------------------------------------------------------------교육생관리

	/**
	 * 과정 일련번호 반환
	 * 
	 * @param seq 과정 코드
	 * @return 과정 코드 반환
	 */
	public LectureDTO get(String seq) {

		LectureDTO lecture = new LectureDTO();

		try {

			String sql = "select lectureseq from tbllecture where lectureseq = 'L' || ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, seq);

			ResultSet rs = stat.executeQuery(sql);

			if (rs.next()) {

				lecture.setLectureSeq(rs.getString("lectureseq"));

				return lecture;
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.get()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.get()");
		}

		return null;
	}

	/**
	 * 교육생 정보 출력
	 * 
	 * @param seq  과정 코드
	 * @param name 학생 명
	 * @return 교육생 정보 반환
	 */

	public VwStudentDTO vwfind(String seq, String name) {

		VwStudentDTO result = new VwStudentDTO();

		try {

			String sql = "select LECTURESEQ,LECTURENAME,LECTURESTARTDATE,LECTUREENDDATE,LECTUREPROGRESS,CLASSSEQ,COURSESEQ,STDSEQ,STDNAME,STDSSN,STDTEL,TCHSEQ,TCHNAME"
					+ " from vwstudent where lectureseq = 'L'|| ? and stdname = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, seq);
			stat.setString(2, name);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {

				result.setLectureseq(rs.getString("LECTURESEQ"));
				result.setLecturename(rs.getString("LECTURENAME"));
				result.setLecturestartdate(rs.getString("LECTURESTARTDATE"));
				result.setLectureenddate(rs.getString("LECTUREENDDATE"));
				result.setLectureprogress(rs.getString("LECTUREPROGRESS"));
				result.setClassseq(rs.getString("CLASSSEQ"));
				result.setCourseseq(rs.getString("COURSESEQ"));
				result.setStdseq(rs.getString("STDSEQ"));
				result.setStdname(rs.getString("STDNAME"));
				result.setStdssn(rs.getString("STDSSN"));
				result.setStdtel(rs.getString("STDTEL"));
				result.setTchseq(rs.getString("TCHSEQ"));
				result.setTchname(rs.getString("TCHNAME"));

				return result;
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.vwfind()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.vwfind()");
		}

		return null;
	}

	/**
	 * 교육생 등록
	 * 
	 * @param std 학생 이름,주민번호,전화번호
	 * @return 교육생 등록 성공 여부
	 */

	public int insert(StudentDTO std) {

		try {

			String sql = "insert into tblStudent values ('ST' || stdseq.nextval,?,?,?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, std.getSTDName());
			stat.setString(2, std.getSTDSsn());
			stat.setString(3, std.getSTDTel());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.insert()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.insert()");
		}

		return 0;
	}

	/**
	 * 수강내역 교육생 등록 메소드
	 * 
	 * @param seq 과정코드
	 * @return 수강내역 교육생 등록 성공 여부
	 */
	public int add(String seq) {

		try {

			String sql = "insert into tblcourse values(courseSeq.nextval,'ST' || stdSeq.currval,'L'|| ?)";

			stat = conn.prepareStatement(sql);

			stat.setString(1, seq);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.add()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.add()");
		}

		return 0;
	}

	/**
	 * 교육생 존재 여부 메소드
	 * 
	 * @param seq  과정 코드
	 * @param name 학생 명
	 * @return 교육생 존재 여부
	 */

	public int stdCheck(String seq, String name) {

		try {

			String sql = "select count(*) as cnt from tblStudent where  stdseq = (select stdseq from vwStudent where lectureseq = 'L'|| ? and stdname = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, seq);
			stat.setString(2, name);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.stdCheck()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.stdCheck()");
		}

		return 0;
	}

	/**
	 * 교육생 삭제 메소드
	 * 
	 * @param seq  수강 코드
	 * @param name 학생 명
	 * @return 교육생 삭제 성공여부
	 */

	public int delete(String seq, String name) { // 교육생 삭제

		try {

			int result = deleteCourse(seq, name);

			if (result > 0) {
				String sql = "delete from tblstudent where stdSeq = (select stdseq from vwStudent where lectureseq = 'L'|| ? and stdname = ?)";

				PreparedStatement stat = conn.prepareStatement(sql);

				stat.setString(1, seq);
				stat.setString(2, name);

				return stat.executeUpdate();
			} else {

				System.out.println("★실패했습니다.  관리자에게 문의해주세요★");

			}
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.delete()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.delete()");
		}

		return 0;
	}

	/**
	 * 상담일지 삭제 메소드
	 * 
	 * @param seq  과정 코드
	 * @param name 학생 이름
	 * @return 상담일지 삭제 성공 여부
	 */

	private int deleteCourse(String seq, String name) {

		try {

			String sql = "update tblcourse set stdseq = null,lectureseq = null where courseseq = (select courseseq from vwStudent where lectureseq = 'L'|| ? and stdname = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, seq);
			stat.setString(2, name);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.deleteCourse()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.deleteCourse()");
		}

		return 0;
	}

	/**
	 * 전체 수강(과정) 목록 출력 메소드
	 * 
	 * @param isAuth 관리자 아이디
	 * @return 전체 과정 목록 출력
	 */

	public ArrayList<LectureDTO> Llist(boolean isAuth) {

		ArrayList<LectureDTO> list = new ArrayList<LectureDTO>();

		try {

			String sql = "select substr(lectureseq,2) as seq,lecturename from tbllecture";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {

				LectureDTO dto = new LectureDTO();

				dto.setLectureSeq(rs.getString("seq"));
				dto.setLectuerName(rs.getString("lecturename"));

				list.add(dto);

			}

			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.Llist()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.Llist()");
		}

		return null;
	}

	/**
	 * 교육생 등록 과정 선택 목록 메소드
	 * 
	 * @param b 관리자 아이디 로그인 존재 여부
	 * @return 수강(과정) 가능한 리스트 출력
	 */
	public ArrayList<LectureDTO> insertList(boolean b) {

		ArrayList<LectureDTO> list = new ArrayList<LectureDTO>();

		try {

			String sql = "select substr(l.lectureseq,2) as lectureseq, l.lecturename as lecturename, l.lecturecurrentstd as lecturecurrentstd from tbllecture l "
					+ " inner join"
					+ " (select lectureseq,count(*) as cnt from vwstudent group by lecturename,lectureseq order by lectureseq asc) s "
					+ " on l.lectureseq = s.lectureseq"
					+ " where l.lectureprogress = '강의예정' and l.lecturecurrentstd <= s.cnt";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {

				LectureDTO dto = new LectureDTO();

				dto.setLectureSeq(rs.getString("lectureseq"));
				dto.setLectuerName(rs.getString("lecturename"));
				dto.setLectureCurrentSTD(rs.getString("lecturecurrentstd"));

				list.add(dto);

			}

			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.insertList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.insertList()");
		}
		return null;
	}

	/**
	 * 교육생 상담일지 리스트 출력 메소드
	 * 
	 * @param seq 과정 코드
	 * @return 해당 과정 전체 교육생 상담일지 리스트 출력
	 */

	public ArrayList<courseRecoardListDTO> cousnselingList(int seq) {

		ArrayList<courseRecoardListDTO> list = new ArrayList<courseRecoardListDTO>();

		try {

			String sql = "select r.courseseq as courseseq,c.stdseq as stdseq, c.stdname as stdname,to_char(r.counseregdate,'yyyy-mm-dd') as counseregdate,r.counsecontents as counsecontents from tblcourserecord r"
					+ " inner join (select courseseq,stdseq,stdname from vwStudent where lectureseq = 'L' || ?) c"
					+ " on r.courseseq = c.courseseq order by r.counseseq asc";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setInt(1, seq);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				courseRecoardListDTO dto = new courseRecoardListDTO();

				dto.setCourseseq(rs.getString("courseseq"));
				dto.setStdSeq(rs.getString("stdseq"));
				dto.setStdName(rs.getString("stdname"));
				dto.setCounseregdate(rs.getString("counseregdate"));
				dto.setCounsecontents(rs.getString("counsecontents"));

				list.add(dto);

			}

			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.cousnselingList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.cousnselingList()");
		}
		return null;
	}

	/**
	 * 교육생 해당 과정 교육생 출력 메소드
	 * 
	 * @param seq 과정 코드
	 * @return 해당 과정 코드 학생 코드, 학생 이름 출력
	 */

	public ArrayList<VwStudentDTO> stdList(String seq) {

		ArrayList<VwStudentDTO> list = new ArrayList<VwStudentDTO>();

		try {

			String sql = "select stdseq,stdname from vwstudent where lectureseq = 'L' || ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, seq);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				VwStudentDTO dto = new VwStudentDTO();

				dto.setStdseq(rs.getString("stdseq"));
				dto.setStdname(rs.getString("stdname"));

				list.add(dto);
			}

			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.stdList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.stdList()");
		}
		return null;
	}

	/**
	 * 교육생 상담일지 정보 출력 메소드
	 * 
	 * @param stdseq 학생 코드
	 * @return 해당 교육생 상담일지 정보 출력
	 */

	public ArrayList<CourseRecordDTO> courseRecordStd(String stdseq) {

		ArrayList<CourseRecordDTO> list = new ArrayList<CourseRecordDTO>();

		try {

			String sql = "select to_char(counseregdate,'yyyy-mm-dd') as counseregdate,counsecontents from tblcourserecord where  courseseq = (select courseseq from vwStudent where stdseq = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, stdseq);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {

				CourseRecordDTO dto = new CourseRecordDTO();

				dto.setCounseRegdate(rs.getString("counseregdate"));
				dto.setCounseContents(rs.getString("counsecontents"));

				list.add(dto);
			}

			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.courseRecordStd()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.courseRecordStd()");
		}

		return null;
	}

	/**
	 * 교육생 과정 수강정보 존재 여부 메소드
	 * 
	 * @param seq  과정 코드
	 * @param name 학생 명
	 * @return 해당 교육생 수강정보 존재 여부
	 */

	public int find(String seq, String name) {

		try {

			String sql = "select count(*) as cnt from vwstudent where lectureseq = 'L' || ? and stdname = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, seq);
			stat.setString(2, name);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.find()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.find()");
		}
		return 0;
	}

	/**
	 * 교육생 이름 존재 여부 메소드
	 * 
	 * @param name 교육생이름
	 * @return 해당 교육생 이름 존재 여부
	 */

	public int stdName(String name) {

		try {

			String sql = "select count(*) as cnt from tblstudent where stdname = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, name);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.stdName()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.stdName()");
		}

		return 0;
	}

	/**
	 * 교육생 이름 수정 메소드
	 * 
	 * @param name       교육생 이름
	 * @param nameUpdate 수정 할 교육생 이름
	 * @return 교육생 이름 수정 성공 여부
	 */

	public int nameUpdate(String name, String nameUpdate) {

		try {

			StudentDTO std = new StudentDTO();

			String sql = "update tblstudent set stdname = ? where stdname = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, nameUpdate);
			stat.setString(2, name);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.nameUpdate()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.nameUpdate()");
		}

		return 0;
	}

	/**
	 * 교육생 주민번호 존재 여부
	 * 
	 * @param ssn 주민번호 조회
	 * @return 교육생 주민번호 존재여부
	 */

	public int stdSsn(String ssn) {

		try {

			String sql = "select count(*) as cnt from tblstudent where stdssn = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, ssn);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.stdSsn()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.stdSsn()");
		}

		return 0;
	}

	/**
	 * 교육생 주민번호 존재 수정 메소드
	 * 
	 * @param ssn       전 주민번호
	 * @param ssnUpdate 수정할 주민번호
	 * @return 교육생 주민번호 성공 여부
	 */

	public int ssnUpdate(String ssn, String ssnUpdate) {

		try {

			StudentDTO std = new StudentDTO();

			String sql = "update tblstudent set stdssn = ? where stdssn = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, ssnUpdate);
			stat.setString(2, ssn);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.ssnUpdate()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.ssnUpdate()");
		}
		return 0;
	}

	/**
	 * 교육생 전화번호 존재 여부 메소드
	 * 
	 * @param tel 전화번호
	 * @return 교육생 정보 존재 여부
	 */
	public int stdTel(String tel) {

		try {

			String sql = "select count(*) as cnt from tblstudent where stdtel = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, tel);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				return rs.getInt("cnt");
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.stdTel()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.stdTel()");
		}

		return 0;
	}

	/**
	 * 교육생 전화번호 정보 수정 메서드
	 * 
	 * @param tel       교육생 전화번호
	 * @param telUpdate 교육생 새전화번호
	 * @return 교육생 정보 수정 성공 여부
	 */
	public int telUpdate(String tel, String telUpdate) {

		try {

			StudentDTO std = new StudentDTO();

			String sql = "update tblstudent set stdssn = ? where stdssn = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, telUpdate);
			stat.setString(2, tel);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.telUpdate()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.telUpdate()");
		}
		return 0;
	}

	/**
	 * 교육생 과정 정보 출력 메서드
	 * 
	 * @param input 교육생 코드
	 * @return 과정, 교육생명, 교육생 코드 반환
	 */
	public VwStudentDTO vwStd(String input) {

		try {

			VwStudentDTO std = new VwStudentDTO();

			String sql = "select lecturename,stdseq,stdname from vwstudent where stdseq = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, input);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {

				std.setLecturename(rs.getString("lecturename"));
				std.setStdseq(rs.getString("stdseq"));
				std.setStdname(rs.getString("stdname"));
			}
			return std;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.vwStd()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.vwStd()");
		}

		return null;
	}

	/**
	 * 상담일지 등록 메서드
	 * 
	 * @param courseRecord 상담일지 DTO
	 * @return 상다일지 등록 성공 여부
	 */
	public int insetCourseRecord(CourseRecordDTO courseRecord) { // 상담일지 등록

		try {

			String sql = "insert into tblcourserecord values (counseseq.nextval,?,?,(select courseseq from vwStudent where stdseq = ?))";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, courseRecord.getCounseRegdate());
			stat.setString(2, courseRecord.getCounseContents());
			stat.setString(3, courseRecord.getCourseSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.insetCourseRecord()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.insetCourseRecord()");
		}

		return 0;
	}

	/**
	 * 상담일지 삭제 메서드
	 * 
	 * @param data  날짜
	 * @param input 수강내역 번호
	 * @return 상담일지 삭제 성공 여부
	 */
	public int deleteCourseRecord(String data, String input) {

		try {
			String sql = "delete from tblcourserecord where to_char(counseregdate,'yyyy-mm-dd') = ? and courseseq =(select courseseq from vwStudent where stdseq = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, data);
			stat.setString(2, input);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.deleteCourseRecord()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.deleteCourseRecord()");
		}
		return 0;
	}

	/**
	 * 상담일지 수정하기 메서드
	 * 
	 * @param input    수강 내역번호
	 * @param data     날짜
	 * @param contents 상담내용
	 * @return 상담일지 내용 성공 여부
	 */
	public int updateCourseRecord(String input, String data, String contents) {

		try {

			String sql = "update tblcourserecord set counsecontents = ? where to_char(counseregdate,'yyyy-mm-dd') = ? and courseseq = (select courseseq from vwStudent where stdseq = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, contents);
			stat.setString(2, data);
			stat.setString(3, input);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateCourseRecord()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateCourseRecord()");
		}

		return 0;
	}

	/**
	 * 사후처리 조회 메서드
	 * 
	 * @param lectureseq 과정 코드
	 * @param stdseq     교육생 코드
	 * @return 사후 처리 등록 여부 확인
	 */
	public int OversightCnt(String lectureseq, String stdseq) {

		try {

			String sql = "select count(*) as cnt from tblstudentmanage where courseseq = (select courseseq from vwstudent where lectureseq = ? and stdseq = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lectureseq);
			stat.setString(2, stdseq);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				int cnt = rs.getInt("cnt");
				if (cnt > 0) {
					return cnt;
				} else {
					return 0;
				}

			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.OversightCnt()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.OversightCnt()");
		}

		return 0;
	}

	/**
	 * 사후 관리 조회 메서드
	 * 
	 * @param lectureseq 과정 조회
	 * @param stdseq     교육생 코드
	 * @return 사후 처리 등록 되어
	 */
	public StudentManageDTO selectStdManage(String lectureseq, String stdseq) {

		try {

			String sql = "select manageseq,companyname,courseseq from tblstudentmanage where courseseq = (select courseseq from vwstudent where lectureseq = ? and stdseq = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lectureseq);
			stat.setString(2, stdseq);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {

				StudentManageDTO dto = new StudentManageDTO();

				dto.setManageSeq(rs.getString("manageseq"));
				dto.setCompanyName(rs.getString("companyname"));
				dto.setCourseSeq(rs.getString("courseseq"));

				return dto;

			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.selectStdManage()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.selectStdManage()");
		}

		return null;
	}

	/**
	 * 교육생 정보 및 과정 정보 메서드
	 * 
	 * @param input 과정 코드
	 * @param seq   교육생 코드
	 * @return 교육생 정보 및 과정 정보 반환
	 */
	public VwStudentDTO vwfind2(String input, String seq) {

		VwStudentDTO result = new VwStudentDTO();

		try {

			String sql = "select LECTURESEQ,LECTURENAME,LECTURESTARTDATE,LECTUREENDDATE,LECTUREPROGRESS,CLASSSEQ,STDSEQ,STDNAME,STDSSN,STDTEL,TCHSEQ,TCHNAME"
					+ " from vwstudent where lectureseq = 'L'|| ? and stdseq = ?";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, input);
			stat.setString(2, seq);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {

				result.setLectureseq(rs.getString("LECTURESEQ"));
				result.setLecturename(rs.getString("LECTURENAME"));
				result.setLecturestartdate(rs.getString("LECTURESTARTDATE"));
				result.setLectureenddate(rs.getString("LECTUREENDDATE"));
				result.setLectureprogress(rs.getString("LECTUREPROGRESS"));
				result.setClassseq(rs.getString("CLASSSEQ"));
				result.setStdseq(rs.getString("STDSEQ"));
				result.setStdname(rs.getString("STDNAME"));
				result.setStdssn(rs.getString("STDSSN"));
				result.setStdtel(rs.getString("STDTEL"));
				result.setTchseq(rs.getString("TCHSEQ"));
				result.setTchname(rs.getString("TCHNAME"));

				return result;
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.vwfind2()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.vwfind2()");
		}

		return null;

	}

	/**
	 * 사후 관리 등록 메서드
	 * 
	 * @param dto 사후 관리 DTO
	 * @return 사후관리 등록 성공 여부
	 */
	public int insertOversight(StudentManageDTO dto) {

		try {

			String sql = "insert into tblstudentmanage values (manageseq.nextval,?,?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, dto.getCompanyName());
			stat.setString(2, dto.getCourseSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.insertOversight()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.insertOversight()");
		}

		return 0;
	}

	/**
	 * 사후관리 삭제 메서드
	 * 
	 * @param stdseq 교육생 코드
	 * @return 사후관리 삭제 성공 여부
	 */
	public int deleteOversight(String stdseq) {

		try {

			String sql = "delete from tblstudentmanage where courseseq = (select courseseq from vwStudent where stdseq = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, stdseq);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.deleteOversight()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.deleteOversight()");
		}

		return 0;
	}

	/**
	 * 사후관리 등록 메서드
	 * 
	 * @param stdseq  교육생 코드
	 * @param company 회사명
	 * @return 사후관리 등록 성공 여부
	 */
	public int updateOversight(String stdseq, String company) {

		try {

			String sql = "update tblstudentmanage set companyname = ? where courseseq = (select courseseq from vwStudent where stdseq = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, company);
			stat.setString(2, stdseq);

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateOversight()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateOversight()");
		}

		return 0;
	}

	// ---------------------------------------------------------------------------------------------------------------------교육생관리
	// 끝

	/**
	 * 필기 점수 수정 메서드
	 * 
	 * @param studentgradeinfo 필기 점수
	 * @return 필기 점수 수정 성공 여부
	 */
	public int updateNoteGrade(StudentGradeInfoDTO studentgradeinfo) {

		// 필기점수 수정 메소드

		try {

			String sql = "update tblGrade " + "set gradenotescore = ? "
					+ "where lecsubseq in (select lecSubSeq from tblLectureSubject where subjectSeq = ?) "
					+ "and courseSeq =  (select courseSeq from tblCourse where stdSeq = (select stdSeq from tblStudent where stdName = ? ))";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, studentgradeinfo.getUpdateGradeNoteScore());
			stat.setString(2, studentgradeinfo.getSubjectSeq());
			stat.setString(3, studentgradeinfo.getSTDName());

			stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateNoteGrade()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateNoteGrade()");
		}

		return 0;
	}

	/**
	 * 실기 점수 수정 메서드
	 * 
	 * @param studentgradeinfo 실기 점수
	 * @return 실기 점수 수정 성공 여부
	 */
	public int updateSkillGrade(StudentGradeInfoDTO studentgradeinfo) {

		// 실기점수 수정 메소드

		try {

			String sql = "update tblGrade " + "set gradeskillscore = ? "
					+ "where lecsubseq in (select lecSubSeq from tblLectureSubject where subjectSeq = ?) "
					+ "and courseSeq =  (select courseSeq from tblCourse where stdSeq = (select stdSeq from tblStudent where stdName = ? ))";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, studentgradeinfo.getUpdateGradeSkillScore());
			stat.setString(2, studentgradeinfo.getSubjectSeq());
			stat.setString(3, studentgradeinfo.getSTDName());

			stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateSkillGrade()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateSkillGrade()");
		}

		return 0;
	}

	/**
	 * 출석 점수 수정 메소드
	 * 
	 * @param studentgradeinfo 출석 점수
	 * @return 출석 점수 수정 성공 여부
	 */
	public int updateAttendanceGrade(StudentGradeInfoDTO studentgradeinfo) {

		// 출석점수 수정 메소드

		try {

			String sql = "update tblGrade " + "set gradeattendancescore = ? "
					+ "where lecsubseq in (select lecSubSeq from tblLectureSubject where subjectSeq = ?) "
					+ "and courseSeq =  (select courseSeq from tblCourse where stdSeq = (select stdSeq from tblStudent where stdName = ? ))";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, studentgradeinfo.getUpdateGradeAttendanceScore());
			stat.setString(2, studentgradeinfo.getSubjectSeq());
			stat.setString(3, studentgradeinfo.getSTDName());

			stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateAttendanceGrade()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateAttendanceGrade()");
		}

		return 0;
	}

	/**
	 * 필기 문제 리스트 조회 메서드
	 * 
	 * @param subjectname 과목명
	 * @return 필기 문제 list 반환
	 */
	public ArrayList<NoteTestDTO> noteTestList(String subjectname) {

		ArrayList<NoteTestDTO> list = new ArrayList<NoteTestDTO>();

		// 필기 문제 리스트 조회
		try {

			String sql = "select notequeseq, notedistribution, notequestion from tblNoteTest "
					+ "where subjectseq = (select subjectseq from tblSubject where subjectname = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, subjectname);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {

				NoteTestDTO dto = new NoteTestDTO();
				dto.setNoteQueSeq(rs.getString("notequeseq"));
				dto.setNoteDistribution(rs.getString("notedistribution"));
				dto.setNoteQuestion(rs.getString("notequestion"));
				list.add(dto);

			}
			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.noteTestList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.noteTestList()");
		}

		return null;
	}

	/**
	 * 필기 문제 수정 메서드
	 * 
	 * @param questnum     문제번호
	 * @param quest        문제
	 * @param distribution 배점
	 * @return 필기 문제 수정 성공 여부
	 */
	public NoteTestDTO updateNoteTestQuest(String questnum, String quest, String distribution) {

		// 필기 문제 수정 메소드

		try {

			String sql = "{call procUpdateNoteTest(?, ?, ?, ?)}";
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, questnum);
			stat.setString(2, quest);
			stat.setString(3, distribution);
			stat.registerOutParameter(4, OracleTypes.NUMBER);

			stat.execute();

			if (stat.getInt(4) == 1) {
				System.out.println("문제가 수정 되었습니다.");
			} else {
				System.out.println("수정 작업에 실패하였습니다.");
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateNoteTestQuest()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateNoteTestQuest()");
		}

		return null;
	}

	/**
	 * 실기문제 리스트 조회 메서드
	 * 
	 * @param subjectname 과목명
	 * @return 과목 실기 문제 list 반환
	 */
	public ArrayList<SkillTestDTO> skillTestList(String subjectname) {

		ArrayList<SkillTestDTO> list = new ArrayList<SkillTestDTO>();

		// 실기 문제 리스트 조회
		try {

			String sql = "select skillqueseq, skilldistribution, skillquestion "
					+ "from tblSkillTest where subjectseq = (select subjectseq from tblSubject where subjectname = ?)";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, subjectname);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {

				SkillTestDTO dto = new SkillTestDTO();
				dto.setSkillQueSeq(rs.getString("skillqueseq"));
				dto.setSkillDistribution(rs.getString("skilldistribution"));
				dto.setSkillQuestion(rs.getString("skillquestion"));
				list.add(dto);

			}
			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.skillTestList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.skillTestList()");
		}

		return null;
	}

	/**
	 * 실기 문제 수정 메소드
	 * 
	 * @param questnum     문제번호
	 * @param quest        문제
	 * @param distribution 배점
	 * @return 실기 문제 수정 성공 여부
	 */
	public SkillTestDTO updateSkillTestQuest(String questnum, String quest, String distribution) {

		// 실기 문제 수정 메소드
		try {

			String sql = "{call procUpdateSkillTest(?, ?, ?, ?)}";
			CallableStatement stat = conn.prepareCall(sql);
			stat.setString(1, questnum);
			stat.setString(2, quest);
			stat.setString(3, distribution);
			stat.registerOutParameter(4, OracleTypes.NUMBER);

			stat.execute();

			if (stat.getInt(4) == 1) {
				System.out.println("문제가 수정 되었습니다.");
			} else {
				System.out.println("수정 작업에 실패하였습니다.");
			}

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateSkillTestQuest()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateSkillTestQuest()");
		}

		return null;
	}

	/**
	 * 배점 수정 메서드
	 * 
	 * @param distribution 배점
	 * @return 배점 수정 성공 여부
	 */
	public int updateDistribution(DistributionDTO distribution) {

		// 배점 테이블 수정 메소드
		try {

			String sql = "update tblDistribution set dstrNote = ?, dstrskill = ?, dstrattendance = ? where dstrseq = 1";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, distribution.getDstrNote());
			stat.setString(2, distribution.getDstrSkill());
			stat.setString(3, distribution.getDstrAttendance());

			stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.updateDistribution()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.updateDistribution()");
		}

		return 0;
	}

	/**
	 * 과정별 시험 성적 조회 메서드
	 * 
	 * @param stdSeq 교육생 코드
	 * @return 과정별 시험 성적 조회 list
	 */
	public ArrayList<DetailScoreListDTO> ScoreList(String stdSeq) {

		ArrayList<DetailScoreListDTO> list = new ArrayList<DetailScoreListDTO>();

		try {

			String sql = "select distinct  c.STDSEQ as 학생코드, std.STDName as 학생명, s.subjectname as 과목명, "
					+ "                g.gradenotescore as 필기점수, g.gradeskillscore as 실기점수, g.gradeattendancescore as 출결점수, l.lectureName as 과정명"
					+ " from tblGrade g " + "    inner join tblLectureSubject ls "
					+ "        on g.lecSubSeq = ls.lecSubSeq " + "            inner join tblLecture l "
					+ "                on l.lectureSeq = ls.lectureSeq "
					+ "                    inner join tblSubject s "
					+ "                        on ls.subjectSeq = s.subjectSeq "
					+ "                            inner join tblCourse c "
					+ "                                on c.courseSeq = g.courseSeq "
					+ "                                    inner join tblStudent std "
					+ "                                        on std.STDseq = c.STDSeq "
					+ "											where std.STDseq = ? "
					+ "                                            	order by std.STDSEQ";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, stdSeq);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {

				DetailScoreListDTO scoreDTO = new DetailScoreListDTO();

				scoreDTO.setStdSeq(rs.getString("학생코드"));
				scoreDTO.setStdName(rs.getString("학생명"));
				scoreDTO.setSubjectName(rs.getString("과목명"));
				scoreDTO.setGradeNoteScore(rs.getString("필기점수"));
				scoreDTO.setGrageSkillScore(rs.getString("실기점수"));
				scoreDTO.setGradeAttendanceScore(rs.getString("출결점수"));
				scoreDTO.setLectureName(rs.getString("과정명"));

				list.add(scoreDTO);
			}
			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.ScoreList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.ScoreList()");
		}

		return null;
	}

	/**
	 * 과정, 학생번호, 학생 명 리스트 출력 점수가 0인 학생에 대한 출력 제외 메서드
	 * 
	 * @return 과정 별 시험 성적 list 반환
	 */
	public ArrayList<LectureListDTO> LectureListDTO() {
		ArrayList<LectureListDTO> list = new ArrayList<LectureListDTO>();

		try {

			String sql = "select distinct l.lectureName as lectureName, c.STDSEQ as STDSeq, std.STDName as STDName "
					+ "from tblGrade g " + "    inner join tblLectureSubject ls "
					+ "        on g.lecSubSeq = ls.lecSubSeq " + "            inner join tblLecture l "
					+ "                on l.lectureSeq = ls.lectureSeq "
					+ "                    inner join tblSubject s "
					+ "                        on ls.subjectSeq = s.subjectSeq "
					+ "                            inner join tblCourse c "
					+ "                                on c.courseSeq = g.courseSeq "
					+ "                                    inner join tblStudent std "
					+ "                                        on std.STDseq = c.STDSeq "
					+ "                                            where g.gradenotescore <> 0 "
					+ "                                            order by c.STDSEQ ";

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				LectureListDTO lecDTO = new LectureListDTO();

				lecDTO.setLectureName(rs.getString("lectureName"));
				lecDTO.setSTDSeq(rs.getString("STDSeq"));
				lecDTO.setSTDName(rs.getString("STDName"));

				list.add(lecDTO);
			}

			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.LectureListDTO()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.LectureListDTO()");
		}
		return null;
	}

	/**
	 * 에러 로그 목록
	 * 
	 * @return 에러 로그 리스트
	 */
	public ArrayList<ErrorLogDTO> ErorrLog() {
		ArrayList<ErrorLogDTO> list = new ArrayList<>();

		try {
			String sql = "select * from tblErrorLog";

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				ErrorLogDTO elDTO = new ErrorLogDTO();

				elDTO.setErrorCode(rs.getString("errorSeq"));
				elDTO.setErrorSeq(rs.getString("errorSeq"));
				elDTO.setErrorDate(rs.getString("errorDate"));

				list.add(elDTO);
			}
			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.ErorrLog()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.ErorrLog()");
		}
		return null;
	}

	/**
	 * 로그 목록
	 * 
	 * @return 로그 리스트
	 */
	public ArrayList<LogDTO> Log() {
		ArrayList<LogDTO> list = new ArrayList<>();

		try {
			String sql = "select * from tblLog";

			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				LogDTO lDTO = new LogDTO();

				lDTO.setLogSeq(rs.getString("logSeq"));
				lDTO.setLogCode(rs.getString("logCode"));
				lDTO.setLogDate(rs.getString("logDate"));
				lDTO.setLogContents(rs.getString("logContents"));

				list.add(lDTO);
			}
			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.Log()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.Log()");
		}
		return null;
	}

	/**
	 * 현재 '강의예정'인 과정 목록 출력
	 * 
	 * @return 강의 예정 과목 목록
	 */
	public ArrayList<LectureDTO> showCurrentLecture() {

		ArrayList<LectureDTO> leclist = new ArrayList<LectureDTO>();

		try {

			String sql = "select LectureSEq, lectureName, lectureProgress " + "    from tblLecture "
					+ "        where lectureProgress = '강의예정'";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {

				LectureDTO lecDTO = new LectureDTO();

				lecDTO.setLectureSeq(rs.getString("lectureSeq"));
				lecDTO.setLectuerName(rs.getString("lectureName"));
				lecDTO.setLectureProgress(rs.getString("lectureProgress"));

				leclist.add(lecDTO);
			}

			return leclist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.showCurrentLecture()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.showCurrentLecture()");
		}

		return null;
	}

	/**
	 * 전체 과목 리스트 출력
	 * 
	 * @param lectureSeq
	 * @param lectureSeq
	 * @return 전체 과목 리스트
	 */
	public ArrayList<SubjectDTO> LecutureSubjectList(String lectureSeq) {

		ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();

		try {

			String sql = "select subjectSeq, subjectName from tblsubject";

			PreparedStatement stat = conn.prepareStatement(sql);
			// stat.setString(1, lectureSeq);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {

				SubjectDTO subDTO = new SubjectDTO();

				subDTO.setSubjectSeq(rs.getString("subjectSeq"));
				subDTO.setSubjectName(rs.getString("subjectName"));

				list.add(subDTO);

			}
			return list;

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.LecutureSubjectList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.LecutureSubjectList()");
		}

		return null;
	}

	/**
	 * 선택 과정 > 과목 에 대한 교재 리스트 출력
	 * 
	 * @param subjectSeq 과목 코드
	 * @param lectureSeq 과정 코드
	 * @return 과목 하위 교재 리스트
	 */
	public ArrayList<TextBookDTO> textBookList(String subjectSeq) {

		ArrayList<TextBookDTO> textList = new ArrayList<TextBookDTO>();

		try {

			String sql = "select distinct s.subjectSeq as subjectSeq, s.subjectname as subjectName,t.textbookseq as textbookSeq, t.textbookname as textbookName "
					+ "    from tblSubject s " + "        inner join tblTextbook t "
					+ "            on t.subjectSeq = s.subjectSeq " + " 				where s.subjectSeq = ? "
					+ "                order by s.subjectName ";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, subjectSeq);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {

				TextBookDTO textDTO = new TextBookDTO();

				// textDTO.setSubjectSeq(rs.getString("subjectSeq"));
				textDTO.setTextBookSeq(rs.getString("textbookSeq"));
				textDTO.setTextBookName(rs.getString("textbookName"));

				textList.add(textDTO);
			}

			return textList;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.textBookList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.textBookList()");
		}
		return null;
	}

	/**
	 * LectureSubject 테이블로 insert
	 * 
	 * @param lecSubReg
	 * @return 결과 성공 여부 반환
	 */
	public int registLectureSubject(LectureSubjectDTO lecSubReg) {

		try {

			String sql = "insert into tblLectureSubject " + " values (LECTURESUBJECTSEQ.nextval, ?,?,?,?,?)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, lecSubReg.getLectureSeq());
			stat.setString(2, lecSubReg.getSubjectSeq());
			stat.setString(3, lecSubReg.getSubjectStartDate());
			stat.setString(4, lecSubReg.getSubjectEndDate());
			stat.setString(5, lecSubReg.getTextBookSeq());

			return stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.registLectureSubject()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.registLectureSubject()");
		}

		return 0;
	}

	/**
	 * 관리자 로그인 기록
	 */
	public void LoginLog() {
		try {
			String sql = "insert into tblLogIn " + "values (loginSeq.nextval, ?, default, null)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, MainClass.isAuth);

			stat.executeUpdate();

		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.LoginLog()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.LoginLog()");
		}
	}

	/**
	 * 관리자 로그아웃 기록
	 */
	public void LogoutLog() {
		try {
			String sql = "update tblLogIn set logoutdate = sysdate "
					+ "where loginSeq = (select max(loginSeq) from tblLogIn where logincode = ? and logoutdate is null)";

			PreparedStatement stat = conn.prepareStatement(sql);

			stat.setString(1, MainClass.isAuth);

			stat.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.LogoutLog()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.LogoutLog()");
		}
	}

	/**
	 * 선택 과정의 현재 과목 리스트 출력
	 * 
	 * @param lectureSeq
	 * @return
	 */
	public ArrayList<SubjectDTO> subjectList(String lectureSeq) {
		ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();

		try {

			String sql = "select A.lectureSeq, A.lectureName, A.subjectseq,  A.subjectname from "
					+ "            (select l.lectureSEq as lectureSeq, l.lectureName, s.subjectseq as subjectSeq,  s.subjectname as subjectName from tbllecturesubject ls "
					+ "                  inner join tblLecture l "
					+ "                       on l.lectureseq = ls.lectureseq "
					+ "                          inner join tblSubject s                "
					+ "                            on s.subjectseq = ls.subjectseq "
					+ "                                 where l.lectureprogress = '강의예정' and l.lectureSeq = ? "
					+ "                                    order by s.subjectname) A "
					+ "                                         group by A.subjectseq, A.subjectname,A.lectureName, A.lectureSeq "
					+ "                                            order by A.subjectseq";

			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, lectureSeq);

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				SubjectDTO dto = new SubjectDTO();

				dto.setLectureName(rs.getString("lectureName"));
				dto.setLectureSeq(rs.getString("lectureSeq"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setSubjectSeq(rs.getString("subjectSeq"));

				list.add(dto);
			}
			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.LogoutLog()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.LogoutLog()");
		}

		return null;
	}

	/**
	 * 관리자 로그인 기록
	 * 
	 * @return 관리자 로그인 기록 리스트 반환
	 */
	public ArrayList<LoginDTO> AdminLoginLog() {	
		ArrayList<LoginDTO> list = new ArrayList<>();
		
		try {
			String sql = "select * from tblLogin";
			
			Statement stat = conn.createStatement();
			
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				LoginDTO ldto = new LoginDTO();
				
				ldto.setLogInSeq(rs.getString("loginSeq"));
				ldto.setLogInCode(rs.getString("loginCode"));
				ldto.setLogInDate(rs.getString("loginDate"));
				ldto.setLogoutDate(rs.getString("logoutDate"));
				
				list.add(ldto);
			}
			
			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.AdminLoginLog()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.AdminLoginLog()");
		}
		return null;
	}

	public ArrayList<LoginDTO> TeacherLoginLog() {
		ArrayList<LoginDTO> list = new ArrayList<>();
		
		try {
			String sql = "select * from tblTeacherLog";
			
			Statement stat = conn.createStatement();
			
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				LoginDTO ldto = new LoginDTO();
				
				ldto.setLogInSeq(rs.getString("loginSeq"));
				ldto.setLogInCode(rs.getString("loginCode"));
				ldto.setLogInDate(rs.getString("loginDate"));
				ldto.setLogoutDate(rs.getString("logoutDate"));
				
				list.add(ldto);
			}
			
			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.TeacherLoginLog()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.TeacherLoginLog()");
		}
		return null;
	}

	public ArrayList<LoginDTO> StudentLoginLog() {
		ArrayList<LoginDTO> list = new ArrayList<>();
		
		try {
			String sql = "select * from tblStudentLog";
			
			Statement stat = conn.createStatement();
			
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				LoginDTO ldto = new LoginDTO();
				
				ldto.setLogInSeq(rs.getString("loginSeq"));
				ldto.setLogInCode(rs.getString("loginCode"));
				ldto.setLogInDate(rs.getString("loginDate"));
				ldto.setLogoutDate(rs.getString("logoutDate"));
				
				list.add(ldto);
			}
			
			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("오라클에러", "AdminDAO.AdminLoginLog()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			systemError("알수없는에러", "AdminDAO.AdminLoginLog()");
		}
		return null;
	}
}












