package kr.co.itcen.mysite.repository;


import java.util.HashMap;
import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;
	//@Transactional//
	public Boolean insert(UserVo vo) throws UserDaoException{
//		tm.begin(); 본래 이러한 쿼리를 다 해주어야 하는데  설정에서 걸어줄시 이작업을 해준다.
//		try {
//			UserDao.insert();
//			EmailDao.insert();
//		}catch() {
//			tm.rollback();
//		}
//		tm.end();
		int count=sqlSession.insert("user.insert",vo);//관심, 나머지는 횡단 관심이다.
		
		return (count == 1);
	}
	public UserVo get(UserVo vo) {
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword1",vo);
		return result;
	}
	public UserVo get(String email) {
		UserVo result = sqlSession.selectOne("user.getByEmail",email);
		return result;
	}
	public UserVo get(String email, String password) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("email",email);
		map.put("password",password);
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword2",map);
		return result;
	}
	public UserVo get(Long no) {
	      
	      return sqlSession.selectOne("user.getByNo",no);//파라미터 변수이름이랑 같지 않아도 된다.
	      												//즉, 쿼리에는 아무거나 써도 된다.
	      
	}
	public Boolean update(UserVo vo) {
			int count=sqlSession.update("user.update",vo);
			return count == 1;
	}
	
}
