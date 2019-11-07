package kr.co.itcen.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import kr.co.itcen.mysite.dto.JSONResult;
import kr.co.itcen.mysite.service.GuestBookService;
import kr.co.itcen.mysite.vo.GuestBookVo;

@RestController("geustbookApiController")//스프링에서 지원하는 REST API
//@Controller("geustbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	@Autowired
	private GuestBookService guestbookservice;

	//@ResponseBody//쓰지 않아도 된다
	//@RequestMapping(value = "/add",method = RequestMethod.POST)
	@PostMapping(value = "/add")
	public JSONResult add(@RequestBody GuestBookVo vo) { 
		guestbookservice.insert(vo);
		vo.setPassword("");//패스워드는 보안상 안좋기 떄문에 안오게끔 한다.
		return JSONResult.success(vo);
	}
	//@ResponseBody
	//@RequestMapping(value = "list/{no}",method = RequestMethod.GET)
	@GetMapping(value = "/list/{no}")
	public JSONResult list(@PathVariable("no") Long startNo) {
		List<GuestBookVo> list=guestbookservice.getList(startNo);
		return JSONResult.success(list);
	}
	
	//@ResponseBody
	@DeleteMapping(value = "/{no}")
	public JSONResult delete(@PathVariable("no") Long no,@RequestParam String password) {
		Boolean result = guestbookservice.delete(no,password);
		return JSONResult.success(result ? no : -1);//지워지면 no주고, 아니면 -1준다//내가 약속을 하는것
	}
}
