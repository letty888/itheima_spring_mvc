package com.itheima.listener;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhangHuan
 * @date: 2020/05/10/20:44
 * @Description:
 */
public class WebApplicationContextUtils {

    public static ApplicationContext getWebApplicationContext(ServletContext servletContext) {
        ApplicationContext app = (ApplicationContext) servletContext.getAttribute("app");
        return app;
    }
}
