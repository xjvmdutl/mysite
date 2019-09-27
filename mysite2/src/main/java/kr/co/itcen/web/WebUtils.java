package kr.co.itcen.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {
	public static void forward(HttpServletRequest request, HttpServletResponse response,String path) throws IOException,ServletException {
		RequestDispatcher rd=request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
	public static void redirect(HttpServletRequest request,HttpServletResponse response,String url) throws IOException,ServletException{
		response.sendRedirect(url);
	}
}
