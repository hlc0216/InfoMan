package com.hlc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.hlc.dao.UserDao;
import com.hlc.dao.impl.UserDaoImpl;
import com.hlc.service.UserService;

import projo.User;

public class UserServiceImpl implements UserService {
	// 声明日志对象
	Logger logger = Logger.getLogger(UserServiceImpl.class);
	// 声明Dao层对象
	UserDao ud = new UserDaoImpl();

	// 用户登陆
	@Override
	public User checkUserLoginService(String uname, String pwd) {
		// 打印日志
		logger.debug(uname + "发起登陆请求");
		User u = ud.checkUserLoginDao(uname, pwd);
		// 判断
		if (u != null)
			logger.debug(uname + "登陆成功");
		else
			logger.debug(uname + "登陆失败");
		return u;
	}

	public int userChangePwdService(String newPwd, int uid) {
		// 修改用户密码
		logger.debug(uid+"发起了修改密码请求");
		int index = ud.userChangePwdDao(newPwd,uid);
		if(index >0){
			logger.debug(uid+"密码修改成功");
			
		}else{
			logger.debug(uid+"密码修改失败");
		}
		return index;
	}

	@Override
	public List<User> userShowService() {
		// 显示所有用户的信息
		List <User> lu =ud.userShowDao();
		logger.debug("显示所有用户的信息"+lu);
		return lu;
	}

	@Override
	public int userRegService(User u) {

		return ud.userRegDao(u);
	}

}
