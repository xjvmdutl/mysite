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
	public Boolean insert(BoardVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      try {
	         connection = datasource.getConnection();
	         String sql = "insert into board values(null,?,?,0,now(),(select ifnull(max(b.g_no)+1,1) from board as b),1,0,'insert',?)";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, vo.getTitle());
	         pstmt.setString(2, vo.getContents());
	         pstmt.setLong(3, vo.getUser_no());
	         int count = pstmt.executeUpdate();
	         result = (count==1);
	         stmt=connection.createStatement();
	         rs =stmt.executeQuery("select last_insert_id()");//본래는 메소드 한번에 쿼리 하나씩이지만 이건 특이한 케이스이다.
	         if(rs.next()) {
	        	Long no=rs.getLong(1);
	         	vo.setNo(no);
	         }
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
	            }if(stmt!=null) {
	            	stmt.close();
	            }if(connection != null){
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
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
	public Boolean update(BoardVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      try {
	         connection = datasource.getConnection();
	        
	         String sql = "update board set title=?,contents=?,status='modify' where no=?";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, vo.getTitle());
	         pstmt.setString(2, vo.getContents());
	         pstmt.setLong(3, vo.getNo());
	         int count = pstmt.executeUpdate();
	         result = (count==1);
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
	            }if(stmt!=null) {
	            	stmt.close();
	            }if(connection != null){
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
	}
	
	public Boolean updatestatus(Long no) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      
	      try {
	         connection = datasource.getConnection();
	         String sql = "update board set status='delete' where no=?";
	         pstmt = connection.prepareStatement(sql);
	         
	         pstmt.setLong(1, no);
	         int count = pstmt.executeUpdate();
	         result = (count==1);
	         
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
	            }if(stmt!=null) {
	            	stmt.close();
	            }if(connection != null){
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
	}

	public Boolean updateRequest(BoardVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      
	      try {
	         connection = datasource.getConnection();
	         String sql = "update board set o_no=o_no+1 where g_no = ? and o_no >= ?";
	         pstmt = connection.prepareStatement(sql);
	         
	         pstmt.setLong(1, vo.getG_no());
	         pstmt.setLong(2, vo.getO_no());
	         int count = pstmt.executeUpdate();
	         result = (count==1);
	         
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
	            }if(stmt!=null) {
	            	stmt.close();
	            }if(connection != null){
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
	}
	public Boolean insertRequest(BoardVo vo) {
		Connection connection = null;
	    PreparedStatement pstmt = null;
	    Statement stmt = null;
	    Boolean result = false;
	    ResultSet rs =null;
	      try {
	         connection = datasource.getConnection();
	         String sql = "insert into board values(null,?,?,0,now(),?,?,?,'insert',?)";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, vo.getTitle());
	         pstmt.setString(2, vo.getContents());
	         pstmt.setLong(3, vo.getG_no());
	         pstmt.setLong(4,vo.getO_no());
	         pstmt.setLong(5, vo.getDepth());
	         pstmt.setLong(6, vo.getUser_no());
	         int count = pstmt.executeUpdate();
	         result = (count==1);
	         stmt=connection.createStatement();
	         rs =stmt.executeQuery("select last_insert_id()");//본래는 메소드 한번에 쿼리 하나씩이지만 이건 특이한 케이스이다.
	         if(rs.next()) {
	        	Long no=rs.getLong(1);
	         	vo.setNo(no);
	         }
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
	            }if(stmt!=null) {
	            	stmt.close();
	            }if(connection != null){
	               connection.close();
	            }
	         } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	         }
	      }
	      return result;
	}
	public Boolean updateHit(BoardVo vo) {
		int count=sqlsession.update("board.updatebyhit",vo.getNo());
		return count==1;
	}
	
	
}
