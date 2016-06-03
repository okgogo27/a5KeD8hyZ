package demo.service;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.entity.DemoEntity;
import demo.query.DemoQuery;
import demo.reposity.DemoReposity;

@Service
public class DemoService {

    @Autowired
    private DemoReposity demoReposity;

    public List<DemoEntity> find(DemoQuery demoQuery) {

        // hibernate查询（注意from后是实体名而不是表名）
        // Query query = entityManager.createQuery("SELECT c FROM DemoEntity
        // c");
        // @SuppressWarnings("unchecked")
        // List<DemoEntity> result = query.getResultList();
        // for (DemoEntity c : result) {
        // System.out.println(c.getId() + "," + c.getName());
        //
        // }
        Predicate predicate = demoQuery.getPredicates();

        return demoReposity.findAll(demoQuery.getPredicateManager(), predicate);
    }

    public void save(DemoEntity demoEntity) {
        demoReposity.save(demoEntity);
    }

}
