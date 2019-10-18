package kr.co.itcen.mysite.initializer;

import javax.servlet.Filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import kr.co.itcen.mysite.config.AppConfig;
import kr.co.itcen.mysite.config.WebConfig;


//어노테이션을 달지 않아도된다 - > fullScan하기 때문에
public class MysiteApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		//Root Application Context
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		//Web Application Context
		return new Class<?>[] {WebConfig.class};
	}

	@Override//URL매핑
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override//filter
	protected Filter[] getServletFilters() {
		return new Filter[] {new CharacterEncodingFilter("UTF-8",true)};//true는 필터를 무조건 설정하겠다는뜻
	}

	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {//GlobalException이 발생하지 않다면 에러가 뜨게 해줌
		DispatcherServlet dispatcherServlet = (DispatcherServlet)super.createDispatcherServlet(servletAppContext);
		//Exception Handler가 발견되지 않으면 Error!!
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return dispatcherServlet;
	}
	
}
