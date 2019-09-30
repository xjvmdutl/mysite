package kr.co.itcen.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.UserDao;
import kr.co.itcen.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public void join(UserVo vo) {
		userDao.insert(vo);
	}

	public UserVo getUser(UserVo vo) {
		return userDao.get(vo);
	}

	public UserVo get(Long no) {
		
		return userDao.get(no);
	}

	public void update(UserVo vo) {
		userDao.update(vo);
	}

	public Boolean existUser(String email) {
		
		return userDao.get(email) != null;
	}
}
