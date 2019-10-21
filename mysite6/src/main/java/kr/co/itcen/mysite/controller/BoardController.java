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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.service.PictureService;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.PictureVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardservice;
	@Autowired
	private PictureService pictureservice;
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
		List<PictureVo> list = pictureservice.select(no);
		model.addAttribute("list",list);
		model.addAttribute("vo",vo);
		return "board/view";
	}
	@RequestMapping(value="/modify/{no}",method=RequestMethod.GET)
	public String modify(@PathVariable("no") Long no,HttpSession session,Model model) {
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		if(authUser==null)
			return "/board";
		BoardVo vo=boardservice.getInfo(no);
		model.addAttribute("vo",vo);
		return "board/modify";
	}
	@RequestMapping(value="/modify/{no}",method = RequestMethod.POST)
	public String modify(@PathVariable("no") Long no,@ModelAttribute BoardVo vo) {
		vo.setNo(no);
		System.out.print(vo);
		boardservice.modify(vo);
		return "redirect:/board/view/"+no;
	}
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String write(HttpSession session) {
		if(session != null && session.getAttribute("authUser") !=null)
			return "/board/write";
		return "redirect:/board";
	}
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo,HttpSession session) {
		UserVo authUser=(UserVo)session.getAttribute("authUser");
		vo.setUser_no(authUser.getNo());
		if(vo.getG_no() == null || vo.getO_no()==null || vo.getDepth() == null){
			boardservice.insertGroup(vo);
			
		}else {
			boardservice.updateRequest(vo);
			boardservice.insertRequest(vo);
		}
		return "redirect:/board";
	}
	@RequestMapping(value="/request/{no}",method=RequestMethod.GET)
	public String request(@PathVariable("no") Long no,Model model,HttpSession session) {
		if(session == null || (session.getAttribute("authUser") ==null))
			return "redirect:/board";
		BoardVo vo = boardservice.getInfo(no);
		model.addAttribute("vo",vo);
		return "board/write";
	}
	@RequestMapping(value="delete/{no}",method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no){
		boardservice.updatestatus(no);
		return "redirect:/board";
	}
	@RequestMapping("/upload/{no}")



	public String upload(@RequestParam(value = "file",required = false) MultipartFile multipartFile,
			@PathVariable("no") Long no) {
		PictureVo vo = new PictureVo();
		vo.setBoard_no(no);
		vo=pictureservice.store(multipartFile,vo);
		return "redirect:/board/view/"+no;
	}
}
