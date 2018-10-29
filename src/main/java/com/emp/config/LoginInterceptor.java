package com.emp.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Override  
	public boolean preHandle(HttpServletRequest request,  
	        HttpServletResponse response, Object handler) throws Exception {  
	      
		Object user = request.getSession().getAttribute("user");
		if (user == null || user.equals(""))  {
	        response.sendRedirect("/index.html");  
	        return false;  
		}
		// 只有返回true才会继续向下执行，返回false取消当前请求
        return true;
	}  
}
