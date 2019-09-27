package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardDao boarddao;

	public List<BoardVo> getList(int start,int finish, String kwd) {
		return boarddao.selectList(start, finish, kwd);
	}

	public int getCount(String kwd) {
		return boarddao.Getcount(kwd);
	}

	public BoardVo get(Long no) {
		return boarddao.get(no);
	}

	public Boolean updatehit(BoardVo vo) {
		return boarddao.updateHit(vo);
	}
	public BoardVo getInfo(Long no) {
		return boarddao.getInfo(no);
	}
}
