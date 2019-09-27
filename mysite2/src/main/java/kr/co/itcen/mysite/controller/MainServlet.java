package kr.co.itcen.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.action.main.MainActionFactory;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		String configPath=this.getServletConfig().getInitParameter("config");//서블릿 파일에서도 초기 설정 파일 경로를 읽어올수 이싿. 
		System.out.println(configPath);
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//서블릿에서 필터값 받기
		//request.getServletContext().getInitParameter("contextConfigLocation");
		String actionName = request.getParameter("a");
		ActionFactory ac=new MainActionFactory();
		Action action=ac.getAction(actionName);
		
		action.excute(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
