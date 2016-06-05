package platform.utils.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

	private static final DateFormat dateFormat;

	private static final DateFormat dateTimeFormat;

	private static final DateFormat timeFormat;

	private DateUtils() {
		// nothing
	}

	static {
		dateTimeFormat = new SimpleDateFormat(Contant.DEFAULT_DATE_FORMATE);

		dateFormat = new SimpleDateFormat(Contant.DATE_NORMAL_FORMATE);

		timeFormat = new SimpleDateFormat(Contant.DATE_TIME_FORMATE);
	}

	/**
	 * 将string转化为日期
	 *
	 * @param dateString
	 * @throws ParseException
	 */
	public synchronized static Date parseDate(String dateString) throws ParseException {
		return dateFormat.parse(dateString);
	}

	/**
	 * 将string转化为时间
	 *
	 * @param dateString
	 * @throws ParseException
	 */
	public synchronized static Date parseTime(String dateString) throws ParseException {
		return timeFormat.parse(dateString);
	}

	/**
	 * 将string转化为日期时间
	 *
	 * @param dateString
	 * @throws ParseException
	 */
	public synchronized static Date parseDateTime(String dateString) throws ParseException {
		return dateTimeFormat.parse(dateString);
	}

	/**
	 * 将ISO8601转化为日期时间
	 *
	 * @param iso8601
	 *            dateString
	 * @throws ParseException
	 */
	public static Date parseISO8601(String dateString) throws ParseException {
		String regex = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2}).(\\d{3})(Z|([+|-])(\\d{2}):?(\\d{2}))";
		if (dateString.matches(regex)) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(dateString);
			while (m.find()) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, Integer.parseInt(m.group(1)));
				cal.set(Calendar.MONTH, Integer.parseInt(m.group(2)) - 1);
				cal.set(Calendar.DATE, Integer.parseInt(m.group(3)));
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(m.group(4)));
				cal.set(Calendar.MINUTE, Integer.parseInt(m.group(5)));
				cal.set(Calendar.SECOND, Integer.parseInt(m.group(6)));
				cal.set(Calendar.MILLISECOND, Integer.parseInt(m.group(7)));
				if ("Z".equals(m.group(8))) {
					cal.setTimeZone(TimeZone.getTimeZone("GMT"));
				} else {
					cal.setTimeZone(TimeZone.getTimeZone("GMT" + m.group(9) + m.group(10) + ":" + m.group(11)));
				}
				return cal.getTime();
			}
		}
		throw new ParseException(dateString + "is not a iso 8601 format date", 0);
	}

	/**
	 * 根据日期字符串是否含有时间决定转换为日期还是日期时间还是时间 可以支持ISO8601格式
	 *
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public synchronized static Date parseAll(String dateString) throws ParseException {
		try {
			return parseISO8601(dateString);
		} catch (ParseException ex) {
			try {
				return parseDateTime(dateString);
			} catch (ParseException ex1) {
				try {
					return parseDate(dateString);
				} catch (ParseException ex2) {
					return parseTime(dateString);
				}
			}
		}
	}

	/**
	 * 按格式输出date到string
	 *
	 * @param date
	 * @return
	 */
	public synchronized static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 按格式输出time到string
	 *
	 * @param date
	 * @return
	 */
	public synchronized static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 按格式输出DateTime到string
	 *
	 * @param date
	 * @return
	 */
	public synchronized static String formatDateTime(Date date) {
		return dateTimeFormat.format(date);
	}

	/**
	 * 按格式输出DateTime到iso8601 string
	 *
	 * @param date
	 * @return iso 8601 string
	 */
	public synchronized static String formatISO8601(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.format(date);
	}

	/**
	 * 按格式输出date到string,按照日期类型自动判断
	 *
	 * @param date
	 * @return
	 */
	public synchronized static String formatAll(Date date) {
		if (date instanceof java.sql.Timestamp) {
			return formatDateTime(date);
		} else if (date instanceof java.sql.Time) {
			return formatTime(date);
		} else if (date instanceof java.sql.Date) {
			return formatDate(date);
		}
		return formatDateTime(date);
	}

	/**
	 * 按格式输出string到date
	 *
	 * @param dateString
	 * @param style
	 *            格式化参数
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String dateString, String style) throws ParseException {
		DateFormat theDateFormat = new SimpleDateFormat(style);
		return theDateFormat.parse(dateString);
	}

	/**
	 * 格式化输出date到string
	 *
	 * @param date
	 * @param style
	 *            转化参数
	 * @return
	 */
	public static String format(Date date, String style) {
		DateFormat theDateFormat = new SimpleDateFormat(style);
		return theDateFormat.format(date);
	}

	/**
	 * 获取时间间隔字符串
	 *
	 * @param dateA
	 * @param dateB
	 * @param resolutionB
	 *            初始解析精度,比如resolutionB=1,表示只有间隔够一个月才会显示1月...，否则显示0月
	 * @return 时间间隔字符串
	 */
	public static final String dateDifference(final long dateA, final long dateB, final long resolutionB) {
		StringBuffer sb = new StringBuffer();
		long difference = Math.abs(dateB - dateA);
		long resolution = resolutionB;
		if (resolution > 0L) {
			resolution--;
			long months = difference / 0x9fa52400L;
			if (months > 0L) {
				difference %= 0x9fa52400L;
				sb.append(months + " 月, ");
			}
		}
		if (resolution <= 0L && sb.length() == 0) {
			return "0 月";
		}
		resolution--;
		long days = difference / 0x5265c00L;
		if (days > 0L) {
			difference %= 0x5265c00L;
			sb.append(days + " 天, ");
		}
		if (resolution <= 0L && sb.length() == 0) {
			return "0 天";
		}
		resolution--;
		long hours = difference / 0x36ee80L;
		if (hours > 0L) {
			difference %= 0x36ee80L;
			sb.append(hours + " 小时, ");
		}
		if (resolution <= 0L && sb.length() == 0) {
			return "0 小时";
		}
		resolution--;
		long minutes = difference / 60000L;
		if (minutes > 0L) {
			difference %= 60000L;
			sb.append(minutes + " 分钟, ");
		}
		if (resolution <= 0L && sb.length() == 0) {
			return "0 分钟";
		}
		resolution--;
		long seconds = difference / 1000L;
		if (seconds > 0L) {
			// difference %= 1000L;
			sb.append(seconds + " 秒, ");
		}
		if (resolution <= 0L && sb.length() == 0) {
			return "0 秒";
		}
		if (sb.length() > 2) {
			return sb.substring(0, sb.length() - 2);
		}

		return "";
	}

	/**
	 * 大于N个月计算
	 */
	public static boolean isSomeMonthsAgo(Date date, int amount) {
		if (date == null) {
			return false;
		}

		Date currentDate = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.MONTH, amount);
		return currentDate.after(cal.getTime());
	}

	/**
	 * 小于N个月计算
	 */
	public static boolean isWithinSomeMonths(Date date, int amount) {
		if (date == null) {
			return false;
		}

		Date currentDate = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.MONTH, amount);
		return currentDate.before(cal.getTime());
	}

	/**
	 * 获取一个月的最后一天的日期
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获取本周星期一
	 */
	public static Date getMondayOfThisWeek() {
		Calendar calendar = Calendar.getInstance();
		int dayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0) {
			dayofweek = 7;
		}
		calendar.add(Calendar.DATE, -dayofweek + 1);
		return calendar.getTime();
	}

	/**
	 * 获取本周星期日
	 */
	public static Date getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0) {
			dayofweek = 7;
		}
		c.add(Calendar.DATE, -dayofweek + 7);
		return c.getTime();
	}

	/**
	 * 获取日期（不包含时分秒）
	 */
	public static java.sql.Date getSqlDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		clearHourMinuteSecond(calendar);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	/**
	 * 时分秒归零
	 */
	public static Calendar clearHourMinuteSecond(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * 获取下一天的日期（不包含时分秒）
	 */
	public static java.sql.Date getNextSqlDate(Date date) {
		return addDate(date, 1);
	}

	/**
	 * 获取上一天的日期（不包含时分秒）
	 */
	public static java.sql.Date getPreviousSqlDate(Date date) {
		return addDate(date, -1);
	}

	/**
	 * @param date
	 * @param amout
	 * @return
	 */
	public static java.sql.Date addDate(Date date, int amout) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, amout);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 从当前日期算年龄
	 *
	 * @param birthday
	 * @return
	 */
	public static int getPersonAge(Date birthday) {
		return getPersonAge(birthday, new Date());
	}

	/**
	 * 从date开始算年龄
	 *
	 * @param birthday
	 * @param date
	 * @return
	 */
	public static int getPersonAge(Date birthday, Date date) {
		Calendar birthdayCalendar = Calendar.getInstance();
		birthdayCalendar.setTime(birthday);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int age = calendar.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, birthdayCalendar.get(Calendar.YEAR));
		if (calendar.before(birthdayCalendar)) {
			age--;
		}
		return age;
	}

	/**
	 * 本周第一天
	 *
	 * @return
	 */
	public static java.sql.Date getBeginDateOfThisWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar = getADayOfWeek(calendar, Calendar.MONDAY);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 本周最后一天
	 *
	 * @return
	 */
	public static java.sql.Date getEndDateOfThisWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar = getADayOfWeek(calendar, Calendar.SUNDAY);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * ？？
	 *
	 * @param
	 * @param dayOfWeek
	 * @return
	 */
	private static Calendar getADayOfWeek(Calendar day, int dayOfWeek) {
		int week = day.get(Calendar.DAY_OF_WEEK);
		if (week == dayOfWeek) {
			return day;
		}
		int diffDay = dayOfWeek - week;
		if (week == Calendar.SUNDAY) {
			diffDay -= 7;
		} else if (dayOfWeek == Calendar.SUNDAY) {
			diffDay += 7;
		}
		day.add(Calendar.DATE, diffDay);
		return day;
	}

}
