package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;


public class WriteAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session=request.getSession();
		if(session ==null) {
			WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
			return;
		}
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
			return;	
		}
		String title=request.getParameter("title");
		String contents=request.getParameter("content");
		System.out.println(request.getParameter("o_no"));
		System.out.println(request.getParameter("depth"));
		Long g_no=null;
		Long o_no=null;
		Long depth=null;
		if(!("".equals(request.getParameter("g_no")))) {
		g_no=Long.parseLong(request.getParameter("g_no"));
		}
		if(!("".equals(request.getParameter("o_no")))) {
			o_no=Long.parseLong(request.getParameter("o_no"));
		}
		if(!("".equals(request.getParameter("depth")))) {
			depth=Long.parseLong(request.getParameter("depth"));
		}
		
		BoardVo vo=new BoardVo();
		vo.setContents(contents);
		vo.setTitle(title);
		vo.setUser_no(authUser.getNo());
		if(o_no==null || depth==null) {
			new BoardDao().insert(vo);
			WebUtils.redirect(request, response, request.getContextPath()+"/board");
			return;
		}
		vo.setG_no(g_no);
		vo.setDepth(depth+1);
		vo.setO_no(o_no+1);
		new BoardDao().updateRequest(vo);
		new BoardDao().insertRequest(vo);
		WebUtils.redirect(request, response, request.getContextPath()+"/board");
	}

}
