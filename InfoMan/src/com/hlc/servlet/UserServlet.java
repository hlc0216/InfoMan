package com.hlc.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hlc.service.UserService;
import com.hlc.service.impl.UserServiceImpl;

import projo.User;

public class UserServlet extends HttpServlet {
	// 声明日志对象
	Logger logger = Logger.getLogger(UserServlet.class);
	// 获取service层对象
	UserService us = new UserServiceImpl();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 设置编码格式
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 获取操作符
		String oper = req.getParameter("oper");
		if ("login".equals(oper))
			checkUserLogin(req, resp);// 调用登陆处理操作
		else if ("out".equals(oper))
			userOut(req, resp);// 调用退出登陆
		else if ("pwd".equals(oper))
			userChangePwd(req, resp);//修改密码
		else if ("show".equals(oper))
			userShow(req, resp);//显示所有用户信息
		else if ("reg".equals(oper))
			userReg(req, resp);//用户注册
		else
			logger.debug("没有找到对应的操作符");
	}
	// 注册用户信息

	private void userReg(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// 获取请求信息
		String uname = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		String sex = req.getParameter("sex");
		int age = req.getParameter("age") != "" ? Integer.parseInt(req.getParameter("age")) : 0;
		String birth = req.getParameter("birth");
		String[] bs = null;
		if (birth != "") {
			bs = birth.split("-");
			birth = bs[0] + "-" + bs[1] + "-" + bs[2];
		}
		logger.debug("要进行注册用户的数据" + uname + "/" + pwd + "/" + sex + "/" + age + "/" + birth);
		User u = new User(0, uname, pwd, sex, age, birth);
		// 处理请求信息
		// 调用业务层处理
		int index = us.userRegService(u);
		// 响应处理结果
		if (index > 0) {
			// 获取sesssion
			HttpSession hs = req.getSession();
			hs.setAttribute("flag", 2);
			// 重定向
			resp.sendRedirect("/InfoMan/login.jsp");
		}

	}

	private void userShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 显示用户所有信息
		// 处理请求
		// 调用service
		List<User> lu = us.userShowService();

		if (lu != null) {
			// 将查询的用户数据存储到request对象
			req.setAttribute("lu", lu);
			// 请求转发
			req.getRequestDispatcher("/user/showUser.jsp").forward(req, resp);
			return;
		}

	}

	private void userChangePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 获取数据
		String newPwd = req.getParameter("newPwd");
		// 从session中获取用户信息
		User u = (User) req.getSession().getAttribute("user");
		int uid = u.getUid();
		
		// 处理请求
		// 调用service方法
		int index = us.userChangePwdService(newPwd, uid);
		if (index!=-1) {
			// 获取session对象
			HttpSession hs = req.getSession();
			hs.setAttribute("flag", 1);
			// 重定向到登陆页面
			resp.sendRedirect("/InfoMan/login.jsp");

		}
	}

	private void userOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 获取session对象
		HttpSession hs = req.getSession();
		// 强制销毁session
		hs.invalidate();
		// 重定向到登陆页面
		resp.sendRedirect("/InfoMan/login.jsp");
	}

	private void checkUserLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 获取请求信息
		String uname = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		// 处理请求信息
		// 校验
		User u = us.checkUserLoginService(uname, pwd);
		if (u != null) {
			// 获取session对象
			HttpSession hs = req.getSession();
			// 将用户数据存储到session中
			hs.setAttribute("user", u);
			// 重定向
			resp.sendRedirect("/InfoMan/main/main.jsp");
			return;
		} else {
			req.setAttribute("flag", 0);
			// 请求转发
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}

	}
}
