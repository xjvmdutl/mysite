package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.GuestBookVo;



public class BoardDao {
	public Boolean insert(BoardVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      try {
	         connection = getConnection();
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
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      List<BoardVo> result = new ArrayList<BoardVo>();
	      ResultSet rs =null;
	      try {  
	         connection = getConnection();
	         String sql = "select board.no,board.title,user.name,board.hit,date_format(board.reg_date,'%Y-%m-%d %h:%i:%s'),board.depth,board.status,board.user_no from board,user where user.no=board.user_no and (board.title like ? or board.contents like ?) order by board.g_no desc, board.o_no asc limit ?,?";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, "%"+kwd+"%");
	         pstmt.setString(2, "%"+kwd+"%");
	         
	         pstmt.setInt(3, start);
	         pstmt.setInt(4, finish);
	         rs= pstmt.executeQuery();
	         while(rs.next()) {
	        	 Long no=rs.getLong(1);
	        	 String title = rs.getString(2);
	        	 String username = rs.getString(3);
	        	 Long hit=rs.getLong(4);
	        	 String reg_date=rs.getString(5);
	        	 Long depth = rs.getLong(6);
	        	 String status=rs.getString(7);
	        	 Long user_no=rs.getLong(8);
	        	 BoardVo vo = new BoardVo();
	        	 vo.setNo(no);
	        	 vo.setTitle(title);
	        	 vo.setName(username);
	        	 vo.setHit(hit);
	        	 vo.setReg_date(reg_date);
	        	 vo.setDepth(depth);
	        	 vo.setStatus(status);
	        	 vo.setUser_no(user_no);
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
	public int Getcount(String kwd) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      int result = 0;
	      ResultSet rs =null;
	      try {  
	         connection = getConnection();
	         String sql = "select count(*) from board where title like ? or contents like ?";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, "%"+kwd+"%");
	         pstmt.setString(2, "%"+kwd+"%");
	         rs= pstmt.executeQuery();
	         if(rs.next()) {
	        	 result=rs.getInt(1);
	         }
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
	public BoardVo select(Long paramno) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      BoardVo result = new BoardVo();
	      ResultSet rs =null;
	      try {  
	         connection = getConnection();
	         String sql = "select no,title,contents,user_no,hit from board where no=?";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setLong(1, paramno);
	         rs= pstmt.executeQuery();
	         if(rs.next()) {
	        	 Long no = rs.getLong(1);
	        	 String title = rs.getString(2);
	        	 String contents= rs.getString(3);
	        	 Long userno=rs.getLong(4);
	        	 Long hit = rs.getLong(5);
	        	 result.setNo(no);
	        	 result.setTitle(title);
	        	 result.setContents(contents);
	        	 result.setUser_no(userno);
	        	 result.setHit(hit);
	         }
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
	public BoardVo selectAll(BoardVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      BoardVo result = new BoardVo();
	      ResultSet rs =null;
	      try {  
	         connection = getConnection();
	         String sql = "select no,title,contents,hit,date_format(reg_date,'%Y-%m-%d %h:%i:%s'),g_no,o_no,depth,status,user_no from board where no=?";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setLong(1, vo.getNo());
	         rs= pstmt.executeQuery();
	         if(rs.next()) {
	        	 Long no = rs.getLong(1);
	        	 String title = rs.getString(2);
	        	 String contents= rs.getString(3);
	        	 Long hit = rs.getLong(4);
	        	 String reg_date = rs.getString(5);
	        	 Long g_no=rs.getLong(6);
	        	 Long o_no = rs.getLong(7);
	        	 Long depth = rs.getLong(8);
	        	 String status = rs.getString(9);
	        	 Long userno=rs.getLong(10);
	        	 result.setNo(no);
	        	 result.setTitle(title);
	        	 result.setContents(contents);
	        	 result.setUser_no(userno);
	        	 result.setG_no(g_no);
	        	 result.setO_no(o_no);
	        	 result.setDepth(depth);
	        	 result.setReg_date(reg_date);
	        	 result.setHit(hit);
	        	 result.setStatus(status);
	         }
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
	public List<BoardVo> selectList(String kwd) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      List<BoardVo> result = new ArrayList<BoardVo>();
	      ResultSet rs =null;
	      try {  
	         connection = getConnection();
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
	         connection = getConnection();
	        
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
	         connection = getConnection();
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
	
	private Connection getConnection() throws SQLException{//카피페이스트를 할 경우에는 반드시 중복된 코드를 제거해 주는 메소드가 있어야한다.
		Connection connection=null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
	
	        // 2. 연결하기
	        String url = "jdbc:mariadb://192.168.1.86:3306/webdb?characterEncoding=utf8";
	        // 아래는 네트워크 코드
	        connection = DriverManager.getConnection(url, "webdb", "webdb");
	        // 3. Statment 객체 생성(받아오기)
		}catch (ClassNotFoundException e) {
	         System.out.println("Fail to Loading Driver :" + e);
	    }
		return connection;
	}
	public Boolean updateRequest(BoardVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      
	      try {
	         connection = getConnection();
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
	         connection = getConnection();
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
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      
	      try {
	         connection = getConnection();
	         String sql = "update board set hit=hit+1 where no=?";
	         pstmt = connection.prepareStatement(sql);
	         
	         pstmt.setLong(1, vo.getNo());
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
}
