package com.emp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.emp.entity.Employee;

@Mapper
@Repository
public interface EmployeeDao {

	public List<Employee> getEmployees(Map<String, Object> map);

	public Integer getTotal(Map<String, Object> map);
	
	public int deleteEmployee(String id);

	public boolean addEmployee(Employee emp);

	public boolean editEmployee(Employee emp);

}
