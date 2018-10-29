package com.emp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.emp.service.UserService;
import com.emp.config.WebMvcConfg;

@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	@RequestMapping("/login")
	public String login(HttpSession session) throws Exception {
		
		String str = "";
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username",username);
		map.put("password", password);
		if(userService.login(map)) {
	        Cookie c1 = new Cookie("loginName", username);
	        c1.setPath("/");
	        response.addCookie(c1);
            session.setAttribute("user",WebMvcConfg.SESSION_KEY);
			str = "redirect:/main.html";
		}else {
			str = "redirect:/index.html";
		}
		return str;
	}
	
	@RequestMapping("/logout")
	public String logout() {
	    HttpSession session = request.getSession();
        session.removeAttribute("user");
		return "redirect:/index.html";
	}
}
