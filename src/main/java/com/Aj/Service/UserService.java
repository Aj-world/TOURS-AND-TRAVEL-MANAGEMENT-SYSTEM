package com.Aj.Service;

import java.util.ArrayList;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

import com.Aj.Dao.UserDao;
import com.Aj.Entity.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	 
	
	public User saveUser(User uEntity) {
		
		 

		return this.userDao.save(uEntity );
	}

	
	public List<User> getAllUser() {
		
		List<User> userRecords = new ArrayList<>();
		this.userDao.findAll().forEach(userRecords::add);

		return userRecords;

	}
	public User getUserby_id(int id) {
		
		return	this.userDao.findById(id).get();
			
		}
	public User updateUser(int id) {
		
		return	this.userDao.findById(id).get();
			
		}
		
		public int deleteUser(int id) {
			
			  this.userDao.deleteById(id);
			  return 1;
		}

}
