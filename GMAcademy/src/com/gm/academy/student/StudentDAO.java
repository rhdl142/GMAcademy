package com.gm.academy.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.gm.academy.MainClass;
import com.gm.academy.Util.DBUtil;
import com.gm.academy.exam.GradeDTO;

public class StudentDAO {
	private Connection conn;
	private PreparedStatement stat;
	
	public StudentDAO() {
		this.conn = DBUtil.getConnection("211.63.89.42","project","JAVA1234");
	}

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
			
		} catch (Exception e) {
			System.out.println("StudentDAO.auth :" + e.toString());
		}
		return null;
	}

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
		} catch (Exception e) {
			System.out.println("StudentDAO.grade :" + e.toString());
		}
		return null;
	}

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
		} catch (Exception e) {
			System.out.println("StudentDAO.inRegister :" + e.toString());
		}
		return 0;
	}

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
		} catch (Exception e) {
			System.out.println("StudentDAO.lectureEvaluation :" + e.toString());
		}
		return 0;
	}

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
		} catch (Exception e) {
			System.out.println("StudentDAO.subjectEvaluation :" + e.toString());
		}
		return 0;
	}

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
		} catch (Exception e) {
			System.out.println("StudentDAO.consulting :" + e.toString());
		}
		return null;
	}

	//출결현황
	public ArrayList<AbsenceRecordType> attendanceStatus() {
		try {
			ArrayList<AbsenceRecordType> list = new ArrayList<>();
			
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
			
			while(rs.next()) {
				AbsenceRecordType artDTO = new AbsenceRecordType();
				
				artDTO.setTypeA(rs.getString("a"));
				artDTO.setTypeB(rs.getString("b"));
				artDTO.setTypeC(rs.getString("c"));
				artDTO.setTypeD(rs.getString("d"));
				artDTO.setTypeE(rs.getString("e"));
				artDTO.setTypeF(rs.getString("f"));
				
				list.add(artDTO);
			}
			return list;
		} catch (Exception e) {
			System.out.println("StudentDAO.attendanceStatus :" + e.toString());
		}
		return null;
	}
}














