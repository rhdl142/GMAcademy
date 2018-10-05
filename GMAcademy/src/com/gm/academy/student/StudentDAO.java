package com.gm.academy.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.Util.DBUtil;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.admin.AdminDAO;

public class StudentDAO {
	private Connection conn;
	private PreparedStatement stat;
	private UtilPrint out;
	private AdminDAO dao;
	
	public StudentDAO() {
		dao = new AdminDAO();
		out = new UtilPrint();
		this.conn = DBUtil.getConnection("211.63.89.42", "Project", "JAVA1234");
	}

	/**
	 * 교육생 > 로그인 인증
	 * 
	 * @param slDTO 학생 과정에 대한 정보
	 * @return select > 학생 로그인 인증 정보
	 */
	public StduentLectureDTO auth(StudentLogInDTO slDTO) {
		StduentLectureDTO sltDTO = new StduentLectureDTO();
		
		try {
			String sql = "select " + 
					"    s.stdname as studentName," + 
					"    s.stdtel as studentTel," +
					"    s.stdseq as studentseq," + 
					"    l.lecturename as lectureName," + 
					"    l.lectureStartDate as lectureStartDate," + 
					"    l.lectureEndDate as lectureEndDate, " +
					"    l.lectureSeq as lectureSeq, " + 
					"    t.tchseq as tchseq, " + 
					"    c.courseSeq as courseSeq " + 
					"from tblStudentLogin sl" + 
					"    inner join tblStudent s " + 
					"        on sl.STDpw = s.STDssn " + 
					"            inner join tblCourse c " + 
					"                on s.STDseq = c.STDseq " + 
					"                    inner join tblLecture l " + 
					"                        on c.lectureSeq = l.lectureSeq " +
					"                        	inner join tblTeacher t " +
					"                       		 on t.TCHSeq = l.TCHSeq " +
					"                            where STDid = ? and STDpw = ?";
			stat = conn.prepareStatement(sql);

			stat.setString(1, slDTO.getSTDID());
			stat.setString(2, slDTO.getSTDPW());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				sltDTO.setStudentSeq(rs.getString("studentseq"));
				sltDTO.setStudentName(rs.getString("studentName"));
				sltDTO.setStudentTel(rs.getString("studentTel"));
				sltDTO.setLectureName(rs.getString("lectureName"));
				sltDTO.setLectureStartDate(rs.getString("lectureStartDate"));
				sltDTO.setLectureEndDate(rs.getString("lectureEndDate"));
				sltDTO.setLectureSeq(rs.getString("lectureSeq"));
				sltDTO.setTchSeq(rs.getString("tchseq"));
				sltDTO.setCourseSeq(rs.getString("courseseq"));
				
				return sltDTO;
			}
			
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.auth()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","StudentDAO.auth()");
		}
		return null;
	}

	/**
	 * 교육생 > 성적조회
	 * 
	 * @return select > 학생 성적 정보
	 */
	public ArrayList<StudentGradeDTO> grade() {
		ArrayList<StudentGradeDTO> list = new ArrayList<>();
		
		try {
			String sql = "select DISTINCT s.subjectName, g.gradeNoteScore, g.gradeSkillScore, g.gradeAttendanceScore, ls.lecSubseq " +
					"    from tblStudent st " + 
					"    inner join tblCourse c" + 
					"        on st.StdSeq = c.STDSeq" + 
					"            inner join tblGrade g" + 
					"                on c.courseSeq = g.courseSeq" + 
					"                    inner join tblLectureSubject ls" + 
					"                        on ls.lecSubSeq = g.lecSubSeq" + 
					"                            inner join tblSubject s" + 
					"                                on ls.subjectSeq = s.subjectSeq" + 
					"                                    where st.stdseq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, MainClass.isAuth);
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				StudentGradeDTO sgDTO = new StudentGradeDTO();
				sgDTO.setSubjectName(rs.getString("subjectName"));
				sgDTO.setGradeNoteScore(rs.getString("gradeNoteScore"));
				sgDTO.setGradeSkillScore(rs.getString("gradeSkillScore"));
				sgDTO.setGradeAttendanceScore(rs.getString("gradeAttendanceScore"));
				sgDTO.setLecSubseq(rs.getString("lecSubseq"));
				
				list.add(sgDTO);
			}
			
			return list;
		}  catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.grade()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","StudentDAO.grade()");
		}
		return null;
	}

	/**
	 * 교육생 > 출결 > 입실등록, 퇴실등록
	 * 
	 * @param today 현재 날짜,시간정보
	 * @param cate 입실 퇴실 구분
	 * @return update > tblAttendance 입실 퇴실
	 */
	public int inOutRegister(String today, int cate) {
		try {
			String addsql = "update tblAttendance set ontime ";
			
			if(cate == 1) {
				addsql = "update tblAttendance set offtime ";
			}
			
			String sql = String.format("%s = to_date(?,'yyyy/mm/dd hh24:mi:ss')  " + 
					"    where courseSeq = (select c.courseSeq from tblStudent s  " + 
					"    inner join tblCourse c  " + 
					"        on s.STDSeq = c.STDSeq  " + 
					"            inner join tblLecture l  " + 
					"                on c.lectureSeq = l.lectureSeq  " + 
					"                    where s.stdseq = ? and lectureProgress = '강의중')  " +
					"                           and absenceSeq is null  ",addsql);
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, today);
			stat.setString(2, MainClass.isAuth);
			
			return stat.executeUpdate();
		}catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.inOutRegister()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","StudentDAO.inOutRegister()");
		}
		return 0;
	}

	/**
	 * 교육생 > 교사 평가 > 교사 평가 등록 > 과정평가
	 * 
	 * @param score 과정 평가 점수
	 * @param command 과정 커맨드
	 * @return insert > tblLectureEvaluation
	 */
	public int lectureEvaluation(String score, String command) {
		try {
			String sql = "insert into tblLectureEvaluation values(evalLecSeq.nextval,?,?,?,?,?)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, MainClass.tchSeq);
			stat.setString(2, score);
			stat.setString(3, command);
			stat.setString(4, MainClass.lectureSeq);
			stat.setString(5, MainClass.courseSeq);
			
			return stat.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.lectureEvaluation()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","StudentDAO.lectureEvaluation()");
		}
		return 0;
	}

	/**
	 * 교육생 > 교사 평가 > 교사 평가 등록 > 과목평가
	 * 
	 * @param name 과목 코드
	 * @param score 과목 평가 점수
	 * @param command 과목 커맨드
	 * @return insert > tblSubjectEvaluation
	 */
	public int subjectEvaluation(String name, String score, String command) {
		try {
			String sql = "insert into tblSubjectEvaluation values(evalsubSeq.nextval,?,?,?,?,?)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, MainClass.tchSeq);
			stat.setString(2, score);
			stat.setString(3, command);
			stat.setString(4, name);
			stat.setString(5, MainClass.courseSeq);
			
			return stat.executeUpdate();
		}   catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.subjectEvaluation()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","StudentDAO.subjectEvaluation()");
		}
		return 0;
	}

	/**
	 * 교육생 > 상담일지 > 상담일지 조회
	 * 
	 * @return select > 상담 등록일,내용
	 */
	public ArrayList<CourseRecordDTO> consulting() {
		ArrayList<CourseRecordDTO> list = new ArrayList<>();
		
		try {
			String sql = "select substr(cr.counseRegdate,1,10) as counseRegdate, cr.counseContents from tblStudent s " + 
					"    inner join tblCourse c " + 
					"        on s.stdseq = c.stdseq " + 
					"            inner join tblCourseRecord cr " + 
					"                on c.courseSeq = cr.courseSeq " + 
					"                    where s.stdseq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, MainClass.isAuth);
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				CourseRecordDTO crDTO = new CourseRecordDTO();
				crDTO.setCounseRegdate(rs.getString("counseRegdate"));
				crDTO.setCounseContents(rs.getString("counseContents"));
				
				list.add(crDTO);
			}
			
			return list;
		}  catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.consulting()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","StudentDAO.consulting()");
		}
		return null;
	}

	/**
	 * 교육생 > 상담일지 > 상담일지 > 출결 현황
	 * 
	 * @return select > 출결 정보(정상,지각,기타...)
	 */
	public ArrayList<AbsenceRecordTypeDTO> attendanceStatus() {
		try {
			ArrayList<AbsenceRecordTypeDTO> list = new ArrayList<>();
			
			String sql = "select " + 
					"    (select count(*) from tblStudent s " + 
					"        inner join tblCourse c " + 
					"            on s.stdseq = c.stdseq " + 
					"                inner join tblAttendance at " + 
					"                    on c.courseSeq = at.courseSeq " + 
					"                        inner join tblAbsenceRecord ar " + 
					"                            on at.absenceSeq = ar.absenceSeq " + 
					"                                where s.stdSeq = ? and ar.absenceSeq = 1) as a, " + 
					"    (select count(*) from tblStudent s " + 
					"        inner join tblCourse c " + 
					"            on s.stdseq = c.stdseq " + 
					"                inner join tblAttendance at " + 
					"                    on c.courseSeq = at.courseSeq " + 
					"                        inner join tblAbsenceRecord ar  " + 
					"                            on at.absenceSeq = ar.absenceSeq " + 
					"                                where s.stdSeq = ? and ar.absenceSeq = 2) as b, " + 
					"    (select count(*) from tblStudent s " + 
					"     inner join tblCourse c " + 
					"           on s.stdseq = c.stdseq " + 
					"                inner join tblAttendance at " + 
					"                    on c.courseSeq = at.courseSeq " + 
					"                        inner join tblAbsenceRecord ar " + 
					"                            on at.absenceSeq = ar.absenceSeq " + 
					"                                where s.stdSeq = ? and ar.absenceSeq = 3) as c, " + 
					"    (select count(*) from tblStudent s " + 
					"        inner join tblCourse c " + 
					"            on s.stdseq = c.stdseq " + 
					"                inner join tblAttendance at " + 
					"                    on c.courseSeq = at.courseSeq " + 
					"                        inner join tblAbsenceRecord ar " + 
					"                            on at.absenceSeq = ar.absenceSeq " + 
					"                                where s.stdSeq = ? and ar.absenceSeq = 4) as d, " + 
					"    (select count(*) from tblStudent s " + 
					"        inner join tblCourse c " + 
					"            on s.stdseq = c.stdseq " + 
					"                inner join tblAttendance at " + 
					"                    on c.courseSeq = at.courseSeq " + 
					"                        inner join tblAbsenceRecord ar " + 
					"                            on at.absenceSeq = ar.absenceSeq " + 
					"                                where s.stdSeq = ? and ar.absenceSeq = 5) as e, " + 
					"    (select count(*) from tblStudent s " + 
					"        inner join tblCourse c " + 
					"            on s.stdseq = c.stdseq " + 
					"                inner join tblAttendance at " + 
					"                    on c.courseSeq = at.courseSeq " + 
					"                        inner join tblAbsenceRecord ar " + 
					"                            on at.absenceSeq = ar.absenceSeq " + 
					"                                where s.stdSeq = ? and ar.absenceSeq = 6) as f " + 
					"from dual ";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, MainClass.isAuth);
			stat.setString(2, MainClass.isAuth);
			stat.setString(3, MainClass.isAuth);
			stat.setString(4, MainClass.isAuth);
			stat.setString(5, MainClass.isAuth);
			stat.setString(6, MainClass.isAuth);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				AbsenceRecordTypeDTO artDTO = new AbsenceRecordTypeDTO();
				
				artDTO.setTypeA(rs.getString("a"));
				artDTO.setTypeB(rs.getString("b"));
				artDTO.setTypeC(rs.getString("c"));
				artDTO.setTypeD(rs.getString("d"));
				artDTO.setTypeE(rs.getString("e"));
				artDTO.setTypeF(rs.getString("f"));
				
				list.add(artDTO);
			}
			
			return list;
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.attendanceStatus()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","StudentDAO.attendanceStatus()");
		}
		return null;
	}

	/**
	 * 교육생 > ID/PW찾기
	 * 
	 * @param sDTO 교육생 코드
	 * @return select > tblStudent
	 */
	public StudentDTO search(StudentDTO sDTO) {
		String sql = null;
		
		try {
			if(sDTO.getCate() == 1) {
				sql = "select STDSeq from tblStudent where STDName = ? and STDTel = ?";
				PreparedStatement stat = conn.prepareStatement(sql);
				
				stat.setString(1, sDTO.getSTDName());
				stat.setString(2, sDTO.getSTDTel());
				
				ResultSet rs = stat.executeQuery();
		
				if(rs.next()) {
					sDTO.setSTDSeq(rs.getString("STDSeq"));
				}
				
				return sDTO;
			} else {
				sql = "select STDSsn, STDTel from tblStudent where STDName = ? and STDSeq = ?";
				PreparedStatement stat = conn.prepareStatement(sql);

				stat.setString(1, sDTO.getSTDName());
				stat.setString(2, sDTO.getSTDSeq());

				ResultSet rs = stat.executeQuery();

				if(rs.next()) {					
					sDTO.setSTDSsn(rs.getString("STDSsn"));
					sDTO.setSTDTel(rs.getString("STDTel"));
				}
				
				return sDTO;
			}	
		} catch (SQLSyntaxErrorException e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.search()");
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("알수없는에러","StudentDAO.search()");
		}
		return null;
	}
	/**
	 * 학생 로그인 기록
	 */
	public void LoginLog() {
		try {
			String sql = "insert into tblstudentlog "+ 
					"values (stdlogseq.nextval, ?, default, null)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1,MainClass.name);
			
			stat.executeUpdate();
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.LoginLog()");
		}
	}
	/**
	 * 학생 로그아웃 기록
	 */
	public void LogoutLog() {
		try {
			String sql = "update tblstudentlog set logout = sysdate " + 
					"where stdlogseq = (select max(stdlogseq) from tblstudentlog where code = ? and logout is null)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1,MainClass.name);
			
			stat.executeUpdate();
		} catch (Exception e) {
			out.result("문제가 발생하였습니다. 관리자에게 문의해주세요");
			dao.systemError("오라클에러","StudentDAO.LoginLog()");
		}
	}
}














