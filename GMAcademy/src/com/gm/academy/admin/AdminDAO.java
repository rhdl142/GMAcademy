package com.gm.academy.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gm.academy.Util.DBUtil;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.exam.GradeDTO;
import com.gm.academy.exam.NoteTestDTO;
import com.gm.academy.exam.SkillTestDTO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.PublisherDTO;
import com.gm.academy.lecture.SubjectDTO;
import com.gm.academy.lecture.TextBookDTO;
import com.gm.academy.student.CourseRecordDTO;
import com.gm.academy.student.StudentDTO;
import com.gm.academy.student.StudentManageDTO;
import com.gm.academy.teacher.TeacherDTO;

import oracle.jdbc.internal.OracleTypes;

public class AdminDAO {
	private Connection conn;
	private PreparedStatement stat;
	private UtilPrint out;
	
	public AdminDAO() {
		this.conn = DBUtil.getConnection("localhost","Project","java1234");
		this.out = new UtilPrint();
	}

//로그인---------------------------------------------------------------------------------------------------------------------------	
	//로그인확인
	public String auth(AdminLogInDTO alDTO) {
		try {
			String sql = "select count(*) as cnt from tblAdminLogIn where adminid = ? and adminpw = ?";
			
			stat = conn.prepareStatement(sql);
			
			stat.setString(1, alDTO.getAdminID());
			stat.setString(2, alDTO.getAdminPW());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getString("cnt");
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.auth :" + e.toString());
		}
	
		return null;
	}
//---------------------------------------------------------------------------------------------------------------------------
//교사계정관리----------------------------------------------------------------------------------------------------------
	
	public ArrayList<TeacherDTO> list(boolean isAuth) { // 교사 조회

		ArrayList<TeacherDTO> list = new ArrayList<TeacherDTO>();

		try {

			String sql = "select * from tblTeacher";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				TeacherDTO dto = new TeacherDTO();

				dto.setTCHSeq(rs.getString("tchseq"));
				dto.setTCHName(rs.getString("tchname"));
				dto.setTCHSsn(rs.getString("tchssn"));
				dto.setTCHTel(rs.getString("tchtel"));

				list.add(dto);

			}

			return list;

		} catch (Exception e) {
			System.out.println("AdminDAO.list()" + e.toString());
		}

