package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ViewFormAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BoardVo vo =new BoardVo();
		Long no=Long.parseLong(request.getParameter("no"));
		vo=new BoardDao().select(no);
		request.setAttribute("vo",vo );
	
		new BoardDao().updateHit(vo);
		request.setAttribute("noUser", false);
		HttpSession session=request.getSession();
		if(session ==null) {
			WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
			return;
		}
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
			return;	
		}
		if(authUser.getNo()==vo.getUser_no()) {
			request.setAttribute("isMe", true);
		}
		UserVo vo2 = new UserDao().get(authUser.getNo());
		request.setAttribute("me", vo2.getName());
		request.setAttribute("noUser", true);
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
