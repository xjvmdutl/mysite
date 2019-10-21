package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.AdminDao;
import kr.co.itcen.mysite.vo.GuestBookVo;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;

	public List<GuestBookVo> getList() {

		return adminDao.getList();
	}
	
}
