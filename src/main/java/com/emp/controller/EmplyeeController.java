package com.emp.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.catalina.core.ApplicationPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.entity.Employee;
import com.emp.service.EmployeeService;

@RestController
@RequestMapping("/emp/*")
public class EmplyeeController {

	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping("/search")
	public List<Employee> searchEmp() {
		List<Employee> list = employeeService.getEmployees();
        return list;
	}
	
	@RequestMapping("/addOrEdit")
	public String addOrEditEmp(HttpServletRequest request) throws Exception {
		String str = "error";
		Employee emp = new Employee();
		emp.setName(request.getParameter("name"));
		emp.setSex(Integer.parseInt(request.getParameter("sex")));
		emp.setBirthday(request.getParameter("birthday"));
		emp.setPhone(request.getParameter("phone"));
		emp.setAddress(request.getParameter("address"));
		// 设置存放员工证件照的路径
		String path=request.getSession().getServletContext().getRealPath("/");
		File f = new File(path+"upload\\");
        if (!f.exists()) {// 若图片保存路径不存在则创建
            f.mkdir();
        }
        Part p=request.getPart("file");
        if(p.getContentType().contains("image")){
        	ApplicationPart ap= (ApplicationPart) p;
            String fname=ap.getSubmittedFileName();
            p.write(path+"upload\\"+fname);
            emp.setPhoto("upload/"+fname);
        }
        
        String type = request.getParameter("type");
        if(type.equals("add")) {
    		emp.setIdcard(request.getParameter("idcard"));
        	if(employeeService.addEmployee(emp)) {
    			str = "添加成功！";
    		}else {
    			str = "添加失败！";
    		}
        }else {
        	emp.setPid(request.getParameter("pid"));
			if(employeeService.editEmployee(emp)) {
				str = "修改成功！";
			}else {
				str = "修改失败！";
			}
		}
        
        return str;
	}
	
	@RequestMapping("/delete")
	public String deleteEmp(HttpServletRequest request) {
		String str = "unknown exception";
		String pid = request.getParameter("pid");
		if( pid==null || pid.equals("")) {
			str = "传入ID为空";
		}
		int i = employeeService.deleteEmployee(pid);
		if(i>0) {
			str = "success";
		}
		return str;
	}
	
	
}
