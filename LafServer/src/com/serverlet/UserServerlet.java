package com.serverlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import com.bean.User;
import com.dao.UserDao;
import com.google.gson.Gson;

public class UserServerlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserServerlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String param = request.getParameter("param");
//		System.out.println(param+"get");
		if (param != null && param.equals("sign")) {
			System.out.println("sign");
			this.sign(request, response);
		} else if (param != null && param.equals("add")) {
			System.out.println("add");
			this.add(request, response);
		} else if (param != null && param.equals("img")) {
			System.out.println("img");
			this.img(request, response);
		} else if (param != null && param.equals("update")) {
			System.out.println("update");
			this.update(request, response);
		}
		else {
			this.select(request, response);
		}
		
		// this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		 String param=request.getParameter("param");
//		 System.out.println("post");
		// this.add(request, response);
		this.doGet(request, response);

	}

	private void select(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		UserDao dao = new UserDao();
		List<User> list = dao.select();
		Gson gson = new Gson();
		out.print(gson.toJson(list));
		out.flush();
		out.close();
	}

	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// http://localhost:8080/androidServer/servlet/StuServlet?param=add&sno=111&sname=111&dept=111&age=20
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		User use = new User();
		use.setName(name);
		use.setTel(tel);
		use.setPassword(password);
		UserDao dao = new UserDao();

		JSONObject root = new JSONObject();

		if (dao.selectName(name)) {
			root.put("state", "1");// 0是假用户名相同 1是真 增加成功
			dao.add(use);
		} else {
			root.put("state", "0");
		}
		PrintWriter out = response.getWriter();
		out.print(root);
		out.flush();
		out.close();
	}

	private void img(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// http://localhost:8080/androidServer/servlet/StuServlet?param=add&sno=111&sname=111&dept=111&age=20
		String name = request.getParameter("name");
		System.out.println(name);

	}

	private void sign(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// http://localhost:8080/androidServer/servlet/StuServlet?param=add&sno=111&sname=111&dept=111&age=20
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		User use = new User();
		use.setName(name);
		use.setPassword(password);
		UserDao dao = new UserDao();
		JSONObject root = new JSONObject();

		if (dao.selectName(name)) {
			System.out.println("用户名不存在");
			root.put("state", "-1");// 0是假用户名密码错误 1是真 密码正确 -1是用户名不存在
//			root.put("quanxian", value)
		} else {
			String uno=dao.sign(use);
			System.out.println(uno);
			if (!uno.equals("erro")){
				String[] sourceStrArray = uno.split(",");
				root.put("state", sourceStrArray[0]);
				root.put("quanxian", sourceStrArray[1]);
				}
			else
				root.put("state", "0");
		}
		PrintWriter out = response.getWriter();
		out.print(root);
		out.flush();
		out.close();
	}
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String jsonString=request.getParameter("jsonString");
			JSONObject json=new JSONObject(jsonString);
			String password=(String) json.get("password");
			String newpassword=(String) json.get("newpassword");
			String tel=(String) json.get("tel");
			String uno=(String) json.get("uno");
			String name=(String) json.get("name");
			UserDao dao =new UserDao();
			User user=new User();
			user.setPassword(password);
			user.setTel(tel);
			user.setName(name);
			user.setNum(Integer.parseInt(uno));
			if(dao.sign(user).equals("erro")){
				PrintWriter out = response.getWriter();
				out.print("0");//密码错误
			}
			else
			{
				user.setPassword(newpassword);
				if(dao.update(user)){
					PrintWriter out = response.getWriter();
					out.print("1");
				}
				else{
					PrintWriter out = response.getWriter();
					out.print("-1");//未知错误
				}
			}
	}
	
	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
