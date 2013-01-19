package org.myjetty;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet{

	private static final long serialVersionUID = 7791920745383626756L;
	
	FileDao fd = new FileDao();
	
	public UserServlet() {
		System.out.println("생성자 호출");
	}

	UserVo vo = new UserVo();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getPathInfo().substring(1);
		response.setContentType("text/html;charset=UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>" + this.getServletName()  + "</title></head><body>");
		out.println("" + fd.select(user).toString() + "");
		out.println("</body></html>");
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{

		vo.setName(request.getHeader("User-Name"));
		if (request.getHeader("User-Sex").equals("male")){
			vo.setSex(false);
		} else {
			vo.setSex(true);
		}
		vo.setAge(Integer.parseInt(request.getHeader("User-Age")));
		vo.setPhoneNumber(request.getHeader("User-Phone-Number"));
		
		fd.insert(vo);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = req.getHeader("User-Name");
		System.out.println("delete = " + user);
		fd.delete(user);
		super.doDelete(req, resp);
	}
	
	protected void doInit() {
		System.out.println("Servlet init");
		
	}
	
}


//out.println("Remote Address : "+request.getRemoteAddr()+"<br>");
//out.println("Remote HOst : "+request.getRemoteHost()+"<br>");
//out.println("Remote Port : "+request.getRemotePort()+"<br>");
//out.println("Remote User" + request.getRemoteUser() + "<br>");
//out.println("Server Name : "+request.getServerName()+"<br>");
//out.println("Server Port : "+request.getServerPort()+"<br>");
//out.println("Servlet Path : "+request.getServletPath()+"<br>");
//out.println("Context Path : " + request.getContextPath() + "<br>");
//out.println("Method Type : " + request.getMethod() + "<br>");
//out.println("Query String : " + request.getQueryString() + "<br>");
//out.println("Get Path Info : " + request.getPathInfo() + "<br>");