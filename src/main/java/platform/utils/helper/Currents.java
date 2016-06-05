package platform.utils.helper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import demo.entity.User;

/**
 * 获取当前时间，当前用户 工具类
 */
@Component
public class Currents {

	private Currents() {
	}

	/**
	 * 获取当前时间
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间
	 */
	public static Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取当前用户
	 */
	public static User getCurrentUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 获取当前用户账号
	 */
	public static String getCurrentUsername() {
		User user = getCurrentUser();
		return user == null ? "" : user.getName();
	}

	/**
	 * 获取当前年份
	 */
	@SuppressWarnings("deprecation")
	public static int getCurrentYear() {
		return Currents.getCurrentDate().getYear();
	}

	/**
	 * 获取当前月份
	 */
	public static int getCurrentMonth() {
		return Currents.getCurrentDate().getMonth();
	}

	/**
	 * 获取当前年的第一天
	 */
	public static Date getCurrentYearFirstDay() {
		Calendar c = Calendar.getInstance();
		try {
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DATE, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return c.getTime();
	}

	/**
	 * 获取当前年的第最后天
	 */
	public static Date getCurrentYearLastDay() {
		Calendar c = Calendar.getInstance();
		try {
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTime();
	}
}
