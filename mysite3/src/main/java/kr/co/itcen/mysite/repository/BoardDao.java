package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	@Autowired
	private DataSource datasource;
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
	public List<BoardVo> selectList(String kwd) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      List<BoardVo> result = new ArrayList<BoardVo>();
	      ResultSet rs =null;
	      try {  
	         connection = datasource.getConnection();
	         String sql = "select board.no,board.title,user.name,board.hit,date_format(board.reg_date,'%Y-%m-%d %h:%i:%s'),board.status,board.depth from board,user where user.no=board.user_no and (board.status='insert' or board.status='modify') and (board.title like ? or board.contents like ?) order by board.g_no desc, board.o_no asc";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, kwd);
	         pstmt.setString(2, kwd);
	
	         rs= pstmt.executeQuery();
	         while(rs.next()) {
	        	 Long no=rs.getLong(1);
	        	 String title = rs.getString(2);
	        	 String username = rs.getString(3);
	        	 Long hit=rs.getLong(4);
	        	 String reg_date=rs.getString(5);
	        	 String status = rs.getString(6);
	        	 Long depth = rs.getLong(7);
	        	 BoardVo vo = new BoardVo();
	        	 vo.setNo(no);
	        	 vo.setTitle(title);
	        	 vo.setName(username);
	        	 vo.setHit(hit);
	        	 vo.setReg_date(reg_date);
	        	 vo.setStatus(status);
	        	 vo.setDepth(depth);
	        	 result.add(vo);
	       
	         }
	         //result = (count==1);
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      }finally {
	         try {
	        	if(rs!=null) {
	        		rs.close();
	        	}
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (connection != null) {
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
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
