package com.gm.academy.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.gm.academy.Util.DBUtil;
import com.gm.academy.teacher.TeacherDTO;

public class AdminDAO {
	private Connection conn;
	private PreparedStatement stat;

	public AdminDAO() {
		this.conn = DBUtil.getConnection("localhost","Project","java1234");
	}

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
	



}
