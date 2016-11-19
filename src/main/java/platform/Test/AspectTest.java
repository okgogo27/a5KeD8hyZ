package platform.Test;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectTest {

	@Pointcut("execution(* demo.controller..*.*(..))")
	public void pointCut() {
	}

	@Before(value = "pointCut()")
	public void doBefore() {
		System.out.println("before");
	}

}
