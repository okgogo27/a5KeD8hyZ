package platform.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import platform.property.HttpRequestHelper;
import platform.property.WebContextHelper;
import platform.utils.helper.Currents;
import platform.utils.helper.DateUtils;

public class PlatformLoaderListener implements ServletContextListener, ServletRequestListener {

	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void requestInitialized(ServletRequestEvent arg0) {
		ServletRequest request = arg0.getServletRequest();
		request.setAttribute(HttpRequestHelper.CURRENT_DATE_KEY, DateUtils.formatDate(Currents.getCurrentDate()));

	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	// 閰嶇疆鐜鍙橀噺
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext servletContext = arg0.getServletContext();
		// 璁剧疆path
		String contextPath = servletContext.getContextPath();
		servletContext.setAttribute(WebContextHelper.CONTEXT_PATH_KEY, contextPath);
		// 璧勬簮鏂囦欢璇锋眰璺緞
		servletContext.setAttribute(WebContextHelper.RESOURCE_PATH_KEY, contextPath + "/resources/");
		// 娣诲姞鍒扮紦瀛�
		WebContextHelper.setServletContext(servletContext);

	}

}
