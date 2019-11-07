package kr.co.itcen.mysite.repository;


import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	@Autowired
	private SqlSession sqlsession;
	
	public Boolean insert(GuestBookVo vo) {
		int count=sqlsession.insert("guestbook.insert", vo);
		return count==1;
	}

	public List<GuestBookVo> selectAll() {
		List<GuestBookVo> result = sqlsession.selectList("guestbook.getlist");
	    return result;
	}
	
	public Boolean delete(GuestBookVo vo) {
		int count=sqlsession.delete("guestbook.delete",vo);
	    return count==1;
	}
	public List<GuestBookVo> selectAll(Long startNo) {
		List<GuestBookVo> result = sqlsession.selectList("guestbook.getlist2",startNo);
	    return result;
	}
	public Boolean delete(Long no,String password) {
		GuestBookVo vo = new GuestBookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
	    return delete(vo);
	}
}
