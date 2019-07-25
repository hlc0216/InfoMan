package com.hlc.service;

import java.util.List;

import projo.User;

public interface UserService {
	/**
	 * 校验用户登陆
	 * 
	 * @param uname
	 * @param pwd
	 * @return
	 */
	User checkUserLoginService(String uname, String pwd);

	/**
	 * 根据用户id和newPwd修改用户密码
	 * 
	 * @param newPwd
	 * @param uid
	 * @return
	 */
	int userChangePwdService(String newPwd, int uid);

	/**
	 * 显示所有用户的信息
	 * 
	 * @return
	 */
	List<User> userShowService();

	int userRegService(User u);

}
