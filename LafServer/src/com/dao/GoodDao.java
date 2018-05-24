package com.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sun.misc.BASE64Encoder;

import com.bean.Good;
import com.bean.Good_User;
import com.bean.News;
import com.bean.User;
import com.db.DBUtil;

public class GoodDao {

	public List<News> list8 = new ArrayList<News>();
	public List<Good> list = new ArrayList<Good>();
	public List<Good_User> list2 = new ArrayList<Good_User>();
	public List<Good> selectsome(String name) {
		Connection conn = DBUtil.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			System.out.println("select * from goods where (name like'%" + name
					+ "%' or `describe` like '%" + name + "%') and claimstate = 'false'");
			rs = stm.executeQuery("select * from goods where (name like'%" + name
					+ "%' or `describe` like '%" + name + "%') and claimstate = 'false'");
			while (rs.next()) {
				Good good = new Good();
				good.setGno(Integer.parseInt(rs.getString(1)));
				good.setName(rs.getString(2));
				good.setTime(rs.getString(3));
				good.setDescribe(rs.getString(4));
				good.setUno(Integer.parseInt(rs.getString(5)));
				good.setType(rs.getString(7));
				good.setImagname(rs.getString(8));
				good.setImag(GetImageStr(rs.getString(8).trim()));
				good.setClaimstate(Boolean.parseBoolean(rs.getString(6)));
				good.setAdress(rs.getString(9));
				System.out.println(rs.getString(9));
				list.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return list;
	}
	public List<Good_User> selectun() {
		Connection conn = DBUtil.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			System.out.println("select * from good_user ");
			rs = stm.executeQuery("select * from good_user ");
			while (rs.next()) {
				Good_User guood = new Good_User();
				guood.setUnno(rs.getString(1));
				guood.setUno(rs.getString(2));
				guood.setGno(rs.getString(3));
				guood.setUname(rs.getString(4));
				guood.setGname(rs.getString(5));
				guood.setTime(rs.getString(6));
				list2.add(guood);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return list2;
	}
	public void updategood(String gno){
		boolean s=true;
		Connection conn=DBUtil.getConnection();
		Statement stm=null;	
		try {
			stm=conn.createStatement();
			String sql="update goods set claimstate=1 where gno= '"+gno+"'";
			stm.executeUpdate(sql);
			s=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			s=false;
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(conn);
		}
	}
	public List<Good> selectsome2(String type) {
		Connection conn = DBUtil.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			System.out.println("select * from goods where type ='" + type
					+ "' and claimstate = 'false'");
			rs = stm.executeQuery("select * from goods where type ='" + type
					+ "' and claimstate = 'false'");
			while (rs.next()) {
				Good good = new Good();
				good.setGno(Integer.parseInt(rs.getString(1)));
				good.setName(rs.getString(2));
				good.setTime(rs.getString(3));
				good.setDescribe(rs.getString(4));
				good.setUno(Integer.parseInt(rs.getString(5)));
				good.setType(rs.getString(7));
				good.setImagname(rs.getString(8));
				good.setImag(GetImageStr(rs.getString(8).trim()));
				good.setClaimstate(Boolean.parseBoolean(rs.getString(6)));
				good.setAdress(rs.getString(9));
				System.out.println(rs.getString(9));
				list.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return list;
	}
	public List<News> newssele() {
		Connection conn = DBUtil.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			System.out.println("select * from news");
			rs = stm.executeQuery("select * from news ");
			while (rs.next()) {
				News news = new News();
				news.setName(rs.getString(4));
				news.setNeirong(rs.getString(3));
				news.setUno(rs.getString(2));
				list8.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return list8;
	}
	public List<Good> myselect(String uno) {
		Connection conn = DBUtil.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			System.out.println("select * from goods where uno ='" + uno
					+ "'");
			rs = stm.executeQuery("select * from goods where uno ='" + uno
					+ "'");
			while (rs.next()) {
				Good good = new Good();
				good.setGno(Integer.parseInt(rs.getString(1)));
				good.setName(rs.getString(2));
				good.setTime(rs.getString(3));
				good.setDescribe(rs.getString(4));
				good.setUno(Integer.parseInt(rs.getString(5)));
				good.setType(rs.getString(7));
				good.setImagname(rs.getString(8));
				good.setImag(GetImageStr(rs.getString(8).trim()));
				good.setClaimstate(Boolean.parseBoolean(rs.getString(6)));
				good.setAdress(rs.getString(9));
				System.out.println(rs.getString(9));
				list.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return list;
	}
	public List<Good> selectsome1(String name,String type){
		Connection conn = DBUtil.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			System.out.println("select * from goods where type='"+type+"' and gno like'%" + name //因为乱码所以此出暂时用gno
					+ "%' or `describe` like '%" + name + "%' and claimstate = 'false'");
			rs = stm.executeQuery("select * from goods where type='"+type+"' and gno like'%" + name //因为乱码所以此出暂时用gno
					+ "%' or name like '%" + name + "%' and claimstate = 'false'");
			while (rs.next()) {
				Good good = new Good();
				good.setGno(Integer.parseInt(rs.getString(1)));
				good.setName(rs.getString(2));
				good.setTime(rs.getString(3));
				good.setDescribe(rs.getString(4));
				good.setUno(Integer.parseInt(rs.getString(5)));
				good.setType(rs.getString(7));
				good.setImagname(rs.getString(8));
				good.setImag(GetImageStr(rs.getString(8).trim()));
				good.setClaimstate(Boolean.parseBoolean(rs.getString(6)));
				good.setAdress(rs.getString(9));
				System.out.println(rs.getString(9));
				list.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return list;
	}

	public String gettel(String uno) {
		Connection conn = DBUtil.getConnection();
		String tel = "1"; // 如果n=true说明数据库中找不到相同的用户名，可以注册 false代表用户名相同
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery("select tel,QQ from user where uno=" + uno);
			while (rs.next()) {
				tel = rs.getString(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return tel;
	}
	
	public boolean add(Good good){
		
		Connection conn=DBUtil.getConnection();
		Statement stm=null;	
		try {
			stm=conn.createStatement();
			String sql="insert into goods(name,time,`describe`,uno,claimstate,type,imagname,adress)" +
					" values ('"+good.getName()+
					"','"+good.getTime()+
					"','"+good.getDescribe()+
					"','"+good.getUno()+
					"','"+"false"+
					"','"+good.getType()+
					"','"+good.getImagname()+
					"','"+good.getAdress()+
					"')";
			System.out.println(sql);
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			return false;
		}finally{
			DBUtil.closeConnection(conn);
		}
		
		return true;
	}
	//------------------------------------------------------------------------------------
	public boolean addnew(News news){
		
		Connection conn=DBUtil.getConnection();
		Statement stm=null;	
		try {
			stm=conn.createStatement();
			String sql="insert into news(name,uid,txt)" +
					" values ('"+news.getName()+
					"','"+news.getUno()+
					"','"+news.getNeirong()+
					"')";
			System.out.println(sql);
			stm.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			return false;
		}finally{
			DBUtil.closeConnection(conn);
		}
		
		return true;
	}
	//------------------------------------------------------------------------------------
	public void ugadd(String uno,String gno,String gname){
		Connection conn = DBUtil.getConnection();
		String uname = "1"; // 
//		String gname = "2";
		Statement stm = null;
		
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery("select name from user where uno=" + uno);
			System.out.println("select name from user where uno=" + uno);
			
			while (rs.next()) {
				uname = rs.getString(1);
			}			
			System.out.println(uname);
			String sql="insert into good_user(uno,gno,uname,nname,time)" +
					" values ('"+uno+
					"','"+gno+
					"','"+uname+
					"','"+gname+
					"','"+getnowtime()+
					"')";
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
	}
	public static String getnowtime() {

		// 获取当前时间
		Date date = new Date();
		// SimpleDateFormat sdf = new
		// SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm",
				Locale.SIMPLIFIED_CHINESE);
		// 添加格式化格式
//		 sdf.applyPattern("yyyyMMddHHmmss");
		String timeStr = sdf.format(new Date());
		System.out.println(timeStr);
		return timeStr;
	}
	public static String GetImageStr(String imagname) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	// String imgFile = "C:/Users/Star/Desktop/test.png";// 待处理的图片
		String imgFile = "D:/图片/" + imagname + ".jpg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
}
