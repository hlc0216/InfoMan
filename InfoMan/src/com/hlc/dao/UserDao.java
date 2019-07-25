package com.hlc.dao;

import java.util.List;

import projo.User;

public interface UserDao {
	/**
	 * 根据用户名和密码查询用户信息
	 * 
	 * @param uname
	 * @param pwd
	 * @return
	 */

	User checkUserLoginDao(String uname, String pwd);

	/**
	 * 根据新密码和uid修改用户密码
	 * 
	 * @param newPwd
	 * @param uid
	 * @return
	 */
	int userChangePwdDao(String newPwd, int uid);

	/**
	 * 显示所有用户的数据
	 * 
	 * @return
	 */
	List<User> userShowDao();

	/**
	 * 用户注册
	 * 
	 * @param u
	 * @return
	 */
	int userRegDao(User u);
}