		return null;
	}

	public int Register(String tchname, String tchssn, String tchtel) { //교사 등록 
		
			
	try {
			
				String sql = "insert into tblTeacher (tchSeq, tchName, tchSsn, tchTel)"
								+" values ('TC' || tchseq.nextval, ?, ?, ?)";
				
				stat = conn.prepareStatement(sql);
				
				stat.setString(1, tchname);
				stat.setString(2, tchssn);
				stat.setString(3, tchtel);
				
				return stat.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("AdminDAO.TeacherRegister()" + e.toString());
			}
			
			return 0;
	}

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
							+ "(select tchseq from tblTeacherLogin where tchseq = ?) " + "and tchname = ?";

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

		} catch (Exception e) {
			System.out.println("AdminDAO.TeacherRemove()" + e.toString());
		}

		return 0;
	}

	public int updateName(String tchNameBefor, String tchNameAfter) { // 교사 수정 이름

		try {
			String sql = "update tblTeacher set tchname = ? where tchname = ?";

			stat = conn.prepareStatement(sql);

			stat.setString(1, tchNameAfter);
			stat.setString(2, tchNameBefor);
			
			return stat.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return 0;
	}

	public int updateTel(String tchTelBefor, String tchTelAfter) { //교사 수정 전화번호
		try {
			String sql = "update tblTeacher set tchtel = ? where tchtel = ?";

			stat = conn.prepareStatement(sql);

			stat.setString(1, tchTelAfter);
			stat.setString(2, tchTelBefor);

			return stat.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return 0;
	}

	public int checkSsn(String tchssn) {
		
		try {
			

			String sql = "select count(*) as cnt from tblTeacher where tchssn = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, tchssn);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				int disSsn = rs.getInt("cnt");
				
				return disSsn;
				
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.check()" + e.toString());
		}
		
		return 0;
	}

	public int checkTel(String tchtel) {
		
	try {
		String sql = "select count(*) as cnt from tblTeacher where tchtel = ?";
		
		PreparedStatement stat = conn.prepareStatement(sql);
		
		stat.setString(1, tchtel);
		
		ResultSet rs = stat.executeQuery();
		
		if(rs.next()) {
			int disTel = rs.getInt("cnt");
			
			return disTel;
			
		}
		
	} catch (Exception e) {
		System.out.println("AdminDAO.check()" + e.toString());
	}
	
		return 0;
	}
	
//-----------------------------------------------------------------------------------------------------------------------
//개설과정관리------------------------------------------------------------------------------------------------------------
	public ArrayList<LectureDTO> LectureList() {
		
		/**
		 * @예지
		 * 강의 목록 출력 메소드
		 */
		ArrayList<LectureDTO> lecList = new ArrayList<LectureDTO>();
		
		try {
			
			String sql = "select lectureSeq as 과정코드, " + 
					"        lectureName as 과정명, " + 
					"        to_char(lectureStartDate,'yyyy-mm-dd') as 과정시작일, " + 
					"        to_char(lectureEndDate,'yyyy-mm-dd') as 과정종료일, " + 
					"        lectureProgress as 강의진행여부 " + 
					"        from tblLecture";

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				
				LectureDTO lecDTO = new LectureDTO();
				
				lecDTO.setLectureSeq(rs.getString("과정코드"));
				lecDTO.setLectuerName(rs.getString("과정명"));
				lecDTO.setLectureStartDate(rs.getString("과정시작일"));
				lecDTO.setLectureEndDate(rs.getString("과정종료일"));
				lecDTO.setLectureProgress(rs.getString("강의진행여부"));
				
				lecList.add(lecDTO);
			}
			
			return lecList;
		} catch (Exception e) {
			System.out.println("AdminDAO.LectureList() :" + e.toString());
		}
		
		return null;
	}

	
	
	public ArrayList<LectureDTO> LectureDetail(String lecSeq) {

		/**
		 * @예지
		 * 과정 seq를 입력하면, 세부 사항 출력
		 */
		
		ArrayList<LectureDTO> lecList = new ArrayList<LectureDTO>();
		
		try {
			//System.out.printf("lecSeq = %s\n",lecSeq);
			String sql = "select l.lectureSeq as lectureSeq, "
					+ "l.lectureProgress as lectureProgress, t.tchName as tchname, "
					+ " l.lectureCurrentSTD as lectureCurrentSTD, l.classSEQ as classSeq, l.lectureName as lectureName from tblLecture L " + 
					"    inner join tblTeacher T " + 
					"        on T.TCHseq = l.tchseq " + 
					"         where lectureSeq = ? ";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, lecSeq);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				
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
		} catch (Exception e) {
			System.out.println("AdminDAO.LectureDetail() :" + e.toString());
		}
		return null;
	}

	////미완성 : 강의등록메소드 -> invalid number error 발생
	public int lectureRegister(LectureDTO lecDTO) {

		/**
		 * @예지
		 * 강의 등록 메소드
		 */
		
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
			

		} catch (Exception e) {
			System.out.println("AdminDAO.lectureRegister() :" + e.toString());
		}
		
		return 0;
	}

	public int LectureRemove(LectureDTO lecDTO) {
		
		/**
		 * @예지
		 * 과정 삭제
		 */

		try {
			
			String sql = "delete from tblLecture where lectureSeq = ? ";
					
			PreparedStatement stat = conn.prepareStatement(sql);			
			
			stat.setString(1, lecDTO.getLectureSeq());
			
		return stat.executeUpdate();
		
		} catch (Exception e) {
			System.out.println("AdminDAO.LectureRemove() :" + e.toString());
		}
		
		return 0;
	}

	public int updateLectureName(LectureDTO lecDTO) {
		
		/**
		 * @예지
		 * 과정 명 수정 
		 */
		
		try {
			
			String sql = "update tblLecture set lecturename = ? where lectureseq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lecDTO.getLectuerName());
			stat.setString(2, lecDTO.getLectureSeq());
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateLectureName() :" + e.toString());
		}
		
		return 0;
	}

	public int updateLectureDate(LectureDTO lecDTO) {

		/**
		 * @예지
		 * 과정 날짜 수정
		 */
		
		try {
			
			String sql = "update tblLecture " + 
					"    set lectureStartDate = to_date(?,'yyyy-mm-dd'),lectureEndDate = to_date(?,'yyyy-mm-dd') " + 
					"        where lectureSeq = ? ";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lecDTO.getLectureStartDate());
			stat.setString(2, lecDTO.getLectureEndDate());
			stat.setString(3, lecDTO.getLectureSeq());
			
			return stat.executeUpdate();	
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateLectureDate() :" + e.toString());
		}
		return 0;
	}

	public int updateLectureProgress(LectureDTO lecDTO) {
		
		/**
		 * @예지
		 * 강의 진행 여부 수정
		 */
		
		try {
			
			String sql = "update tblLecture set lectureProgress  = ? where lectureSeq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lecDTO.getLectureProgress());
			stat.setString(2, lecDTO.getLectureSeq());
			
			return stat.executeUpdate();

		} catch (Exception e) {
			System.out.println("AdminDAO.updateLectureProgress() :" + e.toString());
		}
		return 0;
	}

	public int updateStuedent(LectureDTO lecDTO) {
		
		/**
		 * @예지
		 * 학생 인원 수정
		 */
		
		try {
			
			String sql = "update tblLecture set lectureAcceptSTD = ?, lectureCurrentSTD = ? where lectureSeq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lecDTO.getLectureAcceptSTD());
			stat.setString(2, lecDTO.getLectureCurrentSTD());
			stat.setString(3, lecDTO.getLectureSeq());
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateStuedent() :" + e.toString());
		}
		return 0;
	}

	public int updateClassRoom(LectureDTO lecDTO) {
		
		/**
		 * @예지
		 * 강의실 수정
		 */
		
		try {
			
			String sql = "update tblLecture set classSeq = ? where lectureSeq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lecDTO.getClassSeq());
			stat.setString(2, lecDTO.getLectureSeq());
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateClassRoom() :" + e.toString());
		}
		
		return 0;
	}

	public int updateTeacher(LectureDTO lecDTO) {
		
		/**
		 * @예지
		 * 교사 코드 수정
		 */
		
		try {
			
			String sql = "update tblLecture set TCHseq = (select TCHSeq from tblTeacher where TCHSeq = ?) where lectureseq = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lecDTO.getTCHSeq());
			stat.setString(2, lecDTO.getLectureSeq());
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateTeacher() :" + e.toString());
		}
		return 0;
	}
