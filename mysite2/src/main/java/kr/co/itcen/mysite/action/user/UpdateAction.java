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

public class UpdateAction implements Action {
	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
		UserVo user=new UserDao().get(authUser.getNo());
		Long no = user.getNo();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		UserVo vo = new UserVo();
		vo.setEmail(user.getEmail());
		vo.setGender(gender);
		vo.setName(name);
		vo.setNo(no);
		if("".equals(password))
			vo.setPassword(user.getPassword());
		else
			vo.setPassword(password);
		new UserDao().update(vo);
		
		
		UserVo vo2= new UserVo();
		vo2.setNo(vo.getNo());
		vo2.setName(vo.getName());
		session.setAttribute("authUser", vo2);
		WebUtils.redirect(request, response, request.getContextPath()+"/user?a=updateform");
	}

}
