package kr.co.itcen.mysite.action.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.GuestBookDao;
import kr.co.itcen.mysite.vo.GuestBookVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		GuestBookDao dao=new GuestBookDao();
		List<GuestBookVo> list = dao.selectAll();
		request.setAttribute("list", list);
		WebUtils.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
	}

}
