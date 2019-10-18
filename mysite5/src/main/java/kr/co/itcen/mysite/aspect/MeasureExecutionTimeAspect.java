package kr.co.itcen.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

//@Component
@Aspect
public class MeasureExecutionTimeAspect {
	@Around("execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..)) || execution(* *..controller.*.*(..))")
	public Object aroundAspect(ProceedingJoinPoint pjp) throws Throwable {
		//before
		StopWatch sw =new StopWatch();
		sw.start();
		
		Object result=pjp.proceed();
		
		//after
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
	
		// 어떤 클래스의 어떤 메소드가 출력됬는지가 나와야한다.
		String className=pjp.getTarget().getClass().getName();
		String methodName=pjp.getSignature().getName();
		String taskName=className+"."+methodName;
		System.out.println("[Execution Time]["+taskName+"] : "+totalTime+" mills");		
		return result;
	}
	
}
