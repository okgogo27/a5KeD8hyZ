package platform.security;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;

/**
 * 鍩轰簬owasp瀹夊叏杩囨护鍣� 杩囨护鐗规畩瀛楃
 */
public class AntiSamyFilter implements Filter {

    public static final String ANTISAMY_CONFIG = "antisamy-anythinggoes.xml";

    public static AntiSamy antiSamy;

    public AntiSamyFilter() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(ANTISAMY_CONFIG);
            Policy policy = Policy.getInstance(inputStream);
            antiSamy = new AntiSamy(policy);
            inputStream.close();
        } catch (PolicyException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            CleanServletRequest cleanRequest = new CleanServletRequest((HttpServletRequest) request, antiSamy);
            chain.doFilter(cleanRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {

    }

}
