package org.myjetty;

public class ServiceLauncher {

	public static void main(String[] args) throws Exception {
		
		TestHttpServer infoServer1 = new TestHttpServer("testserver1", "localhost", 9000);
		
		infoServer1.addServlet("Admin Servlet", "/user" + "/*", UserServlet.class);
		
		infoServer1.start();

	}
}
