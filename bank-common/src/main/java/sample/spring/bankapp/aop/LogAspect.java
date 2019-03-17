package sample.spring.bankapp.aop;

import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.stereotype.Component;

import sample.spring.bankapp.annotations.Action;



@Aspect
@Component
public class LogAspect {
	
	@Pointcut("@annotation(sample.spring.bankapp.annotations.Action)")// 設定前面自訂的Annotation @Action 是一個切點
	public void annotationPointCut() { // 透過這個方法去執行切點
		
	}
	
	@After("annotationPointCut()")
	public void after(JoinPoint joinPoint) {
		 MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		 Method method = signature.getMethod();
		 Action action = method.getAnnotation(Action.class);
		 System.out.println("註解攔截:" + action.name());
	}
	
	
	
}
