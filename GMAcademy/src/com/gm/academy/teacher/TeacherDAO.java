package com.gm.academy.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.gm.academy.Util.DBUtil;
import com.gm.academy.Util.TeacherUtil;
import com.gm.academy.Util.UtilPrint;
import com.gm.academy.admin.DistributionDTO;
import com.gm.academy.exam.GradeDTO;
import com.gm.academy.lecture.LectureDTO;
import com.gm.academy.lecture.SubjectDTO;
import com.gm.academy.student.StudentDTO;

public class TeacherDAO {
	private Connection conn;
	private UtilPrint out;

	public TeacherDAO() {
		this.conn = DBUtil.getConnection("211.63.89.42","project","JAVA1234");
		this.out = new UtilPrint();
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return 0; 
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return null;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}

		return null;
	}

	public int addCourseRecord(String stdSeq, String lectureSeq, String content) {
		String sql = "insert into tblCourseRecord values(COUNSESEQ.nextval,sysdate,?,(select courseSeq from tblCourse where lectureseq = ? and stdSeq = ?))";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, content);
			stat.setString(2, lectureSeq);
			stat.setString(3, stdSeq);
			return stat.executeUpdate();
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return 0;
	}

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
				olist.add(new Object[] { rs.getString("counseseq"), rs.getString("counsecontents") });
			}
			return olist;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}

		return null;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return null;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return null;
	}

	public int setDistribution(int note, int skill, int attendance) {
		String sql = "update tblDistribution set dstrnote = ? ,dstrskill = ? , dstrattendance =?";

		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setInt(1, note);
			stat.setInt(2, skill);
			stat.setInt(3, attendance);
			int result = stat.executeUpdate();
			return result;
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return 0;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}

		return 0;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}

		return null;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return null;
	}

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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}

		return null;
	}

	public ArrayList<Object[]> getSubjectList(String tchSeq) {
		String sql = "select s.subjectname as 과목명, to_char(ls.SubjectStartDate,'yyyy-mm-dd') as 시작날짜, to_char(ls.SubjectEndDate,'yyyy-mm-dd') as 종료날짜,"
				+ "    l.lecturecurrentstd as 수강인원, c.classname as 강의실"
				+ "        from tblLecture l inner join tblClassroom c on l.classseq = c.classseq inner join tblLectureSubject ls on ls.lectureseq = l.lectureseq"
				+ "            inner join tblSubject s on ls.subjectseq = s.subjectseq "
				+ "                where tchseq = ?";
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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}

		return null;
	}

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
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
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
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			}
		}
		return null;
	}

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
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
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
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			}
		}
		return 0;
	}

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
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
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
			}catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			}
		}
		return null;
	}

	public int removeExam(String examSeq, int k) {
		String sql;
		if(k==1) {
			sql = "delete from tblNoteTest where noteQueSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, examSeq);
				return stat.executeUpdate();
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			}
		}else if(k==2) {
			sql = "delete from tblSkillTest where skillQueSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, examSeq);
				return stat.executeUpdate();
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			}
		}
		
		
		
		
		return 0;
	}

	public int updateExam(String examSeq, String examContent, int k) {
		String sql;
		if(k==1) {
			sql = "update tblNoteTest set noteQuestion = ? where noteQueSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, examContent);
				stat.setString(2, examSeq);
				return stat.executeUpdate();
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			}
		}else if(k==2) {
			sql = "update tblSkillTest set skillQuestion = ? where skillQueSeq = ?";
			try {
				PreparedStatement stat = conn.prepareStatement(sql);
				stat.setString(1, examContent);
				stat.setString(2, examSeq);
				return stat.executeUpdate();
			} catch (Exception e) {
				out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
			}
		}
		return 0;
	}

	public ArrayList<SubjectDTO> getSubjectAndSeqList(String tchSeq) {
		String sql = "select s.subjectName as 과목명, s.subjectSeq as 과목번호 from tblSubject s inner join tblLectureSubject ls on s.subjectSeq = ls.subjectSeq " + 
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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		
		return null;
	}

	public ArrayList<StudentDTO> getStudentDTO(String tchSeq) {
		String sql = "select s.stdName as 학생명, s.stdSeq 학생번호 from tblStudent s inner join tblCourse c on c.stdSeq = s.stdSeq " + 
				"    inner join tblLecture l on l.lectureSeq = c.lectureSeq where l.tchSeq = ?";
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
		} catch (Exception e) {
			out.result("입력오류가 발생했습니다. 신중히 입력해주세요.");
		}
		return null;
	}
}
