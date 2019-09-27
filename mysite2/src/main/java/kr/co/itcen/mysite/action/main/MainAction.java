package kr.co.itcen.mysite.action.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class MainAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//쿠키굽기
		//Cookie Test
		int count=0;
		//cookie 읽기,쓰기전에 먼저 읽어야 한다.
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0) {
			for(Cookie cookie : cookies)
				if("visitCount".contentEquals(cookie.getName()))
					count=Integer.parseInt(cookie.getValue());
		}
		
		//cookie 쓰기
		count++;
		Cookie cookie = new Cookie("visitCount",String.valueOf(count));
		cookie.setMaxAge(24*60*60);//60초 *24시간*60분//1일
		cookie.setPath(request.getContextPath());//mysite2인 경우에만 
		response.addCookie(cookie);//쿠키는 반드시 응답에넣어야한다.
		
		WebUtils.forward(request, response, "WEB-INF/views/main/index.jsp");
		
	}

}
