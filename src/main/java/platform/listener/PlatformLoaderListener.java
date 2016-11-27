package platform.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import platform.property.HttpHelper;
import platform.security.AuthorizationInfoExpand;
import platform.security.LoginRealm;
import platform.utils.helper.Currents;
import platform.utils.helper.DateUtils;
import platform.utils.helper.PlatformBeanHelper;

public class PlatformLoaderListener implements ServletContextListener, ServletRequestListener {

	
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void requestInitialized(ServletRequestEvent arg0) {
		
		ServletRequest request = arg0.getServletRequest();
		request.setAttribute(HttpHelper.CURRENT_DATE_KEY, DateUtils.formatDate(Currents.getCurrentDate()));
	
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	// servlet容器初始化
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext servletContext = arg0.getServletContext();

		String contextPath = servletContext.getContextPath();
		servletContext.setAttribute(HttpHelper.CONTEXT_PATH_KEY, contextPath);

		servletContext.setAttribute(HttpHelper.RESOURCE_PATH_KEY, contextPath + "/resources/");

		HttpHelper.setServletContext(servletContext);

	}

}
