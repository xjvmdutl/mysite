package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.GuestBookService;
import kr.co.itcen.mysite.vo.GuestBookVo;


@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@Autowired
	private GuestBookService guestbookservice;
	
	@RequestMapping({"","/list"})
	public String index(Model model){
		List<GuestBookVo> list=guestbookservice.getList();
		
		model.addAttribute("list",list);
		return "guestbook/index";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@ModelAttribute GuestBookVo vo) {
		guestbookservice.insert(vo);
		return "redirect:/guestbook";
	}
	@RequestMapping(value="/delete/{no}",method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no,Model model ) {
		model.addAttribute("no",no);
		return "guestbook/delete";
	}
	@RequestMapping(value="/delete/{no}",method=RequestMethod.POST)
	public String delete(GuestBookVo vo,@PathVariable("no") Long no,HttpSession session) {
		vo.setNo(no);
		guestbookservice.delete(vo);
		return "redirect:/guestbook";
	}
}
