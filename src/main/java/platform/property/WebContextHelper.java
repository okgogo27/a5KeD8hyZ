package platform.property;

import javax.servlet.ServletContext;

public class WebContextHelper {

    // 系统配置
    public static final String CONTEXT_PATH_KEY = "path";

    public static final String RESOURCE_PATH_KEY = "resourcePath";

    /** web容器 */
    private static ServletContext servletContext;

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static void setServletContext(ServletContext servletContext) {
        WebContextHelper.servletContext = servletContext;
    }

}