//------------------------------------------------------------------------------------------------------------------------------------------
//개설과목관리------------------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<SubjectDTO> subjectlist() {   //과목 목록(강의중인 과정에 속한)
		
		ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();
		
		try {
			
			String sql = "select A.subjectseq,  A.subjectname from " + 
									"(select s.subjectseq as subjectseq,  s.subjectname as subjectname from tbllecturesubject ls " + 
									"    inner join tblLecture l " + 
									"        on l.lectureseq = ls.lectureseq " + 
									"            inner join tblSubject s " + 
									"                on s.subjectseq = ls.subjectseq " + 
									"                    where l.lectureprogress = '강의중' " + 
									"                        order by s.subjectname) A " + 
									"                            group by A.subjectseq, A.subjectname " + 
									"								order by A.subjectseq";
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				//레코드 1줄 > DTO 1개 > list
				SubjectDTO dto = new SubjectDTO();
				dto.setSubjectSeq(rs.getString("subjectseq"));		//과목 번호
				dto.setSubjectName(rs.getString("subjectname"));		//과목 명
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.subjectlist() : " + e.toString());
		}
		
		return null;
		
	}

	public int addsubject(SubjectDTO subject) {   //과목 추가
		
		try {
			
			String sql = "insert into tblSubject values('S'||subjectSeq.nextval, ?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, subject.getSubjectName());
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.addsubject() : " + e.toString());
		}
		
		return 0;
	}

	public int deletesubject(SubjectDTO subject) {		//과목 삭제
		
		try {
			
			String sql = "delete from tblSubject where subjectname = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, subject.getSubjectName());
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.deletesubject() : " + e.toString());
		}
		
		return 0;
	}

	public int updatesubject(SubjectDTO subject) {		//과목 수정
		
		try {
			
			String sql = "update tblsubject set subjectname = ? " + 
									"where subjectseq = (select subjectseq from tblSubject where subjectname = ?)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, subject.getUpdatesubjectName());
			stat.setString(2, subject.getSubjectName());
			
			stat.executeUpdate();

			
		} catch (Exception e) {
			System.out.println("AdminDAO.updatesubject() : " + e.toString());
		}
		
		return 0;
	}

	public ArrayList<LectureDTO> lecturelist() {			//과정 목록(강의중인 과정 범위)
		
		ArrayList<LectureDTO> list = new ArrayList<LectureDTO>();
		
		try {
			
			String sql = "select lectureseq, " + 
								"     	   lecturename, " + 
								"         to_char(lecturestartdate, 'yyyy-mm-dd') ||' ~ '|| to_char(lectureenddate, 'yyyy-mm-dd') as period " + 
								"				from tblLecture " + 
								"        			where lectureprogress = '강의중'";
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				//레코드 1줄 > DTO 1개 > list
				LectureDTO dto = new LectureDTO();
				dto.setLectureSeq(rs.getString("lectureseq"));
				dto.setLectuerName(rs.getString("lecturename"));
				dto.setPeriod(rs.getString("period"));
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.lecturelist() : " + e.toString());
		}
		
		return null;
	}

	public ArrayList<SubjectDTO> studentSubjectList(String studentSeq, String studentName) {
		
		ArrayList<SubjectDTO> list = new ArrayList<SubjectDTO>();
		
		try {
			
			String sql = "select sb.subjectseq, sb.subjectname from tblCourse c " + 
					"    inner join tblStudent s " + 
					"        on c.stdseq = s.stdseq " + 
					"            inner join tblLecture l " + 
					"                on c.lectureseq = l.lectureseq " + 
					"                    inner join tblGrade g " + 
					"                        on c.courseseq = g.courseseq " + 
					"                            inner join tbllecturesubject ls " + 
					"                                on ls.lectureseq = l.lectureseq " + 
					"                                    inner join tblSubject sb " + 
					"                                        on ls.subjectseq = sb.subjectseq " + 
					"                                                where s.stdseq = ? and s.stdname = ? " + 
					"                                                     and g.gradenotescore <> 0 " + 
					"                                                        order by sb.subjectseq desc";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, studentSeq);
			stat.setString(2, studentName);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				SubjectDTO dto = new SubjectDTO();
				dto.setSubjectSeq(rs.getString("subjectSeq"));
				dto.setSubjectName(rs.getString("subjectname"));
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.studentsubjectlist() : " + e.toString());
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
//------------------------------------------------------------------------------------------------------------------------------------------
//교육생관리------------------------------------------------------------------------------------------------------------------------------------------

	public LectureDTO get(String seq) {
		
		LectureDTO lecture = new LectureDTO();
		
		try {
			
			String sql = "select lectureseq from tbllecture where lectureseq = 'L' || ?";
			
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, seq);
			
			ResultSet rs = stat.executeQuery(sql);
			
			if(rs.next()) {
				
				lecture.setLectureSeq(rs.getString("lectureseq"));
				
				return lecture;
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.get() : " + e.toString());
		}
		
		
		return null;
	}

	public vwStudentDTO vwfind(String seq, String name) {

		vwStudentDTO result = new vwStudentDTO();
		
		try {
			
			
			String sql = "select LECTURESEQ,LECTURENAME,LECTURESTARTDATE,LECTUREENDDATE,LECTUREPROGRESS,CLASSSEQ,STDSEQ,STDNAME,STDSSN,STDTEL,TCHSEQ,TCHNAME"
					+ " from vwstudent where lectureseq = 'L'|| ? and stdname = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, seq);
			stat.setString(2, name);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				
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
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.vwfind() : " + e.toString());
		}
		
		
		
		return null;
	}

	public int insert(StudentDTO std) {
		
		
		try {
			
			
			String sql = "insert into tblStudent values ('ST' || ?,?,?,?)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, std.getSTDSeq());
			stat.setString(2, std.getSTDName());
			stat.setString(3, std.getSTDSsn());
			stat.setString(4, std.getSTDTel());
			
			
			
			return stat.executeUpdate();
			
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.insert() : " + e.toString());
		}
		
		return 0;
	}

	public int add(StudentDTO std, String seq) {
		
		
		try {
			
			String sql = "insert into tblcourse values(?,'ST' || ?,'L'|| ?)";
			
			stat = conn.prepareStatement(sql);
			
			stat.setString(1, std.getSTDSeq());
			stat.setString(2, std.getSTDSeq());
			stat.setString(3, seq);
			
			System.out.println(std.getSTDSeq());
			System.out.println(seq);
			
			return stat.executeUpdate();

		} catch (Exception e) {
			System.out.println("AdminDAO.add() : " + e.toString());
		}
		
		return 0;
	}

	public int stdCheck(String name) {

		try {
			
			String sql = "select count(*) as cnt from tblStudent where  stdname = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, name);
			
			ResultSet rs =  stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
			
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.stdCheck() : " + e.toString());
		}
		
		return 0;
	}

	public int delete(String name) {

		try {
			
			String sql = "delete from tblstudent where stdSeq = (select stdseq from tblstudent where stdname = ?)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, name);
			
				System.out.println(stat.executeUpdate());
				return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.delete() : " + e.toString());
		}
		
		return 0;
	}

	public ArrayList<LectureDTO> lList(boolean isAuth) {
		
		ArrayList<LectureDTO> list = new ArrayList<LectureDTO>();
		
		try {
			
			String sql = "select substr(lectureseq,2) as seq,lecturename from tbllecture";
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				
				LectureDTO dto = new LectureDTO();
				
				dto.setLectureSeq(rs.getString("seq"));
				dto.setLectuerName(rs.getString("lecturename"));
				
				list.add(dto);
				
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.list() : " + e.toString());
		}
		
		return null;
	}
	
	public ArrayList<LectureDTO> insertList(boolean b) {
		
		
		ArrayList<LectureDTO> list = new ArrayList<LectureDTO>();
		
		try {
			
			String sql = "select substr(l.lectureseq,2) as lectureseq, l.lecturename as lecturename, l.lecturecurrentstd as lecturecurrentstd from tbllecture l "
							+" inner join"
								+" (select lectureseq,count(*) as cnt from vwstudent group by lecturename,lectureseq order by lectureseq asc) s "
									+" on l.lectureseq = s.lectureseq"
										+" where l.lectureprogress = '강의예정' and l.lecturecurrentstd <= s.cnt";
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				
				LectureDTO dto = new LectureDTO();
				
				dto.setLectureSeq(rs.getString("lectureseq"));
				dto.setLectuerName(rs.getString("lecturename"));
				dto.setLectureCurrentSTD(rs.getString("lecturecurrentstd"));
				
				list.add(dto);
				
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.list() : " + e.toString());
		}
		
		return null;
	}
	

	public ArrayList<courseRecoardListDTO> cousnselingList(int seq) {
		
		ArrayList<courseRecoardListDTO> list = new ArrayList<courseRecoardListDTO>();
		
		try {
			
			String sql = "select r.courseseq as courseseq, c.stdname as stdname,to_char(r.counseregdate,'yyyy-mm-dd') as counseregdate,r.counsecontents as counsecontents from tblcourserecord r"
					+	" inner join (select substr(stdseq,3) as courseseq,stdname from vwStudent where lectureseq = 'L' || ?) c" 
					+ " on r.courseseq = c.courseseq order by r.counseseq asc";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setInt(1, seq);
			
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				courseRecoardListDTO dto = new courseRecoardListDTO();
				
				dto.setCourseseq(rs.getString("courseseq"));
				dto.setStdName(rs.getString("stdname"));
				dto.setCounseregdate(rs.getString("counseregdate"));
				dto.setCounsecontents(rs.getString("counsecontents"));
				
				list.add(dto);
				
			}
		
			return list;
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.cousnselingList() : " + e.toString());
		}
		
		return null;
	}
	
	
	public ArrayList<vwStudentDTO> stdList(String seq) {
		
		ArrayList<vwStudentDTO> list = new ArrayList<vwStudentDTO>();
		
		try {
			
			String sql = "select 'ST' || substr(stdseq,3) as stdseq,stdname from vwstudent where lectureseq = 'L' || ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, seq);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				vwStudentDTO dto = new vwStudentDTO();
				
				dto.setStdseq(rs.getString("stdseq"));
				dto.setStdname(rs.getString("stdname"));
			
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.stdList() : " + e.toString());
		}
		
		return null;
	}
	
	
	public ArrayList<CourseRecordDTO> courseRecordStd(String stdseq) {

		ArrayList<CourseRecordDTO> list = new ArrayList<CourseRecordDTO>();
		
		try {
			
			String sql = "select to_char(counseregdate,'yyyy-mm-dd') as counseregdate,counsecontents from tblcourserecord where  courseseq = substr(?,3)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, stdseq);
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				
				CourseRecordDTO dto = new CourseRecordDTO();
				
				dto.setCounseRegdate(rs.getString("counseregdate"));
				dto.setCounseContents(rs.getString("counsecontents"));
				
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdminDAO.courseRecordStd() : " + e.toString());
		}
		
		return null;
	}

	
	
	public int find(String seq, String name) {

		try {
			
			String sql = "select count(*) as cnt from vwstudent where lectureseq = 'L' || ? and stdname = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, seq);
			stat.setString(2, name);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.find() : " + e.toString());
		}
		
		return 0;
	}

	public int stdName(String name) {

		try {
			
			
			String sql = "select count(*) as cnt from tblstudent where stdname = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, name);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.stdName() : " + e.toString());
		}
		
		
		return 0;
	}

	public int nameUpdate(String name,String nameUpdate) {

		
		try {
			
			StudentDTO std = new StudentDTO();
			
			System.out.println(name);
			System.out.println(nameUpdate);
			
			String sql = "update tblstudent set stdname = ? where stdname = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, nameUpdate);
			stat.setString(2, name);
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.nameUpdate() : " + e.toString());
		}
		
		return 0;
	}
	
	
	public int stdSsn(String ssn) {
		
		try {
			
			
			String sql = "select count(*) as cnt from tblstudent where stdssn = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, ssn);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.stdName() : " + e.toString());
		}
		
		return 0;
	}

	public int ssnUpdate(String ssn, String ssnUpdate) {

		try {
			
			StudentDTO std = new StudentDTO();
			
			System.out.println(ssn);
			System.out.println(ssnUpdate);
			
			String sql = "update tblstudent set stdssn = ? where stdssn = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, ssnUpdate);
			stat.setString(2, ssn);
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.nameUpdate() : " + e.toString());
		}	
		
		return 0;
	}

	public int stdTel(String tel) {

		try {
			
			
			String sql = "select count(*) as cnt from tblstudent where stdssn = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, tel);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.stdName() : " + e.toString());
		}
		
		return 0;
	}

	public int telUpdate(String tel, String telUpdate) {

		try {
			
			StudentDTO std = new StudentDTO();
			
			
			String sql = "update tblstudent set stdssn = ? where stdssn = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, telUpdate);
			stat.setString(2, tel);
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.nameUpdate() : " + e.toString());
		}	
		
		return 0;
	}

	public vwStudentDTO vwStd(String input) {

		try {
			
			vwStudentDTO std = new vwStudentDTO();
			
			String sql = "select lecturename,stdseq,stdname from vwstudent where stdseq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1,input);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				
				std.setLecturename(rs.getString("lecturename"));
				std.setStdseq(rs.getString("stdseq"));
				std.setStdname(rs.getString("stdname"));
			}
			return std;
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.vwStd() : " + e.toString());
		}
		
		return null;
	}

	public int insetCourseRecord(CourseRecordDTO courseRecord) {

		try {
			
			String sql = "insert into tblcourserecord values (counseseq.nextval,?,?,substr(?,3))";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, courseRecord.getCounseRegdate());
			stat.setString(2, courseRecord.getCounseContents());
			stat.setString(3, courseRecord.getCourseSeq());
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.insetCourseRecord() : " + e.toString());
		}
		
		return 0;
	}

	public int deleteCourseRecord(String data, String input) {

		try {
			String sql = "delete from tblcourserecord where to_char(counseregdate,'yyyy-mm-dd') = ? and courseseq = substr(?,3)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			System.out.println(data);
			System.out.println(input);
			
			stat.setString(1, data);
			stat.setString(2, input);
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.deleteCourseRecord() : " + e.toString());
		}
		
		return 0;
	}

	public int updateCourseRecord(String input, String data, String contents) {

		try {
			
			String sql = "update tblcourserecord set counsecontents = ? where to_char(counseregdate,'yyyy-mm-dd') = ? and courseseq = substr(?,3)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, contents);
			stat.setString(2, data);
			stat.setString(3, input);
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.updateCourseRecord() : " + e.toString());
		}
		
		return 0;
	}

	public int OversightCnt(String lectureseq, String stdseq) {

		try {
			
			
			String sql = "select count(*) as cnt from tblstudentmanage where courseseq = (select substr(stdseq,3) as courseseq from vwstudent where lectureseq = ? and stdseq = ?)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lectureseq);
			stat.setString(2, stdseq);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				int cnt = rs.getInt("cnt");
				System.out.println(rs.getInt("cnt"));
				if(cnt > 0) {
					return cnt;
				} else {
					return 0;
				}
				
			}
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.OversightCnt() : " + e.toString());
		}
		
		return 0;
	}

	public StudentManageDTO selectStdManage(String lectureseq, String stdseq) {

		try {
			
			String sql = "select manageseq,companyname,courseseq from tblstudentmanage where courseseq = (select substr(stdseq,3) as courseseq from vwstudent where lectureseq = ? and stdseq = ?)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lectureseq);
			stat.setString(2, stdseq);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				
				StudentManageDTO dto = new StudentManageDTO();
				
				dto.setManageSeq(rs.getString("manageseq"));
				dto.setCompanyName(rs.getString("companyname"));
				dto.setCourseSeq(rs.getString("courseseq"));
				
				return dto;
				
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.selectStdManage() : " + e.toString());
		}
		
		return null;
	}

	public vwStudentDTO vwfind2(String input, String seq) {

		vwStudentDTO result = new vwStudentDTO();
		
		try {
			
			
			String sql = "select LECTURESEQ,LECTURENAME,LECTURESTARTDATE,LECTUREENDDATE,LECTUREPROGRESS,CLASSSEQ,STDSEQ,STDNAME,STDSSN,STDTEL,TCHSEQ,TCHNAME"
					+ " from vwstudent where lectureseq = 'L'|| ? and stdseq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, input);
			stat.setString(2, seq);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				
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
			
			
		} catch (Exception e) {
			System.out.println("AdminDAO.vwfind() : " + e.toString());
		}
		
		
		
		return null;
	
	}

	public int insertOversight(StudentManageDTO dto) {

		
		try {
			
			String sql = "insert into tblstudentmanage values (manageseq.nextval,?,?)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, dto.getCompanyName());
			stat.setString(2, dto.getCourseSeq());
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.insertOversight() : " + e.toString());
		}
		
		return 0;
	}

	public int deleteOversight(String stdseq) {

		try {
			
			String sql = "delete from tblstudentmanage where courseseq = substr(?,3)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, stdseq);
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.deleteOversight() : " + e.toString());
		}
		
		return 0;
	}

	public int updateOversight(String stdseq, String company) {
		
		try {
			
			String sql = "update tblstudentmanage set companyname = ? where courseseq = substr(?,3)";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, company);
			stat.setString(2, stdseq);
			
			return stat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("AdminDAO.deleteOversight() : " + e.toString());
		}
		
		return 0;
	}
