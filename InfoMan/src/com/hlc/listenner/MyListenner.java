package com.hlc.listenner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class MyListenner implements HttpSessionListener,ServletContextListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		//获取ServletContezt对象
		ServletContext sc = se.getSession().getServletContext();
		//获取在线统计人数的变量
		int count= (int) sc.getAttribute("count");
		//存储
		sc.setAttribute("count", ++count);//session在创建时自增
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//获取ServletContezt对象
		ServletContext sc = se.getSession().getServletContext();
		//获取在线统计人数的变量
		int count= (int) sc.getAttribute("count");
		//存储
		sc.setAttribute("count", --count);//session在销毁时自减
	}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//获取application
		ServletContext sc = sce.getServletContext();
		//在applcation中存储变量用来统计在线人数
		sc.setAttribute("count", 0);
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
	
	}
