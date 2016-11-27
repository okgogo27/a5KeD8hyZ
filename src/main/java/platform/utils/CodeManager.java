package platform.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import platform.demo.entity.IdEntity;

public class CodeManager {
	
	private CodeManager(){}

    private List<CodeMethod<? extends IdEntity>> codeMethods;

    private Map<Class<?>, CodeMethod<?>> codeMethodMap = new HashMap<Class<?>, CodeMethod<?>>();

    @PostConstruct
    private void initCodeMethodMap() {
        for (CodeMethod<? extends IdEntity> codeMethod : codeMethods) {
            codeMethodMap.put(codeMethod.getActualTypeArgument(), codeMethod);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends IdEntity> CodeMethod<T> getCodeMethods(Class<T> clazz) {
        CodeMethod<T> codeMethod = (CodeMethod<T>) codeMethodMap.get(clazz);
        if (codeMethod == null) {
            throw new RuntimeException("no CodeMethod of type!!!");
        }
        return codeMethod;
    }

    public List<CodeMethod<? extends IdEntity>> getCodeMethods() {
        return codeMethods;
    }

    public void setCodeMethods(List<CodeMethod<? extends IdEntity>> codeMethods) {
        this.codeMethods = codeMethods;
    }

}
