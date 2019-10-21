package kr.co.itcen.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.PictureVo;

@Repository
public class PictureDao {
	@Autowired
	private SqlSession sqlsession;

	public Boolean insert(PictureVo vo) {
		int count = sqlsession.insert("picture.insert",vo);
		return count == 1;
	}
	public List<PictureVo> select(Long no) {
		List<PictureVo> result = sqlsession.selectList("picture.select",no);
		return result;
	}
}