//---------------------------------------------------------------------------------------------------------------------
//출결관리 및 출결조회---------------------------------------------------------------------------------------------------------------------
	public ArrayList<Object[]> getLecture() {
		String sql ="select lectureseq as 과정번호, lectureName as 과정명, " + 
				"        lectureenddate-lecturestartdate||'일' as 기간 " + 
				"            from tblLecture where lectureprogress = '강의중'";
		ArrayList<Object[]> olist = new ArrayList<Object[]>();
		try {
			stat= conn.prepareStatement(sql);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				olist.add(new Object[] {
					rs.getString("과정번호"),rs.getString("기간"),"\t"+rs.getString("과정명")	
				});
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}		
		return null;
	}
	public ArrayList<Object[]> getAttendance(String sel) {
		String sql="select s.stdSeq as 학생번호,s.stdName as 학생명, s.stdtel as 전화번호, " + 
				"    to_char(ad.ontime,'yyyy-mm-dd') as 날짜,ab.absencesituation as 출결 " + 
				"        from tblattendance ad  " + 
				"            inner join tblCourse c on c.courseseq = ad.courseseq " + 
				"                inner join tblStudent s on s.stdseq = c.stdseq " + 
				"                    inner join tblabsencerecord  ab on ab.absenceSeq = ad.absenceSeq " + 
				"                        where lectureSeq = ?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, sel);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while(rs.next()) {
				olist.add(new Object[] {
						rs.getString("학생번호")
						,rs.getString("학생명")
						,rs.getString("전화번호")
						,rs.getString("날짜"),
						rs.getString("출결")
				});
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		
		return null;
	}
	public ArrayList<Object[]> getAttendanceByDay(String year, String month, String day) {
		String sql = "select s.stdName as 학생명, l.lectureName as 과정명, to_char(ontime,'hh24:mi:ss') as 출석시간, to_char(offtime,'hh24:mi:ss') as 퇴실시간, " + 
				"    ab.absenceSituation as 출결 " + 
				"        from tblattendance ad inner join tblabsencerecord ab on ad.absenceseq = ab.absenceseq " + 
				"            inner join tblCourse c on c.courseSeq = ad.courseseq " + 
				"                inner join tblStudent s on s.stdSeq = c.stdSeq " + 
				"                    inner join tblLecture l on l.lectureSeq = c.lectureSeq " + 
				"                        where to_char(ontime,'yyyy-mm-dd') = ? and to_char(offtime,'yyyy-mm-dd') = ?";
		ArrayList<Object[]> olist = new ArrayList<Object[]>();
		try {
			String date = year+"-"+month+"-"+day;
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, date);
			stat.setString(2, date);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				olist.add(new Object[] {
					rs.getString("학생명"),rs.getString("출석시간")
					,rs.getString("퇴실시간"),rs.getString("출결"),
					rs.getString("과정명")
				});
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		
		
		
		
		return null;
	}
	public ArrayList<StudentDTO> getStudentDTO(String stdName) {
		String sql="select * from tblStudent where stdName = ?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, stdName);
			ResultSet rs = stat.executeQuery();
			ArrayList<StudentDTO> slist = new ArrayList<StudentDTO>();
			while(rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setSTDSeq(rs.getString("stdSeq"));
				dto.setSTDName(rs.getString("stdName"));
				dto.setSTDSsn(rs.getString("stdSsn"));
				dto.setSTDTel(rs.getString("stdTel"));
				slist.add(dto);
			}
			return slist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		return null;
	}
	public ArrayList<Object[]> getAttendanceByStudent(String stdSeq) {
		String sql ="select to_char(ontime,'yyyy-mm-dd') as 날짜 ,  ab.absencesituation as 출결, " + 
				"    to_char(ontime,'hh24:mi:ss') as 출입시간,to_char(offtime,'hh24:mi:ss') as 퇴실시간 " + 
				"        from tblattendance a inner join tblCourse c on c.courseseq = a.courseseq " + 
				"            inner join tblabsencerecord ab on ab.absenceseq = a.absenceseq " + 
				"                inner join tblStudent s on s.stdSeq = c.stdSeq " + 
				"                    where s.stdSeq = ? ";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, stdSeq);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while(rs.next()) {
				olist.add(new Object[] {
					rs.getString("날짜"),rs.getString("출입시간"),rs.getString("퇴실시간"),rs.getString("출결")	
				});
				
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		
		
		return null;
	}
	public int updateAttendance(String stdSeq, String date, String situation) {
		String sql ="update tblAttendance set absenceSeq = ? where TO_CHAR(ONTIME,'YYYY-MM-DD') = ? and courseSeq=" + 
				"    (select courseSeq from tblCourse c inner join tblLecture l on c.lectureSeq = l.lectureSeq " + 
				"        where c.stdSeq = ? and l.lectureProgress = '강의중')";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, situation);
			stat.setString(2, date);
			stat.setString(3, stdSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("AdminDAO.updateAttendance() : " + e.toString());
		}
		
		
		return 0;
	}
	

