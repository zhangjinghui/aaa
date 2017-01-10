package com.zjh.db;

import java.sql.*;
public class DB {
	private static Connection conn=null;
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/aaa?useUnicode=true&characterEncoding=utf-8","root","zhangjinghui");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(){
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
