package platform.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import platform.demo.entity.CodeEntity;
import platform.demo.reposity.CodeReposity;
import platform.utils.SimpleCodeManager;

@Service
public class CodeService extends SimpleCodeManager<CodeEntity> {

    // @PostConstruct初始化执行二次
    private static boolean initFlag = false;

    @Autowired
    private CodeReposity codeReposity;

    private Map<String, List<CodeEntity>> codeMap = new HashMap<String, List<CodeEntity>>();

    @PostConstruct
    private void initCodeMap() {
        if (CodeService.initFlag) {
            List<CodeEntity> list = codeReposity.findAll(new Sort("type"));
            for (CodeEntity codeEntity : list) {
                List<CodeEntity> items = codeMap.get(codeEntity.getType());
                if (items == null) {
                    items = new ArrayList<CodeEntity>();
                    codeMap.put(codeEntity.getType(), items);
                }
                items.add(codeEntity);
            }
        } else {
            CodeService.initFlag = true;
        }
    }

    @Override
    public List<CodeEntity> findCodeByType(String type) {
        // TODO Auto-generated method stub
        return codeMap.get(type);
    }

}
