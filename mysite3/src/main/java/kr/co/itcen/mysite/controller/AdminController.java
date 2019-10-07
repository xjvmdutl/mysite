package kr.co.itcen.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.itcen.mysite.security.Auth;

@Controller
@RequestMapping("/admin")//예측할수 없게 적어주어야한다
@Auth("ADMIN")
public class AdminController {
	//site라는 테이블을 만들어서 db을 내용을 읽어서 main페이지를 세팅해주어야한다,
	@RequestMapping("")
	public String main() {
		return "admin/main";
	}
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
