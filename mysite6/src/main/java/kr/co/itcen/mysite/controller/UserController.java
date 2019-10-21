package kr.co.itcen.mysite.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.security.Auth;
import kr.co.itcen.mysite.security.AuthUser;

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
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {//(valid)에 에러가 있다면 실행
//			List<ObjectError> list = result.getAllErrors();//
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			model.addAllAttributes(result.getModel());//getModel에는 맵을 리턴을 해주고 키수대로 자동으로 모델에 세팅
			
			return "user/join";
		}
			
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(UserVo vo,HttpSession session,Model model) {
//		System.out.println(userService);
//		
//		UserVo userVo= userService.getUser(vo);
//		if(userVo == null) {
//			model.addAttribute("result","fail");
//			return "user/login";
//		}
//		//로그인 처리
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}
//	@RequestMapping(value="/logout", method=RequestMethod.GET)
//	public String logout(HttpSession session) {
//		//접근제어(권한 없는사람이 접근,ACL)
//		UserVo authUser=(UserVo)session.getAttribute("authUser");
//		if(authUser!=null) {
//			session.removeAttribute("authUser");
//			session.invalidate();
//		}
//		return "redirect:/";
//	}
//	@Auth(role=Role.USER)
//	@Auth("USER")
	
	@Auth("USER")
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(
			Model model,
			@AuthUser UserVo authUser//-> 이걸 사용해야지만이 HttpSession을 뺄수가 있다.
			//HttpSession session
			) {
		//UserVo authUser= (UserVo)session.getAttribute("authUser");
		Long no = authUser.getNo();
		System.out.println(authUser);
		UserVo vo=userService.get(no);
		
		model.addAttribute("userVo",vo);
		return "user/update";
	}
	
//	@Auth("USER")
	@Auth("USER")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute @Valid UserVo vo,
			BindingResult result,
			@AuthUser UserVo authUser,
			Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/update";
		}
		
		vo.setNo(authUser.getNo());
		userService.update(vo);
		authUser.setName(vo.getName());
		return "redirect:/";
	}
//	@ExceptionHandler(UserDaoException.class)//Excpetion핸들러로 작동
//	public String handlerException() {
//		return "error/exception";
//	}모든 컨트롤러에 해주어야하기 떄문에 불편
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public void auth() {
		
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout() {
		
	}
}
