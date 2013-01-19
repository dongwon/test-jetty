package org.myjetty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.FilterHolder;
import org.mortbay.jetty.servlet.FilterMapping;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;

public class TestHttpServer {

	protected final Server svr;
	protected final Connector listener;
	protected final WebAppContext webAppContext;
	protected final List<String> filterNames = new ArrayList<String>();


	public TestHttpServer(String name, String bindAddress, int port) throws Exception{

		listener = createDefaultChannelConnector();
		listener.setHost(bindAddress);
		listener.setPort(port);

		svr = new Server();

		svr.addConnector(listener);

		svr.setThreadPool(new QueuedThreadPool());

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		svr.setHandler(contexts);

		webAppContext = new WebAppContext();
		
		webAppContext.addFilter(new FilterHolder(TestFilter.class), "/*", 1);
		
		webAppContext.setDisplayName("WepAppsContext");
		webAppContext.setContextPath("/");
		System.out.println(webAppContext.toString());
		webAppContext.setWar("webapps" + "/" + name);
		
		svr.addHandler(webAppContext);
	}


	public static Connector createDefaultChannelConnector() {
		SelectChannelConnector ret = new SelectChannelConnector();
		ret.setLowResourceMaxIdleTime(10000);
		ret.setAcceptQueueSize(128);
		ret.setResolveNames(false);
		ret.setUseDirectBuffers(false);
		return ret;   
	}

	protected void defineFilter(Context ctx, String name,
			String classname, Map<String,String> parameters, String[] urls) {

		FilterHolder holder = new FilterHolder();
		holder.setName(name);
		holder.setClassName(classname);
		holder.setInitParameters(parameters);
		FilterMapping fmap = new FilterMapping();
		fmap.setPathSpecs(urls);
		fmap.setDispatches(Handler.ALL);
		fmap.setFilterName(name);
		ServletHandler handler = ctx.getServletHandler();
		handler.addFilter(holder, fmap);

	}

	public void addServlet(String name, String pathSpec,
			Class<? extends HttpServlet> clazz) {
		addInternalServlet(name, pathSpec, clazz);
		addFilterPathMapping(pathSpec, webAppContext);
	}

	public void addInternalServlet(String name, String pathSpec, 
			Class<? extends HttpServlet> clazz) {
		ServletHolder holder = new ServletHolder(clazz);
		
		if (name != null) {
			holder.setName(name);
		}
		webAppContext.addServlet(holder, pathSpec);
	}

	protected void addFilterPathMapping(String pathSpec,
			Context webAppCtx) {
		ServletHandler handler = webAppCtx.getServletHandler();
		for(String name : filterNames) {
			FilterMapping fmap = new FilterMapping();
			fmap.setPathSpec(pathSpec);
			fmap.setFilterName(name);
			fmap.setDispatches(Handler.ALL);
			handler.addFilterMapping(fmap);
		}
	}

	public void start() throws Exception {
		listener.open();
		svr.start();
	}

	public void stop() throws Exception {
		listener.close();
		svr.stop();
	}

}
