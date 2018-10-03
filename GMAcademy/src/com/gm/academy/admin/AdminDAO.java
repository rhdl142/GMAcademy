package com.gm.academy.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.gm.academy.Util.DBUtil;
import com.gm.academy.lecture.LectureDTO;
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
	
}