//교재관리-----------------------------------------------------------------------------------------------------------------------------------
	//과목명,교재명,저자,가격 출력 교재관리 전체조회
	public ArrayList<TextBookManagementDTO> textBookManagement() {
		ArrayList<TextBookManagementDTO> list = new ArrayList<>();
		
		try {
			String sql = "select " + 
					"    s.subjectName, " + 
					"    t.textbookwriter, " + 
					"    textBookName, " + 
					"    t.textbookprice " + 
					" from tblTextBook t " + 
					"    inner join tblSubject s " + 
					"        on t.subjectSeq = s.subjectSeq " + 
					"            order by length(s.subjectName) ";
			
			Statement stat = conn.createStatement();
			
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				TextBookManagementDTO tbmDTO = new TextBookManagementDTO();
				
				tbmDTO.setSubjectName(rs.getString("subjectName"));
				tbmDTO.setTextBookWriter(rs.getString("textbookwriter"));
				tbmDTO.setTextBookName(rs.getString("textBookName"));
				tbmDTO.setTextBookPrice(rs.getString("textbookprice"));
				
				list.add(tbmDTO);
			}
			
			return list;
		} catch (Exception e) {
			System.out.println("AdminDAO.textBookManagement :" + e.toString());
		}
		return null;
	}

	//과목명,교재명,저자,가격 출력 교재관리 과정조회
	public ArrayList<TextBookManagementDTO> textBookLecture(String lec) {
		ArrayList<TextBookManagementDTO> list = new ArrayList<>();
		
		try {
			String sql = "select s.subjectName, tb.textBookname, tb.textBookWriter, tb.textBookprice from tblLectureSubject ls " + 
					"    left outer join tblTextBook tb " + 
					"        on tb.textBookSeq = ls.textBookSeq " + 
					"            inner join tblSubject s " + 
					"                on ls.subjectSeq = s.subjectSeq " + 
					"                    where ls.lectureSeq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, lec);
			
			ResultSet rs = stat.executeQuery();
			
			
			while(rs.next()) {
				TextBookManagementDTO tbmDTO = new TextBookManagementDTO();
				
				tbmDTO.setSubjectName(rs.getString("subjectName"));
				tbmDTO.setTextBookName(rs.getString("textBookname"));
				tbmDTO.setTextBookWriter(rs.getString("textBookWriter"));
				tbmDTO.setTextBookPrice(rs.getString("textBookprice"));
				
				list.add(tbmDTO);
			}
			
			return list;
		} catch (Exception e) {
			System.out.println("AdminDAO.textBookLecture :" + e.toString());
		}
		return null;
	}

	//과정명 과정번호 출력, 교재관리 과정조회
	public ArrayList<LectureDTO> lectureNumber() {
		ArrayList<LectureDTO> list = new ArrayList<>();
		
		try {
			String sql ="select lectureSeq, lectureName from tblLecture";
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				LectureDTO lDTO = new LectureDTO();
				
				lDTO.setLectureSeq(rs.getString("lectureSeq"));
				lDTO.setLectuerName(rs.getString("lectureName"));
				
				list.add(lDTO);
			}
			
			return list;
		} catch (Exception e) {
			System.out.println("AdminDAO.lectureNumber :" + e.toString());
		}
		return null;
	}

	//과목명, 과목코드 출력 교재관리_교재등록
	public ArrayList<SubjectDTO> subject() {
		ArrayList<SubjectDTO> list = new ArrayList<>();
		
		try {
			String sql = "select * from tblSubject";
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				SubjectDTO sDTO = new SubjectDTO();
				
				sDTO.setSubjectSeq(rs.getString("subjectSeq"));
				sDTO.setSubjectName(rs.getString("subjectName"));
				
				list.add(sDTO);
			}
			
			return list;
		} catch (Exception e) {
			System.out.println("AdminDAO.subject :" + e.toString());
		}
		return null;
	}

	//출판사명, 출판사코드 출력 교재관리_교재등록
	public ArrayList<PublisherDTO> publisher() {
		ArrayList<PublisherDTO> list = new ArrayList<>();
		
		try {
			String sql = "select * from tblPublisher";
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				PublisherDTO pDTO = new PublisherDTO();
				
				pDTO.setPublisherName(rs.getString("publisherName"));
				pDTO.setPublisherSeq(rs.getString("publisherSeq"));
				
				list.add(pDTO);
			}
			
			return list;
		} catch (Exception e) {
			System.out.println("AdminDAO.publisher :" + e.toString());
		}
		return null;
	}

	//교재관리_교재신청하기
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
		} catch (Exception e) {
			System.out.println("AdminDAO.textBookApplicationWrite :" + e.toString());
		}
		return 0;
	}
