package platform.utils;

/**
 * Memcached缓存 Manager
 */
public interface MemcachedCacheManager {

	/**
	 * 根据键获得缓存对�?
	 *
	 * @param category
	 *            种类
	 * @param key
	 *            �?
	 * @return 缓存对象，不存在或出错时返回<code> null </code>
	 */
	<T> T get(final String category, final String key);

	/**
	 * 缓存对象
	 *
	 * @param category
	 *            种类
	 * @param key
	 *            �?
	 * @param value
	 *            要缓存的对象
	 * @return 缓存是否成功
	 */
	boolean put(final String category, final String key, final Object value);

	/**
	 * 移除缓存对象
	 *
	 * @param category
	 *            种类
	 * @param key
	 *            �?
	 * @return 移除是否成功
	 */
	boolean remove(final String category, final String key);

	/**
	 * 缓存对象
	 *
	 * @param category
	 *            种类
	 * @param key
	 *            �?
	 * @param value
	 *            要缓存的对象
	 * @param exp
	 *            保存时间 【按秒作为单位，�?�?30天，0表示不过期�??
	 * @return 缓存是否成功
	 */
	boolean put(final String category, final String key, final Object value, int exp);

}
