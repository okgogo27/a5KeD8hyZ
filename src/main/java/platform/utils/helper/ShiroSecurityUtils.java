package platform.utils.helper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiroSecurityUtils extends SecurityUtils {

	/**
	 * 当前用户的权限判断
	 */
	public static boolean isPermitted(String permission) {
		Subject subject = getSubject();

		if (subject == null) {
			return false;
		}

		return subject.isPermitted(permission);
	}

	/**
	 * 当前用户的权限判断
	 */
	public static boolean isPermittedAll(String... permissions) {
		Subject subject = getSubject();

		if (subject == null) {
			return false;
		}

		return subject.isPermittedAll(permissions);
	}

}
