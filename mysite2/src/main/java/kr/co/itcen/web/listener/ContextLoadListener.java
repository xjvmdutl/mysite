package kr.co.itcen.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoadListener implements ServletContextListener {


    public void contextDestroyed(ServletContextEvent arg0)  { 
    	
    }

	
    public void contextInitialized(ServletContextEvent servletContextEvent)  { 
    	String contextConfigLocation=servletContextEvent.getServletContext().getInitParameter("contextConfigLocation");
    	System.out.println(contextConfigLocation);
    }
	
}
