package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;



@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlsession;

	public Boolean insertGroup(BoardVo vo) {
		int count = sqlsession.insert("board.insertbygroup",vo);
		return count==1;
	}
	public List<BoardVo> selectList(int start,int finish,String kwd) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("finish", finish);
		map.put("kwd", kwd);
		
		List<BoardVo> result=sqlsession.selectList("board.getlist",map); 
		return result;
	}
	public int Getcount(String kwd) {
		int count = sqlsession.selectOne("board.count",kwd);
	    return count;
	}
	public BoardVo get(Long no) {
		BoardVo vo= sqlsession.selectOne("board.get",no);
	    return vo;
	}
	public BoardVo getInfo(Long no) {
		BoardVo vo = sqlsession.selectOne("board.getAllInfo",no);
	    return vo;
	}
	
	public Boolean modify(BoardVo vo) {
		int count = sqlsession.update("updatebytitlebycontentsbystatus",vo);
		return count==1;
	}
	
	public Boolean updatestatus(Long no) {
		int count =sqlsession.update("updatebydel",no);
		return count==1;
	}

	public Boolean updateRequest(BoardVo vo) {
		vo.setO_no(vo.getO_no()+1);
		vo.setDepth(vo.getDepth()+1);
		int count = sqlsession.update("updatebyrequest",vo);
		return count>=0;
	}
	public Boolean insertRequest(BoardVo vo) {
		vo.setO_no(vo.getO_no()+1);
		vo.setDepth(vo.getDepth()+1);
		int count = sqlsession.insert("insertbyrequest",vo);
		return count==1;
	}
	public Boolean updateHit(BoardVo vo) {
		int count=sqlsession.update("board.updatebyhit",vo.getNo());
		return count==1;
	}
	

	
	
}
