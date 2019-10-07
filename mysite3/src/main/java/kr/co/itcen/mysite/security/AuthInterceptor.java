package kr.co.itcen.mysite.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.itcen.mysite.vo.UserVo;



public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler)
			throws Exception {
		//1.handler 종류(DefaultServletHttpRequestHandler, HandlerMethod),2번째꺼에 관심이 있다.
		if(handler instanceof HandlerMethod ==false) 
			return true;//DefaultServletHttpRequestHandler로 들어오는 경우
		//2.casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3.@Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);//어노테이션 읽어오기
		
		//4. @Auth가 없으면 class type에 있을 수 있으므로,//5. @Auth가 없으면 
		if(auth == null) {
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
			if(auth == null ) {
				//과제 : class type에서 @Auth가 있는지를 확인해 봐야 한다.
				return true;
			}
		}
		//6.@Auth가 class나 method에 붙어 있기 때문에 인증 여부를 체크한다.
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(session==null || authUser==null){
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		//7.Method의 @Auth의 Role가지고 오기
		String role = auth.value();
		
		//8.로그인한 사람이 USER일 경우,ADMIN일 경우에는 무조건 통과 되기떄문에 따로 적지 x
		//메소드의 @Auth의 Role이 "USER"인 경우은, 인증만 되어 있으면 모두 통과
		if("USER".equals(role)) {
			return true;
		}
		//9.메소드의 @Auth의 Role이 "ADMIN"인 경우//과제
		if ("ADMIN".equals(role)) {
			if ("ADMIN".equals(authUser.getRole())) {
				return true;
			}
		}
		response.sendRedirect(request.getContextPath() + "/");
		return false;
		
	}
	
}
