package com.gm.academy.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.gm.academy.Util.DBUtil;

public class StudentDAO {
	private Connection conn;
	private PreparedStatement stat;
	
	public StudentDAO() {
		this.conn = DBUtil.getConnection("localhost","Project","java1234");
	}
}
