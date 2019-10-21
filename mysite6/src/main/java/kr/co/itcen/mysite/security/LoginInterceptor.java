package kr.co.itcen.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		
		UserVo vo =new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		
		
		//어플리케이션 어디서든지 Spring Container(ApplicationContext)를 가져오는 방법
		//ApplicationContext appCtxt=WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		//UserService userService=appCtxt.getBean(UserService.class);
		UserVo authUser=userService.getUser(vo);
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//session처리를 해주면 된다.
		HttpSession session=request.getSession(true);
		System.out.println(authUser);
		session.setAttribute("authUser", authUser);
		
		response.sendRedirect(request.getContextPath()+"/");
		return false;
	}

}
