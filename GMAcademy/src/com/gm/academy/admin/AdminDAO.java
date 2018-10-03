package com.gm.academy.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.gm.academy.Util.DBUtil;
import com.gm.academy.exam.GradeDTO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.SubjectDTO;
import com.gm.academy.student.CourseRecordDTO;
import com.gm.academy.student.StudentDTO;
import com.gm.academy.student.StudentManageDTO;
import com.gm.academy.teacher.TeacherDTO;

public class AdminDAO {
	private Connection conn;
	private PreparedStatement stat;

	public AdminDAO() {
		this.conn = DBUtil.getConnection("localhost","Project","java1234");
	}

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

	public GradeDTO studentGradeInfo(String studentSeq, String studentName, String input) {
		
		StudentDTO student = new StudentDTO();
		GradeDTO grade = new GradeDTO();
		LectureDTO lecture = new LectureDTO();
		SubjectDTO subject = new SubjectDTO();
		
		//선택한 과목코드 입력받은 뒤 해당 학생의 성적 출력 메소드
		try {
			
			String sql = "select s.stdseq, s.stdname, g.gradenotescore as 필기점수, g.gradeskillscore as 실기점수, g.gradeattendancescore as 출석점수, l.lecturename, sb.subjectname from tblCourse c " + 
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
					"                                                where s.stdseq = ? and s.stdname = ? and sb.subjectseq = ? " + 
					"                                                     and g.gradenotescore <> 0";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, studentSeq);
			stat.setString(2, studentName);
			stat.setString(3, input);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				
				student.setSTDSeq(rs.getString("stdseq"));
				student.setSTDName(rs.getString("stdname"));
				grade.setGradeNoteScore(rs.getString("필기점수"));
				grade.setGradeSkillScore(rs.getString("실기점수"));
				grade.setGradeAttendanceScore(rs.getString("출석점수"));
				lecture.setLectuerName(rs.getString("lecturename"));
				subject.setSubjectName(rs.getString("subjectname"));
				
				return grade;
				
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.studentGradeInfo() : " + e.toString());
		}
		
		return null;
	}
//------------------------------------------------------------------------------------------------------------------------------------------
//교육생관리------------------------------------------------------------------------------------------------------------------------------------------
	public AdminLogInDTO auth(AdminLogInDTO admin) {

		AdminLogInDTO result = new AdminLogInDTO();
		
		try {
			
			String sql = "select adminid,adminpw from tbladminlogin where adminid = ? and adminpw = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, admin.getAdminID());
			stat.setString(2, admin.getAdminPW());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				
				result.setAdminID(rs.getString("adminid"));
				result.setAdminPW(rs.getString("adminpw"));
				
				return result;
				
			}
			
		} catch (Exception e) {
			System.out.println("AdminDAO.auth() : " + e.toString());
		}
		
		
		return null;
	}


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
}
