package com.gm.academy.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.gm.academy.Util.DBUtil;

public class TeacherDAO {
	private Connection conn;
	private PreparedStatement stat;
	
	public TeacherDAO() {
		this.conn = DBUtil.getConnection("localhost","Project","java1234");
	}
}
