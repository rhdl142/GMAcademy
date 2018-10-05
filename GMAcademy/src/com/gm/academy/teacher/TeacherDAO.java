package com.gm.academy.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gm.academy.Util.DBUtil;
import com.gm.academy.Util.TeacherUtil;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.admin.AdminDAO;
import com.gm.academy.admin.DistributionDTO;
import com.gm.academy.exam.GradeDTO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.SubjectDTO;
import com.gm.academy.student.StudentDTO;

/**
 * 교사와 DB기능
 * @author 3조
 *
 */
public class TeacherDAO {
	private Connection conn;
	private UtilPrint out;
	private AdminDAO dao;

	public TeacherDAO() {
		this.conn = DBUtil.getConnection("211.63.89.42", "Project", "JAVA1234");
		this.out = new UtilPrint();
		this.dao = new AdminDAO();
	}
	/**
	 * 아이디와 비밀번호를 받아 값이 존재하면 1을 반환하는 메소드
	 * @param id 아이디 
	 * @param pw 비밀번호
	 * @return 성공여부
	 */
	public int login(String id, String pw) {
		String sql = "select tchSeq from tblTeacherLogin where tchid = ? and tchpw = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.setString(2, pw);
			ResultSet rs = stat.executeQuery();
			String seq = "";
			if (rs.next()) {
				seq = rs.getString("tchSeq");
			}
			TeacherUtil.loginTchSeq = seq;
			rs.close();
			stat.close();

			sql = "select * from tblTeacher where tchSeq = ?";
			stat = conn.prepareStatement(sql);
			stat.setString(1, seq);
			rs = stat.executeQuery();
			if (rs.next()) {
				TeacherDTO dto = new TeacherDTO();
				dto.setTCHSeq(rs.getString("tchSeq"));
				dto.setTCHName(rs.getString("tchName"));
				dto.setTCHSsn(rs.getString("tchSsn"));
				dto.setTCHTel(rs.getString("tchTel"));
				TeacherUtil.loginTeacher = dto;
				return 1;
			}
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.login()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.login()");
		}
		return 0;
	}
	
	/**
	 * 기간별 학생 출결사항을 조회하는 쿼리를 DB에 전달하여 ArrayList<Object[]>로 반환해주는 메소드
	 * @param year 연
	 * @param month 월 
	 * @param day 일
	 * @return
	 */
	public ArrayList<Object[]> showAttendanceByDay(String year, String month, String day) {
		String sql = "select s.stdName as 학생명,l.lecturename as 과정명, to_char(ontime,'hh24:mi:ss') as 출석시간,to_char(offTime,'hh24:mi:ss') as 퇴실시간 "
				+ "    ,ab.absencesituation as 출결상황    " + "        from tblAttendance a "
				+ "            inner join tblabsencerecord ab on a.absenceseq = ab.absenceseq"
				+ "                inner join tblCourse c on c.courseseq = a.courseseq"
				+ "                    inner join tblStudent s on s.stdSeq = c.stdseq"
				+ "                        inner join tblLecture l on l.lectureSeq = c.lectureseq"
				+ "                            where to_char(a.ontime,'yyyy-mm-dd') = ? and l.tchseq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, String.format("%s-%s-%s", year, month, day));
			stat.setString(2, TeacherUtil.loginTchSeq);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> array = new ArrayList<Object[]>();
			while (rs.next()) {
				Object[] list = { rs.getString("학생명"), rs.getString("과정명"), "\t"+rs.getString("출석시간"), rs.getString("퇴실시간"),
						rs.getString("출결상황") };
				array.add(list);
			}
			return array;
		} catch (SQLSyntaxErrorException e) {
	         out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
	         dao.systemError("오라클에러","showAttendanceByDay");
	      } catch (Exception e) {
	         out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
	         dao.systemError("오라클에러","showAttendanceByDay");
	      }
		return null;
	}

	/**
	 * 과정별 학생 출결사항을 조회하는 쿼리를 DB에 전달하여 ArrayList<Object[]>로 반환해주는 메소드
	 * @param selectedLecture 선택된 과정
	 * @return 데이터 배열
	 */
	public ArrayList<Object[]> showAttendanceByLecture(LectureDTO selectedLecture) {
		String sql = "select s.stdseq as 학생코드, s.stdname as 학생명, s.stdtel as 전화번호, to_char(ontime,'yyyy-mm-dd') as 날짜"
				+ "    ,ar.absencesituation as 출결"
				+ "        from tblAttendance a inner join tblCourse c on c.courseseq = a.courseseq"
				+ "            inner join tblStudent s on s.stdseq = c.stdseq"
				+ "                inner join tblAbsenceRecord ar on ar.absenceSeq = a.absenceSeq where c.lectureseq = ?";
		ArrayList<Object[]> olist = new ArrayList<Object[]>();
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, selectedLecture.getLectureSeq());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("학생코드"), rs.getString("학생명"), rs.getString("전화번호"),
						rs.getString("날짜"), rs.getString("출결") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.showAttendanceByLecture()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.showAttendanceByLecture()");
		}

		return null;
	}
	
	/**
	 * 상담일지를 등록하는 쿼리를 DB에 전달하여 삽입된 행의 개수를 반환.
	 * @param stdSeq 학생번호
	 * @param lectureSeq 과정번호
	 * @param content 상담내용
	 * @return 등록 결과
	 */
	public int addCourseRecord(String stdSeq, String lectureSeq, String content) {
		String sql = "insert into tblCourseRecord values(COUNSESEQ.nextval,sysdate,?,(select courseSeq from tblCourse where lectureseq = ? and stdSeq = ?))";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, content);
			stat.setString(2, lectureSeq);
			stat.setString(3, stdSeq);
			return stat.executeUpdate();
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.showAttendanceByLecture()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.showAttendanceByLecture()");
		}
		return 0;
	}
	
	/**
	 * 상담일지를 조회하는 쿼리를 DB에 전달하여 ArrayList<Object[]>로 반환해주는 메소드
	 * @param stdSeq 학생번호
	 * @param lectureSeq 과정번호
	 * @return 데이터 배열
	 */
	public ArrayList<Object[]> showCourseRecord(String stdSeq, String lectureSeq) {
		String sql = "select counseseq, counsecontents from tblCourseRecord where courseSeq in "
				+ "    (select courseseq from tblCourse where stdSeq = ? and lectureSeq = ?)";

		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, stdSeq);
			stat.setString(2, lectureSeq);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("counseseq"), "\t"+rs.getString("counsecontents") });
			}
			return olist;
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.showCourseRecord()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.showCourseRecord()");
		}

		return null;
	}
	
	/**
	 * 교사평가를 조회하는 쿼리를 DB에 전달하고 반환값을 ArrayList<Object[]>로 반환해주는 메소드
	 * @return 데이터 배열
	 */
	public ArrayList<Object[]> showTeacherEvaluation() {
		String sql = "select le.evalLecSeq as 번호,t.tchname as 교사명 ,l.lectureName as 과정명, " + 
				"				 case " + 
				"                    when le.evalleccomment is null then ' ' " + 
				"                    else le.evalleccomment " + 
				"                 end as 코멘트 " + 
				"                 ,le.evalLecScore as 점수 from tblLectureEvaluation le inner join tblCourse c on c.courseSeq = le.courseseq " + 
				"				    inner join tblLecture l on l.lectureSeq = le.lectureSeq inner join tblTeacher t on t.tchSeq = le.tchSeq " + 
				"				        where le.tchseq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, TeacherUtil.loginTeacher.getTCHSeq());
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("번호"), rs.getString("교사명"), rs.getString("과정명"),
						"   "+rs.getString("점수"), rs.getString("코멘트") });
			}
			return olist;
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.showTeacherEvaluation()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.showTeacherEvaluation()");
		}
		return null;
	}

	/**
	 * 배점의 조회문을 DB에 전달하고 값을 가진 객체를 반환하는 메소드
	 * @return 배점객체
	 */
	public DistributionDTO getDistribution() {
		String sql = "select * from tblDistribution";

		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			if (rs.next()) {
				DistributionDTO dto = new DistributionDTO();
				dto.setDstrSeq(rs.getString("dstrSeq"));
				dto.setDstrNote(rs.getString("dstrNote"));
				dto.setDstrSkill(rs.getString("dstrSkill"));
				dto.setDstrAttendance(rs.getString("dstrAttendance"));
				return dto;
			}
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.getDistribution()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.getDistribution()");
		}
		return null;
	}
	
	/**
	 * 배점정보를 수정하는 쿼리를 DB에 전달해주는 메소드
	 * @param note 필기점수
	 * @param skill 실기점수
	 * @param attendance 출결점수
	 * @return
	 */
	public int setDistribution(int note, int skill, int attendance) {
		String sql = "update tblDistribution set dstrnote = ? ,dstrskill = ? , dstrattendance =?";

		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, note);
			stat.setInt(2, skill);
			stat.setInt(3, attendance);
			int result = stat.executeUpdate();
			return result;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.setDistribution()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.setDistribution()");
		}
		return 0;
	}
	
	/**
	 * 성적정보를 DB상에 등록하는 메소드, 행의 개수 반환.
	 * @param stdSeq 학생번호
	 * @param note 필기점수
	 * @param skill 실기점수
	 * @param attendance 출결점수
	 * @param subjectSeq 과목번호
	 * @return
	 */
	public int addGrade(String stdSeq, int note, int skill, int attendance, String subjectSeq) {
		String sql = "select ls.lecsubseq as 과정과목번호,c.courseseq as 수강내역번호 from tblCourse c inner join tblLecture l on c.lectureseq = l.lectureseq "
				+ "    inner join tblLectureSubject ls on ls.lectureseq = l.lectureseq where c.stdseq = ? and ls.subjectseq = ?";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, stdSeq);
			stat.setString(2, subjectSeq);
			ResultSet rs = stat.executeQuery();
			int lecSubSeq = 0;
			int courseSeq = 0;
			if (rs.next()) {
				lecSubSeq = rs.getInt("과정과목번호");
				courseSeq = rs.getInt("수강내역번호");
			}
			rs.close();
			stat.close();

			sql = "insert into tblGrade values(gradeSeq.nextval,?,?,?,?,?)";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, note);
			stat.setInt(2, skill);
			stat.setInt(3, attendance);
			stat.setInt(4, lecSubSeq);
			stat.setInt(5, courseSeq);
			return stat.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.addGrade()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.addGrade()");
		}
		return 0;
	}
	
	/**
	 * 과정코드,학생코드를 받아 해당 학생(과정)의 성적정보 객체를 반환하는 메소드
	 * @param selectedLectureSeq 선택된 과정번호
	 * @param selectedStdSeq 선택된 학생번호
	 * @return
	 */
	public ArrayList<GradeDTO> getGradeDTO(String selectedLectureSeq, String selectedStdSeq) {
		String sql = "select s.stdName as 학생명,g.gradenotescore as 필기, g.gradeskillscore as 실기, g.gradeattendancescore as 출석 "
				+ "    from tblCourse c inner join tblGrade g on g.courseseq = c.courseseq "
				+ "        inner join tblStudent s on s.stdSeq = c.stdSeq where s.stdseq = ? and c.lectureSeq = ?";
		ArrayList<GradeDTO> glist = new ArrayList<GradeDTO>();

		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, selectedStdSeq);
			stat.setString(2, selectedLectureSeq);
			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				GradeDTO dto = new GradeDTO();
				dto.setGradeNoteScore(rs.getString("필기"));
				dto.setGradeSkillScore(rs.getString("실기"));
				dto.setGradeAttendanceScore(rs.getString("출석"));
				dto.setCourseSeq(rs.getString("학생명"));// 학생명...
				glist.add(dto);

			}
			return glist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.getGradeDTO()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.getGradeDTO()");
		}

		return null;
	}
	
	/**
	 * 학생코드를 받아 성적을 입력하는 메소드
	 * @param stdSeq 학생번호
	 * @return 성적객체
	 */
	public ArrayList<GradeDTO> getGradeDTO(String stdSeq) {
		String sql = "select s.stdseq, s.stdName as 학생명,g.gradenotescore as 필기, g.gradeskillscore as 실기, g.gradeattendancescore as 출석 "
				+ "    from tblCourse c inner join tblGrade g on g.courseseq = c.courseseq "
				+ "        inner join tblStudent s on s.stdSeq = c.stdSeq where s.stdseq = ?";
		ArrayList<GradeDTO> glist = new ArrayList<GradeDTO>();
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, stdSeq);
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				GradeDTO dto = new GradeDTO();
				dto.setGradeNoteScore(rs.getString("필기"));
				dto.setGradeSkillScore(rs.getString("실기"));
				dto.setGradeAttendanceScore(rs.getString("출석"));
				dto.setCourseSeq(rs.getString("학생명"));// 학생명...
				glist.add(dto);
			}
			return glist;
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.getGradeDTO()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.getGradeDTO()");
		}
		return null;
	}

	/**
	 * 과정스케줄 정보를 반환해주는 메소드
	 * @return 데이터 배열
	 */
	public ArrayList<Object[]> getLectureSchedule() {
		String sql = "select l.lectureName as 과정명, to_char(l.lecturestartdate,'yyyy-mm-dd') as 시작날짜, "
				+ "to_char(l.lectureenddate,'yyyy-mm-dd') as 종료날짜, l.lecturecurrentstd as 수강인원, c.classname as 강의실"
				+ "    from tblLecture l inner join tblClassroom c on l.classseq = c.classseq where tchseq = ?";

		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, TeacherUtil.loginTchSeq);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("과정명"), rs.getString("시작날짜"), rs.getString("종료날짜"),
						rs.getString("강의실"), rs.getString("수강인원") });
			}
			return olist;
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.getLectureSchedule()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.getLectureSchedule()");
		}
		return null;
	}
	
	/**
	 * 해당 교사의 과목리스트를 반환해주는 메소드
	 * @param tchSeq 교사번호
	 * @return 데이터 배열
	 */
	public ArrayList<Object[]> getSubjectList(String tchSeq) {
		String sql = "select s.subjectname as 과목명, to_char(ls.SubjectStartDate,'yyyy-mm-dd') as 시작날짜, to_char(ls.SubjectEndDate,'yyyy-mm-dd') as 종료날짜,"
				+ "    l.lecturecurrentstd as 수강인원, c.classname as 강의실"
				+ "        from tblLecture l inner join tblClassroom c on l.classseq = c.classseq inner join tblLectureSubject ls on ls.lectureseq = l.lectureseq"
				+ "            inner join tblSubject s on ls.subjectseq = s.subjectseq "
				+ "                where tchseq = ? AND L.LECTUREPROGRESS='강의중'";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, tchSeq);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("과목명"), rs.getString("시작날짜"), rs.getString("종료날짜"),
						rs.getString("강의실"), rs.getString("수강인원") });
			}
			return olist;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.getSubjectList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.getSubjectList()");
		}

		return null;
	}
	
	/**
	 * K는 실기와 필기를 나누는 분기변수이고, 이를 통해 해당 과목의 시험문제 정보를 반환해준다.
	 * @param subjectSeq 과목번호
	 * @param k 필/실기 분기 변수
	 * @return 데이터 배열
	 */
	public ArrayList<Object[]> getQuestionlist(String subjectSeq, int k) {
		String sql;
		ArrayList<Object[]> olist = new ArrayList<Object[]>();
		if (k == 1) {
			// 필기
			sql = "select nt.notedistribution as 배점," + "    s.subjectName as 과목명, nt.notequestion as 문제"
					+ "        from tblNoteTest nt inner join tblsubject s on nt.subjectSeq = s.subjectSeq "
					+ "where nt.subjectSeq =?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, subjectSeq);
				ResultSet rs = stat.executeQuery();
				while (rs.next()) {
					olist.add(new Object[] { rs.getString("배점"), rs.getString("과목명"), rs.getString("문제") });
				}
				return olist;
			}catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.getQuestionlist()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.getQuestionlist()");
			}
		} else if (k == 2) {
			// 실기
			sql = "select sk.skilldistribution as 배점," + "    s.subjectName as 과목명, sk.skillquestion as 문제"
					+ "        from tblSkillTest sk inner join tblsubject s on sk.subjectSeq = s.subjectSeq"
					+ "	where sk.subjectSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, subjectSeq);
				ResultSet rs = stat.executeQuery();
				while (rs.next()) {
					olist.add(new Object[] { rs.getString("배점"), rs.getString("과목명"), rs.getString("문제") });
				}
				return olist;
			}catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.getQuestionlist()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.getQuestionlist()");
			}
		}
		return null;
	}
	
	/**
	 * K는 실기와 필기를 나누는 분기변수이고, 이를 통해 시험문제를 등록하는 메소드
	 * @param question 문제
	 * @param distribution 배점
	 * @param subjectSeq 과목번호
	 * @param k 필기 실기 분기변수
	 * @return 결과
	 */
	public int addExam(String question, String distribution, String subjectSeq, int k) {
		String sql;

		if (k == 1) {
			// 필기
			sql = "insert into tblNoteTest values(NOTEQUESEQ.nextval,?,?,?)";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, question);
				stat.setString(2, distribution);
				stat.setString(3, subjectSeq);
				return stat.executeUpdate();
			} catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.addExam()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.addExam()");
			}
		} else if (k == 2) {
			// 실기
			sql = "insert into tblSkillTest values(skillQueSeq.nextval,?,?,?)";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, question);
				stat.setString(2, distribution);
				stat.setString(3, subjectSeq);
				return stat.executeUpdate();
			} catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.addExam()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.addExam()");
			}
		}
		return 0;
	}
	
	/**
	 * K는 필/실 분기변수이고, 이를 통해 해당 시험정보를 반환해준다.
	 * @param subjectSeq 과목번호
	 * @param k 필기 실기 분기변수
	 * @return 데이터 배열
	 */
	public ArrayList<Object[]> getExamList(String subjectSeq, int k) {
		String sql;
		ArrayList<Object[]> olist = new ArrayList<Object[]>();
		if(k==1) {
			sql = "select * from tblNoteTest where subjectSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, subjectSeq);
				ResultSet rs = stat.executeQuery();
				while(rs.next()) {
					olist.add(new Object[] {
							rs.getString("NOTEQUESEQ"),rs.getString("NOTEDISTRIBUTION"),rs.getString("NOTEQUESTION")
					});
				}
				return olist;
			} catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.getExamList()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.getExamList()");
			}
			
			
		}else if(k==2) {
			sql = "select * from tblSkillTest where subjectSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, subjectSeq);
				ResultSet rs = stat.executeQuery();
				while(rs.next()) {
					olist.add(new Object[] {
							rs.getString("SKILLQUESEQ"),rs.getString("SKILLDISTRIBUTION"),rs.getString("SKILLQUESTION")
					});
				}
				return olist;
			}catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.getExamList()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.getExamList()");
			}
		}
		return null;
	}
	
	/**
	 * 분기변수 k , 시험정보를 삭제하는 메소드
	 * @param examSeq 시험 번호
	 * @param k 필기/실기 변수
	 * @return 결과
	 */
	public int removeExam(String examSeq, int k) {
		String sql;
		if(k==1) {
			sql = "delete from tblNoteTest where noteQueSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, examSeq);
				return stat.executeUpdate();
			} catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.removeExam()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.removeExam()");
			}
		}else if(k==2) {
			sql = "delete from tblSkillTest where skillQueSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, examSeq);
				return stat.executeUpdate();
			}  catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.removeExam()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.removeExam()");
			}
		}
		
		
		
		
		return 0;
	}
	
	/**
	 * 분기변수 k, 시험문제를 수정하는 메소드
	 * @param examSeq 시험문제번호
	 * @param examContent 시험문제
	 * @param k 필기/실기 변수
	 * @return 결과
	 */
	public int updateExam(String examSeq, String examContent, int k) {
		String sql;
		if(k==1) {
			sql = "update tblNoteTest set noteQuestion = ? where noteQueSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, examContent);
				stat.setString(2, examSeq);
				return stat.executeUpdate();
			}catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.updateExam()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.updateExam()");
			}
		}else if(k==2) {
			sql = "update tblSkillTest set skillQuestion = ? where skillQueSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, examContent);
				stat.setString(2, examSeq);
				return stat.executeUpdate();
			} catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.updateExam()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.updateExam()");
			}
		}
		return 0;
	}
	
	/**
	 * 해당 교사의 과목명과 과목번호를 반환해주는 메소드
	 * @param tchSeq 교사번호
	 * @return 데이터 배열
	 */
	public ArrayList<SubjectDTO> getSubjectAndSeqList(String tchSeq) {
		String sql = "select distinct s.subjectName as 과목명, s.subjectSeq as 과목번호 from tblSubject s inner join tblLectureSubject ls on s.subjectSeq = ls.subjectSeq " + 
				"    inner join tblLecture l on l.lectureSeq = ls.lectureSeq" + 
				"        where tchSeq = ? order by s.subjectSeq desc ";
		ArrayList<SubjectDTO> slist = new ArrayList<SubjectDTO>();
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, tchSeq);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				SubjectDTO dto = new SubjectDTO();
				dto.setSubjectSeq(rs.getString("과목번호"));
				dto.setSubjectName(rs.getString("과목명"));
				slist.add(dto);
			}
			return slist;
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.getSubjectAndSeqList()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.getSubjectAndSeqList()");
		}
		return null;
	}
	
	/**
	 * 임광민씨 이런거 검증안하세요?
	 * @param tchSeq 교사번호
	 * @param lectureSeq 과정 번호
	 * @return 학생 객체배열
	 */
	public ArrayList<StudentDTO> getStudentDTO(String tchSeq, String lectureSeq) {
		String sql = "select s.stdName as 학생명, s.stdSeq 학생번호 from tblStudent s inner join tblCourse c on c.stdSeq = s.stdSeq " + 
				"    inner join tblLecture l on l.lectureSeq = c.lectureSeq where l.tchSeq = ? and l.lectureSeq = ?";
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, tchSeq);
			stat.setString(2, lectureSeq);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setSTDName(rs.getString("학생명"));
				dto.setSTDSeq(rs.getString("학생번호"));
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return null;
	}
	
	/**
	 * 해당 교사가 가르치는 학생명과 학생번호를 반환해주는 메소드
	 * @param tchSeq 교사번호
	 * @return 학생객체 배열
	 */
	public ArrayList<StudentDTO> getStudentDTO(String tchSeq) {
		String sql = "select s.stdName as 학생명, s.stdSeq 학생번호 from tblStudent s inner join tblCourse c on c.stdSeq = s.stdSeq " + 
				"    inner join tblLecture l on l.lectureSeq = c.lectureSeq where l.tchSeq = ? and l.LECTUREPROGRESS = '강의중'";
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, tchSeq);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setSTDName(rs.getString("학생명"));
				dto.setSTDSeq(rs.getString("학생번호"));
				list.add(dto);
			}
			return list;
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.getStudentDTO()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.getStudentDTO()");
		}
		return null;
	}
	
	/**
	 * 해당 교사객체의 아이디와 비밀번호를 반환해주는 메소드
	 * @param tDTO 교사객체
	 * @return 교사객체
	 */
	public TeacherDTO search(TeacherDTO tDTO) {
		String sql = null;
		
		try {
			if(tDTO.getCate() == 1) {
				sql = "select TCHSeq from tblTeacher where TCHName = ? and TCHTel = ?";
				PreparedStatement stat = conn.prepareStatement(sql);
				
				stat.setString(1, tDTO.getTCHName());
				stat.setString(2, tDTO.getTCHTel());
				
				ResultSet rs = stat.executeQuery();
		
				if(rs.next()) {
					tDTO.setTCHSeq(rs.getString("TCHSeq"));
				}
				
				return tDTO;
			} else {
				sql = "select TCHSsn,TCHTel from tblTeacher where TCHName = ? and TCHSeq = ?";
				PreparedStatement stat = conn.prepareStatement(sql);

				stat.setString(1, tDTO.getTCHName());
				stat.setString(2, tDTO.getTCHSeq());

				ResultSet rs = stat.executeQuery();

				if(rs.next()) {					
					tDTO.setTCHSsn(rs.getString("TCHSsn"));
					tDTO.setTCHTel(rs.getString("TCHTel"));
				}
				
				return tDTO;
			}	
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","TeacherDAO.search()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","TeacherDAO.search()");
		}
		return null;
	}
	
	/**
	 * 강의중인 과정의 목록을 반환
	 * @param seq 과정번호
	 * @return 과정객체 리스트
	 */
	public ArrayList<LectureDTO> lecturelist(String seq) {         //과정 목록(강의중인 과정 범위)
	      
	      ArrayList<LectureDTO> list = new ArrayList<LectureDTO>();
	      
	      try {
	         
	         String sql = "select lectureseq, " + 
	                        "           lecturename, " + 
	                        "         to_char(lecturestartdate, 'yyyy-mm-dd') ||' ~ '|| to_char(lectureenddate, 'yyyy-mm-dd') as period " + 
	                        "            from tblLecture where tchSeq = ?";
	         
	         PreparedStatement stat = conn.prepareStatement(sql);
	         
	         stat.setString(1, seq);
	         
	         ResultSet rs = stat.executeQuery();
	         
	         while(rs.next()) {
	            //레코드 1줄 > DTO 1개 > list
	            LectureDTO dto = new LectureDTO();
	            dto.setLectureSeq(rs.getString("lectureseq"));
	            dto.setLectuerName(rs.getString("lecturename"));
	            dto.setPeriod(rs.getString("period"));
	            list.add(dto);
	         }
	         
	         return list;
	         
	      }catch (SQLSyntaxErrorException e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("오라클에러","TeacherDAO.search()");
			} catch (Exception e) {
				out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
				dao.systemError("알수없는에러","TeacherDAO.search()");
			}
	      
	      return null;
	   }
	/**
	 * 교사 로그인 기록
	 */
	public void LoginLog() {
		try {
			String sql = "insert into tblTeacherLog "+ 
					"values (tchlogseq.nextval, ?, default, null)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1,TeacherUtil.loginTeacher.getTCHName());
			
			stat.executeUpdate();
		} catch (Exception e) {
			out.result("에러가 발생했습니다.");
		}
	}
	/**
	 * 교사 로그아웃 기록
	 */
	public void LogoutLog() { 
		try {
			String sql = "update tblTeacherLog set logout = sysdate " + 
					"where tchlogseq = (select max(tchlogseq) from tblteacherlog where code = ? and logout is null)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1,TeacherUtil.loginTeacher.getTCHName());
			
			stat.executeUpdate();
		} catch (Exception e) {
			out.result("에러가 발생했습니다.");
		}
	}
}










