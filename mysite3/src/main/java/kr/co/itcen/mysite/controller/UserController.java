package kr.co.itcen.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	@RequestMapping(value="/login", method=RequestMethod.GET)
	private String login() {
		return "user/login";
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	private String login(UserVo vo,HttpSession session,Model model) {
		UserVo userVo=userService.getUser(vo);
		if(userVo == null) {
			model.addAttribute("result","fail");
			return "user/login";
		}
		//로그인 처리
		session.setAttribute("authUser", userVo);
		return "redirect:/";
	}
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	private String logout(HttpSession session) {
		//접근제어(권한 없는사람이 접근,ACL)
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		if(authUser!=null) {
			session.removeAttribute("authUser");
			session.invalidate();
		}
		return "redirect:/";
	}
	@RequestMapping(value="/update", method=RequestMethod.GET)
	private String update(HttpSession session,Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/";
		}
		UserVo vo = userService.get(authUser.getNo());
		model.addAttribute("result",vo);
		return "user/update";
	}
	@RequestMapping(value="/update", method=RequestMethod.POST)
	private String update(UserVo vo,HttpSession session,Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null){
			return "redirect:/";
		}
		UserVo v=userService.get(authUser.getNo());
		vo.setNo(v.getNo());
		userService.update(vo);
		authUser.setName(vo.getName());
		session.setAttribute("authUser", authUser);
		return "redirect:/";
	}
//	@ExceptionHandler(UserDaoException.class)//Excpetion핸들러로 작동
//	public String handlerException() {
//		return "error/exception";
//	}모든 컨트롤러에 해주어야하기 떄문에 불편
	
}
