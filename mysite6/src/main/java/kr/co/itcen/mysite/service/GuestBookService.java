package kr.co.itcen.mysite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.GuestBookDao;
import kr.co.itcen.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookDao guestbookdao;

	public List<GuestBookVo> getList() {
		List<GuestBookVo> list= guestbookdao.selectAll();
		return list;
	}
	public void insert(GuestBookVo vo) {
		guestbookdao.insert(vo);
	}
	public void delete(GuestBookVo vo) {
		guestbookdao.delete(vo);
	}
	
}
