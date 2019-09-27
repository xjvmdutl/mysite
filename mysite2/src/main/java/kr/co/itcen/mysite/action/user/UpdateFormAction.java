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

public class UpdateFormAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//접근제어가 필요//업데이트에서도 필요하다.
		HttpSession session=request.getSession();
		if(session ==null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;	
		}
		//////////////////////////////////
		UserVo vo=new UserDao().get(authUser.getNo());
		request.setAttribute("result", vo);
		WebUtils.forward(request, response, "/WEB-INF/views/user/updateform.jsp");
		
	}

}
