package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class LoginAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String email =request.getParameter("email");
		String password=request.getParameter("password");
		UserVo uservo = new UserDao().get(email,password);
		if(uservo==null) {
			request.setAttribute("result", "fail");//로그인을 실패했을떄 그 값을 저장
			WebUtils.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
			//종료를 해주어야한다,
			//밑 코드를 실행 시켜주면 안된다.
			return;//코드도 끊어 주어야한다.***
		}
		
		//인증 처리(session 처리)
		HttpSession session=request.getSession(true);//false를 넣으면 없을경우 null,true를 넣으면 없을경우 만들어서 리턴//default=false
		session.setAttribute("authUser", uservo);
		WebUtils.redirect(request, response, request.getContextPath());
	}

}
