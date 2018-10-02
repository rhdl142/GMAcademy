package com.gm.academy.Util;

import java.util.Iterator;
import java.util.Stack;

import com.gm.academy.MainClass;

public class BreadCrumb {
	private  Stack<String> stack;
	
	public BreadCrumb() {
		this.stack = new Stack<String>();
	}
	
	public void in(String title) {
		stack.push(title);
	}
	
	public void out() {
		stack.pop();
	}
	
	public void now() { 
		//stack loop 출력
		Iterator<String> iter = stack.iterator();
		
		System.out.println();
		
		String temp = String.format("[%s 접속중] ", MainClass.isAuth != null ? MainClass.isAuth : "guest");
		
		while(iter.hasNext()) {
			temp = temp + iter.next() + "▶";
		}
		
		System.out.println(temp.substring(0,temp.length()-1));
	}
	public int size() {
		return stack.size();
	}
}
