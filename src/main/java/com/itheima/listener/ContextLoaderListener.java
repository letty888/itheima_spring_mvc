package com.itheima.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhangHuan
 * @date: 2020/05/10/20:39
 * @Description: 这个类监听整个web项目,只要web项目启动,就会自动加载这个类中的代码
 */
public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //1.获取应用上下文
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.将应用上下文保存到servletContext域中
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("app", applicationContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
