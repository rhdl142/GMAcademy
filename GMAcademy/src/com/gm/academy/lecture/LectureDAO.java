package com.gm.academy.lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.gm.academy.Util.DBUtil;

public class LectureDAO {
	private Connection conn;
	
	
	public LectureDAO() {
		this.conn = DBUtil.getConnection("211.63.89.42","project","JAVA1234");
	}

	public ArrayList<String> getLectureNameList() {
		String sql = "select lectureName from tblLecture where lectureprogress = '강의중'";

		try { 
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			ArrayList<String> array = new ArrayList<String>();
			while(rs.next()) {
				array.add(rs.getString(1));
			}
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public ArrayList<LectureDTO> getLectureDTO() {
		String sql ="select  LECTURESEQ,LECTURENAME,to_char(LECTURESTARTDATE,'yyyy-mm-dd') as LECTURESTARTDATE,to_char(LECTUREENDDATE,'yyyy-mm-dd') as LECTUREENDDATE, " + 
				"    LECTUREPROGRESS,LECTUREACCEPTSTD,LECTURECURRENTSTD,CLASSSEQ,TCHSEQ" + 
				"                from tblLecture where lectureprogress = '강의중'";
		ArrayList<LectureDTO> array = new ArrayList<LectureDTO>();
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setLectureSeq(rs.getString("LECTURESEQ"));
				dto.setLectuerName(rs.getString("LECTURENAME"));
				dto.setLectureStartDate(rs.getString("LECTURESTARTDATE"));
				dto.setLectureEndDate(rs.getString("LECTUREENDDATE"));
				dto.setLectureProgress(rs.getString("LECTUREPROGRESS"));
				dto.setLectureAcceptSTD(rs.getString("LECTUREACCEPTSTD"));
				dto.setLectureCurrentSTD(rs.getString("LECTURECURRENTSTD"));
				dto.setClassSeq(rs.getString("CLASSSEQ"));
				dto.setTCHSeq(rs.getString("tchSeq"));
				array.add(dto);
			}
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
