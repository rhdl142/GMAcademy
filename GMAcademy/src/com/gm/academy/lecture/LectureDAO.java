package com.gm.academy.lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.gm.academy.Util.DBUtil;

public class LectureDAO {
	private Connection conn;
	private PreparedStatement stat;
	
	public LectureDAO() {
		this.conn = DBUtil.getConnection("localhost","Project","java1234");
	}
}
