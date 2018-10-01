package com.gm.academy.Util;

import java.util.Scanner;

public class UtilScanner {
	private Scanner scan;
	
	public UtilScanner() {
		 scan = new Scanner(System.in);
	}
	
	//String입력
	public String next(String label) {
		System.out.printf(label+" :");
		return scan.nextLine();		
	}
	
	//String 다음칸 입력	
	public String nextln(String label) {
		System.out.println(label+" :");
		return scan.nextLine();		
	}
	
	//Int입력
	public int nextInt(String label) {
		System.out.print(label+" :");
		return Integer.parseInt(scan.nextLine());		
	}
	
	//Int 다음칸 입력
	public int nextIntln(String label) {
		System.out.println(label+" :");
		return Integer.parseInt(scan.nextLine());		
	}
	
	public String nextLine() {
		return scan.nextLine();
	}
	
	public int nextInt() {
		return Integer.parseInt(scan.nextLine());
	}
}
