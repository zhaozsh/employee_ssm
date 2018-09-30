package com.emp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.dao.EmployeeDao;
import com.emp.entity.Employee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	public List<Employee> getEmployees() {
		return employeeDao.getEmployees();
	}
	
	public int deleteEmployee(String id) {
		return employeeDao.deleteEmployee(id);
	}

	public boolean addEmployee(Employee emp) {
		return employeeDao.addEmployee(emp);
	}

	public boolean editEmployee(Employee emp) {
		return employeeDao.editEmployee(emp);
	}
}
