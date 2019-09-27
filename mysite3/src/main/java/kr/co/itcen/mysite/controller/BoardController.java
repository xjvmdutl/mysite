package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardservice;
	@RequestMapping({"","list"})
	public String index(@RequestParam(value="page",required = false, defaultValue = "1") int page, @RequestParam(value="kwd",required = false,defaultValue="") String kwd,Model model) {
		List<BoardVo> list= boardservice.getList((page-1)*5,5,kwd);
		System.out.println("kwd: " + kwd);
		int count = boardservice.getCount(kwd);
		int index=count-((page-1)*5);
		int pagenumber=((page-1)/5)*5;
		model.addAttribute("list",list);
		model.addAttribute("pagenumber",pagenumber);
		model.addAttribute("index",index);
		model.addAttribute("count",count);
		model.addAttribute("kwd", kwd);
		model.addAttribute("showpage",page);
		return "board/list";
	}
	@RequestMapping(value={"","list"},method=RequestMethod.POST)
	public String index(@RequestParam(value="kwd",required = false,defaultValue = "") String kwd) {
		return "redirect:/board?kwd="+kwd;
	}
	@RequestMapping(value="/view/{no}",method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no,Model model,HttpSession session) {
		BoardVo vo=boardservice.get(no);
		boardservice.updatehit(vo);
		model.addAttribute("vo",vo);
		UserVo uservo=(UserVo) session.getAttribute("authUser");
		return "board/view";
	}
	@RequestMapping(value="/modify",method=RequestMethod.GET)
	public String write(@PathVariable("no") Long no,HttpSession session,Model model) {
		BoardVo vo=boardservice.getInfo(no);
		model.addAttribute("vo",vo);
		return "board/modify";
	}
}
