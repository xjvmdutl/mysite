package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kr.co.itcen.mysite.vo.UserVo;

public class UserDao {
	public Boolean insert(UserVo vo) {
		Connection connection = null;
	      PreparedStatement pstmt = null;
	      Statement stmt = null;
	      Boolean result = false;
	      ResultSet rs =null;
	      try {
	         connection = getConnection();
	         // 3. Statment 객체 생성(받아오기)

	         // 4. SQL문 실행
	         String sql = "insert into user values(null,?,?,?,?,now())";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, vo.getName());
	         pstmt.setString(2, vo.getEmail());
	         pstmt.setString(3, vo.getPassword());
	         pstmt.setString(4, vo.getGender());
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
	public UserVo get(Long no1) {
		UserVo result=null;
		  Connection connection = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs =null;
	      try {
	         connection = getConnection();
	         String sql = "select no,name,email,password,gender from user where no =?";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setLong(1, no1);
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	        	Long no=rs.getLong(1);
	        	String name = rs.getString(2);
	        	String email = rs.getString(3);
	        	String password=rs.getString(4);
	        	String gender = rs.getString(5);
	        	result = new UserVo();
	        	result.setNo(no);
	        	result.setName(name);
	        	result.setEmail(email);
	        	result.setGender(gender);
	        	result.setPassword(password);
	         }
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
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
	public UserVo get(String email, String password) {
		UserVo result=null;
		  Connection connection = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs =null;
	      try {
	         connection = getConnection();
	         String sql = "select no,name from user where email =? and password=?";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, email);
	         pstmt.setString(2, password);
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	        	Long no=rs.getLong(1);
	        	String name = rs.getString(2);
	        	result = new UserVo();
	        	result.setNo(no);
	        	result.setName(name);
	         }
	      }catch (SQLException e) {
	         System.out.println("error : " + e);
	      } finally {
	         try{
	        	if(pstmt != null) {
	               pstmt.close();
	            }if(rs!=null) {
	            	rs.close();
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
	public Boolean update(UserVo vo) {
		  Boolean result=null;
		  Connection connection = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs =null;
	      try {
	         connection = getConnection();
	         String sql = "update user set name=?, password=?, gender=? where no=?";
	         pstmt = connection.prepareStatement(sql);
	         pstmt.setString(1, vo.getName());
	         pstmt.setString(2, vo.getPassword());
	         pstmt.setString(3, vo.getGender());
	         pstmt.setLong(4, vo.getNo());
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
