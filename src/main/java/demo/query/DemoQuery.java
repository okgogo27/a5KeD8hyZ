package demo.query;

import demo.entity.DemoEntity;
import platform.query.AbstractQuery;
import platform.repository.PredicateFilter;
import platform.repository.Operators.Operator;

public class DemoQuery extends AbstractQuery<DemoEntity> {

	@PredicateFilter(fieldName="name",value=Operator.LIKE_RIGHT)
	private String name = "w";

}
