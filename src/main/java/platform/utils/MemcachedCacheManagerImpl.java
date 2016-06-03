package platform.utils;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemcachedCacheManagerImpl implements MemcachedCacheManager {

    // �? * �? * �? * �? [�?�?30天]，这里设置为0�?0表示永不超时
    private static final int MAX_EXPIRED_TIME = 0; // 60 * 60 * 24 * 30

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemcachedClient memcachedClient;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(final String category, final String key) {
        try {
            return (T) memcachedClient.get(category + key);
        } catch (Exception e) {
            logger.error("读缓存失�?", e);
            return null;
        }
    }

    @Override
    public boolean put(final String category, final String key, final Object value) {
        try {
            return memcachedClient.set(category + key, MAX_EXPIRED_TIME, value);
        } catch (Exception e) {
            logger.error("写缓存失�?", e);
            return false;
        }
    }

    @Override
    public boolean remove(final String category, final String key) {
        try {
            return memcachedClient.delete(category + key);
        } catch (Exception e) {
            logger.error("删缓存失�?", e);
            return false;
        }
    }

    @Override
    public boolean put(String category, String key, Object value, int exp) {
        try {
            return memcachedClient.set(category + key, exp, value);
        } catch (Exception e) {
            logger.error("写缓存失�?", e);
            return false;
        }
    }
}
