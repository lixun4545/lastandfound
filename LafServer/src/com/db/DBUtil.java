package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
//	private static  String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //����JDBC����
//	private static   String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=laf";  //���ӷ����������ݿ�
	private static   String driverName = "com.mysql.jdbc.Driver";
	private static   String dbURL = "jdbc:mysql://localhost:3306/laf";
	private static   String userName = "root";  //Ĭ���û���
	private static   String userPwd = "123";  //����
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL,userName,userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void closeConnection(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
