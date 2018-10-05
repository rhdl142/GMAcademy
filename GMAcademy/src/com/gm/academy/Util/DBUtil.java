package com.gm.academy.Util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 디비객체
 * @author 3조
 *
 */
public class DBUtil {
	
	/**
	 * 디비에 연결하는 메소드
	 * @param server 서버
	 * @param id 유저명
	 * @param pw 비밀번호
	 * @return
	 */
	public static Connection getConnection(String server, String id, String pw) {
		Connection conn = null;
		
		String url = "jdbc:oracle:thin:@"+ server +":1521:xe";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, id, pw);
			
			return conn;
		} catch (Exception e) {
			System.out.println("DBUtil.getConnection :" + e.toString());
		}
		
		return null;
	}
}


