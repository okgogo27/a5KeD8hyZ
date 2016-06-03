package platform.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;

public class CleanServletRequest extends HttpServletRequestWrapper{

	private final AntiSamy antiSamy;

	public CleanServletRequest(HttpServletRequest request, AntiSamy antiSamy) {
		super(request);
		this.antiSamy = antiSamy;
	}

	/**
	 * overriding getParameter functions in {@link ServletRequestWrapper}
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] originalValues = super.getParameterValues(name);
		if (originalValues == null) {
			return null;
		}
		List<String> newValues = new ArrayList<String>(originalValues.length);
		for (String value : originalValues) {
			newValues.add(filterString(value));
		}
		return newValues.toArray(new String[newValues.size()]);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getParameterMap() {
		Map<String, String[]> originalMap = super.getParameterMap();
		Map<String, String[]> filteredMap = new ConcurrentHashMap<String, String[]>(originalMap.size());
		for (String name : originalMap.keySet()) {
			filteredMap.put(name, getParameterValues(name));
		}
		return Collections.unmodifiableMap(filteredMap);
	}

	@Override
	public String getParameter(String name) {
		String potentiallyDirtyParameter = super.getParameter(name);
		return filterString(potentiallyDirtyParameter);
	}

	/**
	 * This is only here so we can see what the original parameters were, you should delete this method!
	 *
	 * @return original unwrapped request
	 */
	@Deprecated
	public HttpServletRequest getOriginalRequest() {
		return (HttpServletRequest) super.getRequest();
	}

	/**
	 * @param potentiallyDirtyParameter string to be cleaned
	 * @return a clean version of the same string
	 */
	private String filterString(String potentiallyDirtyParameter) {
		if (potentiallyDirtyParameter == null) {
			return null;
		}

		try {
			CleanResults cr = antiSamy.scan(potentiallyDirtyParameter, AntiSamy.DOM);
			if (cr.getNumberOfErrors() > 0) {
				//LOG.warn("antisamy encountered problem with input: " + cr.getErrorMessages());
			}
			return cr.getCleanHTML();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
