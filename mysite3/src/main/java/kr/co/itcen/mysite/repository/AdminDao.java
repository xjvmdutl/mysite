package kr.co.itcen.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.GuestBookVo;

@Repository
public class AdminDao {
	@Autowired
	private SqlSession sqlsession;

	public List<GuestBookVo> getList() {
		List<GuestBookVo> list = sqlsession.selectList("admin.guestbookgetList");
		return list;
	}
	
}
