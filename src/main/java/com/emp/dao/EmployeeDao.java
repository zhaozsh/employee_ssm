package com.emp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.emp.entity.Employee;

@Mapper
@Repository
public interface EmployeeDao {

	public List<Employee> getEmployees();
	
	public int deleteEmployee(String id);

	public boolean addEmployee(Employee emp);

	public boolean editEmployee(Employee emp);
}
