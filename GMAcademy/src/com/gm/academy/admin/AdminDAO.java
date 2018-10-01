package com.gm.academy.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.gm.academy.Util.DBUtil;

public class AdminDAO {
	private Connection conn;
	private PreparedStatement stat;
	
	public AdminDAO() {
		this.conn = DBUtil.getConnection("localhost","Project","java1234");
	}
}
