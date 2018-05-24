package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.User;
import com.db.DBUtil;

public class UserDao {
	public List<User> select(){
		List<User> list =new ArrayList<User>();
		Connection conn=DBUtil.getConnection();
		Statement stm=null;
		ResultSet rs  =null;
		try {
			stm =conn.createStatement();
			rs=stm.executeQuery("select * from user ");
			while(rs.next()){
				User us=new User();
				us.setName(rs.getString(2));
				us.setPassword(rs.getString(3));
				us.setNum(rs.getInt(1));
				list.add(us);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBUtil.closeConnection(conn);
		}
		return list;
	}
	
	public boolean selectName(String name){
		List<User> list =new ArrayList<User>();
		Connection conn=DBUtil.getConnection();
		boolean n=true;  //如果n=true说明数据库中找不到相同的用户名，可以注册 false代表用户名相同
		Statement stm=null;
		ResultSet rs =null;
		try {
			stm =conn.createStatement();
			rs=stm.executeQuery("select * from user where name='"+name+"'");
			if(rs.next()){
				n=false;
			}
			else{
				n=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBUtil.closeConnection(conn);
		}
		return n;
	}
	
	public void add(User use){
		Connection conn = DBUtil.getConnection();
		Statement stm = null;
		try {
			stm = conn.createStatement();
			String sql = "insert into user (name,password,tel) values('"+use.getName()+"','"
			+use.getPassword()+"','"+use.getTel()+"')";
			System.out.println(sql);
			stm.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(conn);
		}
//		Bitmap bitmap = null;  
//        try  
//        {  
//            // out = new FileOutputStream("/sdcard/aa.jpg");  
//            byte[] bitmapArray;  
//            bitmapArray = Base64.decode(st, Base64.DEFAULT);  
//            bitmap =  
//                    BitmapFactory.decodeByteArray(bitmapArray, 0,  
//                            bitmapArray.length);  
//            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);  
//            return bitmap;  
//        }  
//        catch (Exception e)  
//        {  
//            return null;  
//        }  
	}
	public String sign(User use){
		Connection conn = DBUtil.getConnection();
		Statement stm = null;
		ResultSet rs =null;
		try {
			stm = conn.createStatement();
			String sql = "select * from user where name= '"+use.getName()+"'";
			System.out.println(sql);
			String uno="erro";
			String quanxian;
			rs=stm.executeQuery(sql);
			String password="1";
			while(rs.next()){
				uno=rs.getString(1);
				password=rs.getString(3);
				quanxian=rs.getString(6);
				uno=uno+","+quanxian;
			}
			System.out.println(password+use.getPassword()+"    "+uno);
			if(use.getPassword().equals(password)) //0代表密码错误,1代表密码正确
				return uno;
			else return "erro";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "erro";
		}finally{
			DBUtil.closeConnection(conn);
		}
	}
	public boolean update(User use){
		boolean s=true;
		Connection conn=DBUtil.getConnection();
		Statement stm=null;	
		try {
			stm=conn.createStatement();
			String sql="update user set password='"+use.getPassword()+"',tel='"+use.getTel()+"' where uno="+use.getNum();
			stm.executeUpdate(sql);
			s=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			s=false;
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(conn);
		}
		return s;
	}
}
