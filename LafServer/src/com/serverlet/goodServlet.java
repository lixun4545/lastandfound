package com.serverlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.security.action.GetBooleanAction;

import com.bean.Good;
import com.bean.Good_User;
import com.bean.News;
import com.bean.User;
import com.dao.GoodDao;
import com.dao.UserDao;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

public class goodServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public goodServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String param = request.getParameter("param");
		System.out.println(param);
		if (param != null && param.equals("selectsome")) {
			this.selectsome(request, response);
		} else if (param != null && param.equals("gettel")) {
			this.gettel(request, response);
		} else if (param != null && param.equals("goodadd")) {
			this.goodadd(request, response);
		} else if (param != null && param.equals("myselect")) {
			this.myselect(request, response);
		} else if (param != null && param.equals("ugadd")) {
			this.ugadd(request, response);
		} else if (param != null && param.equals("selectun")) {
			this.selectun(request, response);
		} else if (param != null && param.equals("updategood")) {
			this.updategood(request, response);
		}else if (param != null && param.equals("newsadd")) {
			this.newsadd(request, response);
		}else if (param != null && param.equals("newssele")) {
			this.newssele(request, response);
		}

	}
	public void newssele(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
//		String uno = request.getParameter("uno");
		// String type=request.getParameter("type");
//		System.out.println(uno);
		GoodDao dao = new GoodDao();
		List<News> list;
		list = dao.newssele();
		Gson gson = new Gson();
		System.out.println(gson.toJson(list).toString());
		out.print(gson.toJson(list));
		out.flush();
		out.close();
	}
	public void selectsome(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		if (name != null)
			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");

		String type = request.getParameter("type");
		if (type != null)
			type = new String(type.getBytes("ISO-8859-1"), "UTF-8");
		System.out.println(name);
		GoodDao dao = new GoodDao();
		List<Good> list;
		if (type == null)
			list = dao.selectsome(name);
		else {
			if (name != null) {
				list = dao.selectsome1(name, type);
//				System.out.println("111");
			} else {
				list = dao.selectsome2(type);
//				System.out.println("222");
			}
		}
		Gson gson = new Gson();
//		System.out.println(gson.toJson(list).toString());
		out.print(gson.toJson(list));
		out.flush();
		out.close();
	}

	public void selectun(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		GoodDao dao = new GoodDao();
		List<Good_User> list;
		list = dao.selectun();
		Gson gson = new Gson();
//		System.out.println(gson.toJson(list).toString());
		out.print(gson.toJson(list));
		out.flush();
		out.close();
	}

	public void myselect(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String uno = request.getParameter("uno");
		// String type=request.getParameter("type");
		System.out.println(uno);
		GoodDao dao = new GoodDao();
		List<Good> list;
		list = dao.myselect(uno);
		Gson gson = new Gson();
		System.out.println(gson.toJson(list).toString());
		out.print(gson.toJson(list));
		out.flush();
		out.close();
	}

	public void ugadd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String uno = request.getParameter("uno");
		String gno = request.getParameter("gno");
		String gname = request.getParameter("gname");
		gname = new String(gname.getBytes("ISO-8859-1"), "UTF-8");
		System.out.println(uno + gno + gname);
		GoodDao dao = new GoodDao();
		dao.ugadd(uno, gno, gname);
		Gson gson = new Gson();
		out.flush();
		out.close();
	}

	private void gettel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// http://localhost:8080/androidServer/servlet/StuServlet?param=add&sno=111&sname=111&dept=111&age=20
		String uno = request.getParameter("uno");

		GoodDao dao = new GoodDao();
		JSONObject root = new JSONObject();
		String tel = dao.gettel(uno);
		root.put("tel", tel);
		PrintWriter out = response.getWriter();
		out.print(root);
		out.flush();
		out.close();
	}

	private void updategood(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// http://localhost:8080/androidServer/servlet/StuServlet?param=add&sno=111&sname=111&dept=111&age=20
		String gno = request.getParameter("gno");

		GoodDao dao = new GoodDao();
		// JSONObject root = new JSONObject();
		dao.updategood(gno);
		// root.put("tel", tel);
		PrintWriter out = response.getWriter();
		// out.print(root);
		out.flush();
		out.close();
	}

	public void newsadd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			String jsonString = request.getParameter("jsonString");
			JSONObject json = new JSONObject(jsonString);
			String neirong = json.get("neirong").toString();
			String name = json.get("name").toString();
//			String imagename = getnowtime();
//			String time = json.get("time").toString();
//			String other = json.get("other").toString();
//			String adress = json.get("adress").toString();
			String uno = json.get("uno").toString();
//			String type = json.get("type").toString();
//			GenerateImage(image, uno + imagename);
			
			News news = new News();
			news.setUno(uno);// 0是未认领，1是认领
			news.setName(name);
			news.setNeirong(neirong);
			GoodDao dao = new GoodDao();
			if (dao.addnew(news)) {
				PrintWriter out = response.getWriter();
				out.print("true");
			} else {
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.print("false");
			e.printStackTrace();
		}

	}
	public void goodadd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			String jsonString = request.getParameter("jsonString");
			JSONObject json = new JSONObject(jsonString);
			String name = json.get("name").toString();
			String image = json.get("image").toString();
			String imagename = getnowtime();
			String time = json.get("time").toString();
			String other = json.get("other").toString();
			String adress = json.get("adress").toString();
			String uno = json.get("uno").toString();
			String type = json.get("type").toString();
			GenerateImage(image, uno + imagename);
			
			Good good = new Good();
			good.setClaimstate(false);// 0是未认领，1是认领
			good.setDescribe(other);
			good.setImagname(uno + imagename);
			good.setName(name);
			good.setTime(time);
			good.setType(type);
			good.setAdress(adress);
			good.setUno(Integer.parseInt(uno));
			GoodDao dao = new GoodDao();
			if (dao.add(good)) {
				PrintWriter out = response.getWriter();
				out.print("true");
			} else {
				PrintWriter out = response.getWriter();
				out.print("false");
			}
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.print("false");
			e.printStackTrace();
		}

	}
	public void init() throws ServletException {
		// Put your code here
	}

	public static boolean GenerateImage(String imgStr, String filename) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = "D:/图片/" + filename + ".jpg";// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String getnowtime() {

		// 获取当前时间
		Date date = new Date();
		// SimpleDateFormat sdf = new
		// SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.SIMPLIFIED_CHINESE);
		// 添加格式化格式
		sdf.applyPattern("yyyyMMddHHmmss");
		String timeStr = sdf.format(new Date());
		System.out.println(timeStr);
		return timeStr;
	}
}
