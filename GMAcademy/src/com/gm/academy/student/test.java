package com.gm.academy.student;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class test {
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		String datetime1 = date.format(c.getTime());
		System.out.println(datetime1);
	}
}
