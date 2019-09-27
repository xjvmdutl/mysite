package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int pagelimit=5;
		int showpage=1;
		int count;
		String kwd = "";
		if(request.getParameter("kwd")!=null)
			kwd=request.getParameter("kwd");
		
		if(request.getParameter("page")!=null) {
			showpage=Integer.parseInt(request.getParameter("page"));
		}
		int pagenumber=((showpage-1)/5)*5;

		List<BoardVo> list=new BoardDao().selectList((showpage-1)*pagelimit,pagelimit,kwd);
		count=new BoardDao().Getcount(kwd);
		
		int index=count-((showpage-1)*5);
		System.out.println(count);
		request.setAttribute("showpage", showpage);
		request.setAttribute("pagenumber", pagenumber);
		request.setAttribute("list", list);
		request.setAttribute("index", index);
		request.setAttribute("count", count);
		request.setAttribute("kwd", kwd);
			
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
