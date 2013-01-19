package org.myjetty;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestFilter implements Filter{

	private String adminID = "admin";
	private String adminPW = "cdpadmin";
		
	@Override
	public void destroy() {

		System.out.println("Filter doDestory");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		System.out.println("doFilter");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String adminIDTemp = "";
		String adminPWTemp = "";

		adminIDTemp = req.getHeader("Admin-ID");
		adminPWTemp = req.getHeader("Admin-PW");
		System.out.println(req.getMethod());
		
		if(req.getMethod().equals("PUT") || req.getMethod().equals("DELETE")){
			if(authCheck(adminIDTemp, adminPWTemp) == true) {
				chain.doFilter(req, res);			
			} else {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failure");
				return;
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

		System.out.println("Filter init");
		
	}
 
	private boolean authCheck(String id, String password){

		boolean auth = false;
		if (id.equals(adminID) && password.equals(adminPW)){
			auth = true;
		}
		return auth;
	}
}
