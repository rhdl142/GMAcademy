package com.gm.academy.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.gm.academy.Util.DBUtil;

public class ExamDAO {
	private Connection conn;
	private PreparedStatement stat;
	
	public ExamDAO() {
		this.conn = DBUtil.getConnection("localhost","Project","java1234");
	}
}
