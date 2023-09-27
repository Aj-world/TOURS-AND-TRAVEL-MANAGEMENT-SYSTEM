package com.Aj.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Aj.Dao.UserDao;
import com.Aj.Entity.User;
@Service
public class RegistationService {
	
	@Autowired
	private UserDao userDao;

	
	public User saveUser(User uEntity) {
		
		return this.userDao.save(uEntity );
	}
	
	public User findByName(String name) {
		
		return this.userDao.findByEmail(name).get();
	}

}
