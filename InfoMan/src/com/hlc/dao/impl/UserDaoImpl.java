package com.hlc.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hlc.dao.UserDao;

import projo.User;

public class UserDaoImpl implements UserDao {
	/**
	 * 根据用户名和密码查询用户信息
	 */
	Logger logger = Logger.getLogger(UserDaoImpl.class);
	public User checkUserLoginDao(String uname, String pwd) {
		User u = null;
		// 声明jdbc对象
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println("UserDaoImpl--->" + uname + ":" + pwd);

		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 获取连接对象
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_in?useUnicode=true&characterEncoding=utf-8", "root", "123456");
			// 创建sql命令
			String sql = "select * from t_user where uname=? and pwd=?";
			// 创建sql命令对象
			ps = conn.prepareStatement(sql);
			// 给占位符赋值
			ps.setString(1, uname);
			ps.setString(2, pwd);
			// 执行
			rs = ps.executeQuery();
			// 遍历执行结果
			while (rs.next()) {
				u = new User();
				// 给变量赋值
				u.setUid(rs.getInt("uid"));
				u.setUname(rs.getString("uname"));
				u.setPwd(rs.getString("pwd"));
				u.setSex(rs.getString("sex"));
				u.setAge(rs.getInt("age"));
				u.setBirth(rs.getString("birth"));
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// 返回

		return u;
	}

	@Override
	public int userChangePwdDao(String newPwd, int uid) {
		// 声明对象
		User u = null;
		Connection conn = null;
		PreparedStatement ps = null;
		int index = -1;
		// 加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 获取链接
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_in?useUnicode=true&characterEncoding=utf-8", "root", "123456");
			// 创建sql命令
			String sql = "update t_user set pwd=? where uid=?";
			// 创建sql命令对象
			ps = conn.prepareStatement(sql);
			// 为占位符赋值
			ps.setString(1, newPwd);
			ps.setInt(2, uid);
//			Logger.debug("newPwd是："+newPwd+"uid是"+uid);
			System.out.println("newPwd是："+newPwd+"uid是"+uid);
			// 执行
			index = ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return index;
	}
	
	// 显示所有用户的信息

	@Override
	public List<User> userShowDao() {
		// 声明对象
		List<User> lu = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// 加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 获取连接
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_in?useUnicode=true&characterEncoding=utf-8", "root", "123456");
			// 创建sql命令
			String sql = "select * from t_user";
			// 创建sql命令对象
			ps = conn.prepareStatement(sql);
			// 执行sql
			rs = ps.executeQuery();
			// 添加到集合中
			lu = new ArrayList<User>();
			// 遍历结果
			while (rs.next()) {
				// 给变量赋值
				User u = new User();
				u.setUid(rs.getInt("uid"));
				u.setUname(rs.getString("uname"));
				u.setPwd(rs.getString("pwd"));
				u.setSex(rs.getString("sex"));
				u.setAge(rs.getInt("age"));
				u.setBirth(rs.getString("birth"));
				lu.add(u);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 返回所有用户的集合
		return lu;
	}

	// 用户注册
	@Override
	public int userRegDao(User u) {
		// 声明对象
		Connection conn = null;
		PreparedStatement ps = null;
		int index = -1;
		// 加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 获取连接对象
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_in?useUnicode=true&characterEncoding=utf-8", "root", "123456");
			// 创建sql命令
			String sql = "insert into t_user value(default,?,?,?,?,?)";
			//创建sql命令对象
			ps = conn.prepareStatement(sql);
			//给占位符赋值
			ps.setString(1, u.getUname());
			ps.setString(2, u.getPwd());
			ps.setString(3, u.getSex());
			ps.setInt(4, u.getAge());
			ps.setString(5, u.getBirth());
			//执行
			index=ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return index;
	}

}
