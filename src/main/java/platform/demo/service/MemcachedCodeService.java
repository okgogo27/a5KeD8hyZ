package platform.demo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import platform.demo.entity.MemcachedEntity;
import platform.utils.SimpleCodeManager;

@Service
public class MemcachedCodeService extends SimpleCodeManager<MemcachedEntity> {

    // @Autowired
    // private MemcachedCacheManager memcachedCacheManager;

    // 防止@PostConstruct初始化执行二次
    private static boolean initFlag = false;

    @PostConstruct
    public void MemcachedInit() {
        if (MemcachedCodeService.initFlag) {
        } else {
            MemcachedCodeService.initFlag = true;
        }

    }

    @Override
    public List<MemcachedEntity> findCodeByType(String type) {
        return null;
    }

}
