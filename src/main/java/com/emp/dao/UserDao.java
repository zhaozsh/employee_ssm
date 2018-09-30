package com.emp.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.emp.entity.User;

@Mapper
@Repository
public interface UserDao {

	public User login(Map<String, Object> map);
}
