package com.emp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.dao.UserDao;
import com.emp.entity.User;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public boolean login(Map<String, Object> map) {
		boolean flag = false;
	    User user=userDao.login(map);
	    if(user!=null){
	    	flag = true;
	    }
		return flag;
	}
}
