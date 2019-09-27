package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.GuestBookVo;

public class GuestBookDao {

	public Boolean insert(GuestBookVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      try {
	         connection = getConnection();
	         // 3. Statment 객체 생성(받아오기)

	         // 4. SQL문 실행
	         String sql = "insert into guestbook values(null,?,?,?,now())";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, vo.getName());
	         pstmt.setString(2, vo.getPassword());
	         pstmt.setString(3, vo.getText());
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

	public List<GuestBookVo> selectAll() {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      List<GuestBookVo> result = new ArrayList<GuestBookVo>();
	      ResultSet rs =null;
	      try {  
	         connection = getConnection();
	         String sql = "select no,name,contents,date_format(reg_date,'%Y-%m-%d %h:%i:%s') from guestbook order by no desc";
	         pstmt = connection.prepareStatement(sql);
	         rs= pstmt.executeQuery();
	         while(rs.next()) {
	        	 Long no=rs.getLong(1);
	        	 String name = rs.getString(2);
	        	 String contents = rs.getString(3);
	        	 String date = rs.getString(4);
	        	 
	        	 GuestBookVo vo = new GuestBookVo();
	        	 vo.setNo(no);
	        	 vo.setName(name);
	        	 vo.setText(contents);
	        	 vo.setReg_date(date);
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
	public Boolean delete(GuestBookVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      
	      try {
	         connection = getConnection();
	         String sql = "delete from guestbook where no = ? and password = ?";
	         pstmt = connection.prepareStatement(sql);
	         
	         pstmt.setLong(1, vo.getNo());
	         pstmt.setString(2, vo.getPassword());
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
