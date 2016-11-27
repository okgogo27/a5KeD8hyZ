package platform.property;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpHelper {

    public static final String CURRENT_DATE_KEY = "currentDate";
    
 // 系统配置
    public static final String CONTEXT_PATH_KEY = "path";

    public static final String RESOURCE_PATH_KEY = "resourcePath";

    /** web容器 */
    private static ServletContext servletContext;

    public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		HttpHelper.servletContext = servletContext;
	}

	/**
     * 获取当前http请求的Request，只在http请求线程中生效
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs == null ? null : attrs.getRequest();
    }

}
