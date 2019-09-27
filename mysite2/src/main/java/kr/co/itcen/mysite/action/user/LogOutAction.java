package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class LogOutAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session=request.getSession();
		if(session !=null && session.getAttribute("authUser")!=null) {//로그인을 않했을떄에도 sesion객체가 x
			session.removeAttribute("authUser");
			session.invalidate();//세션아이디를 새로 발급 받아라
		}
		WebUtils.redirect(request, response, request.getContextPath());
	}

}