//-----------------------------------------------------------------------------------------------------------------------------------
//학원현황-----------------------------------------------------------------------------------------------------------------------------------
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
				olist.add(new Object[] { rs.getString("시작일"), rs.getString("종료일"), rs.getString("교사명"),
						rs.getString("과정명") });
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}

		return null;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}

		return null;
	}

	public int addRecommendCompany(String name, String location, String payment) {
		String sql = "insert into tblRecommendCompany values" + "(RecCompanySeq.nextval,?,?,?)";

		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setString(2, location);
			stat.setString(3, payment);
			return stat.executeUpdate();
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		return 0;
	}

	public int removeRecommendCompany(int seq) {
		String sql = "delete from tblRecommendCompany where RecCompanySeq =?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setInt(1, seq);
			return stat.executeUpdate();
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		return 0;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}

		return 0;
	}

	public ArrayList<Object[]> getLectureAndTeacher() {
		String sql = "select l.lectureseq as 번호, t.tchName as 교사명, l.lectureName as 과정명 "
				+ "    from tblLecture l inner join tblTeacher t on t.tchseq = l.tchseq ";
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while (rs.next()) {
				olist.add(new Object[] { rs.getString("번호"), rs.getString("교사명"), rs.getString("과정명") });
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		return null;
	}

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
				while(rs.next()) {
					olist.add(new Object[] {
							rs.getString("교사명"),rs.getString("과정명")+"\t",rs.getString("현황"),rs.getString("과목명")
					});
				}
				return olist;
			} else {
				while (rs.next()) {
					olist.add(new Object[] { rs.getString("과정명") + "\t", rs.getString("시작일"), rs.getString("종료일"),
							rs.getString("과목명")

					});
				}
				return olist;
			}

		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}

		return null;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}

		return null;
	}

	public ArrayList<Object[]> getAllLecture() {
		String sql = "select l.lectureSeq as 과정변호,to_char(l.lecturestartdate,'yyyy-mm-dd') as 시작일, to_char(l.lectureenddate,'yyyy-mm-dd') as 종료일 " + 
				"    ,t.tchName as 교사명, l.lectureName as 과정명 " + 
				"        from tblLecture l inner join tblTeacher t on t.tchSeq = l.tchSeq";
		try {
			Statement state = conn.createStatement();			
			ResultSet rs = state.executeQuery(sql);
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while(rs.next()) {
				olist.add(new Object[] {
					rs.getString("과정변호"),"\t"+rs.getString("교사명"),rs.getString("시작일"),
					rs.getString("종료일"),rs.getString("과정명")
				});
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		return null;
	}

	public ArrayList<Object[]> getEmploymentRate(String sel) {
		String sql = "select (select lectureName from tblLecture where lectureSeq = ?) as 과정명, "
				+ " (select count(*) from tblStudent s inner join tblCourse c on s.stdSeq = c.stdSeq " + 
				"    inner join tblLecture l on l.lectureSeq = c.lectureSeq " + 
				"        inner join tblStudentManage sm on sm.courseseq = c.courseseq " + 
				"            where l.lectureSeq = ?)/(select count(*) from tblCourse c inner join tblStudent s on s.stdseq = c.stdSeq " + 
				"    inner join tblLecture l on l.lectureSeq = c.lectureSeq " + 
				"        where l.lectureSeq = ?)*100 as 취업률 from dual";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, sel);
			stat.setString(2, sel);
			stat.setString(3, sel);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			
			if(rs.next()) {
				olist.add(new Object[] {
					rs.getString("과정명"),"\t"+rs.getString("취업률")+"%"
				});
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		
		return null;
	}

	public ArrayList<Object[]> getCompletionRate(String sel) {
		String sql ="select (select lectureName from tblLecture where lectureSeq = ? ) as 과정명, " + 
				"    (select count(*) from tblLectureComplete lc inner join tblCourse c on lc.courseSeq = c.courseSeq " + 
				"    inner join tblLecture l on l.lectureSeq = c.lectureSeq where l.lectureSeq = ? )/(select count(*) from tblLecture l inner join tblCourse c on c.lectureSeq = l.lectureSeq " + 
				"    where l.lectureSeq = ? )*100 as 수료율, (select count(*) from tblDropOut do inner join tblCourse c on c.courseseq = do.courseseq " + 
				"    inner join tblLecture l on l.lectureSeq = c.lectureSeq where l.lectureSeq = ?) as 중도탈락 " + 
				"        from dual";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, sel);
			stat.setString(2, sel);
			stat.setString(3, sel);
			stat.setString(4, sel);
			ResultSet rs = stat.executeQuery();
			ArrayList<Object[]> olist = new ArrayList<Object[]>();
			while(rs.next()) {
				olist.add(new Object[] {
					rs.getString("과정명"),"\t"+rs.getString("수료율")+"%",rs.getString("중도탈락")+"명"	
				});
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주시기 바랍니다.");
		}
		
		return null;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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
}
