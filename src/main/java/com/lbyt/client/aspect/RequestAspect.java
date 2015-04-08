package com.lbyt.client.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lbyt.client.service.LoggerService;

@Aspect
@Component
public class RequestAspect {

	@Autowired
	private LoggerService loggerService;
	
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundController(final ProceedingJoinPoint joinPoint) {
		Logger logger = loggerService.getLogger(joinPoint.getTarget().getClass());
		Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
		if (logger.isDebugEnabled()) {
			logger.debug("invoke: " + method.getName());
		}
		// 验证 token
		return joinPoint;
	}
	
}
