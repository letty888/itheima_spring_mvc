servlet调用service层一般流程:
在servlet中:
   ` //1.从应用上下文中获取service层
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    UserService userService = applicationContext.getBean(UserService.class);`
上面这句代码存在以下问题:
    1).servlet每次调用service层都需要创建一个应用上下文-->消耗资源.
        解决: 自定义一个实现了ServletContextListener的监听器,这个监听器就会自动监听整个web项目的启动,那么我们可以在这个监听器中获取一个应用上下文,然后获取到想要的service层.并且保存到最大域ServletContext中.这样的话别的servlet就可以在自己内部通过servletcontext获取想要的service层.
               ` //1.获取应用上下文
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
                //2.将应用上下文保存到servletContext域中
                ServletContext servletContext = servletContextEvent.getServletContext();
                servletContext.setAttribute("app", applicationContext);`
    2).如果采用1)中的思路,那么就需要在servlet中存在这样的代码:
       ` ServletContext servletContext = this.getServletContext();
        ApplicationContext applicationContext = servletContext.getAttribute("app");`
        然后再通过applicationContext获取想要的service即可.
        但是这样耦合太高,所以我们自定义一个工具类,传入ServletContext参数,返回ApplicationContext.
    3).自定义工具类WebApplicationContextUtils:
        `public static ApplicationContext getWebApplicationContext(ServletContext servletContext) {
                ApplicationContext app = (ApplicationContext) servletContext.getAttribute("app");
                return app;
            }`
    4).这样的话,我们就可以在servlet层通过如下代码来获取到想要的service层:
        `` `@Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                this.doPost(req, resp);
            }``
        
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
            //1.从应用上下文中获取service层
            //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
            ServletContext servletContext = this.getServletContext();
            ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            UserService userService = app.getBean(UserService.class);
            userService.save();
        }`
        
总结:
    1.实现了ServletContextListener的监听器会监听整个web项目的启动,在这个监听器中我们获取到应用上下文然后保存到servletContext域中;
    2.自定义工具类WebApplicationContextUtils,在这个工具类中,传入ServletContext,返回应用上下文ApplicationContext.
    3.完成了上面两步,还需要在web.xml中做如下配置:
           `<!--全局初始化参数-->
            <context-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>classpath:applicationContext.xml</param-value>
            </context-param>
            <!--配置监听器-->
            <listener>
                <listener-class>com.itheima.listener.ContextLoaderListener</listener-class>
            </listener>`
    4.在servlet中采用如下方式就可以获取到想要的service层:
        `ServletContext servletContext = this.getServletContext();
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        UserService userService = app.getBean(UserService.class);
        userService.save();`
    5.spring框架就基于这样的思想演化出了springmvc.