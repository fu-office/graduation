package com.lbyt.client.aspect;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.lbyt.client.HttpContextHolder;
import com.lbyt.client.bean.ExcelOutputJsonBean;
import com.lbyt.client.util.ExcelUtil;

@Aspect
@Component
public class RequestAspect {

	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundController(final ProceedingJoinPoint joinPoint) {
		 try {
			return processOutputs(joinPoint.proceed());
		} catch (IOException e) {
			return joinPoint;
		} catch (Throwable e) {
			return joinPoint;
		}
	}
	
	 private Object processOutputs(final Object output) throws IOException {
	        if (null != output) {
	            if (output instanceof ExcelOutputJsonBean) {
	                //对于Excel格式的输出,根据bean中的内容创建Excel文件并写入输出流
	                ExcelOutputJsonBean bean = (ExcelOutputJsonBean) output;
	                HttpContextHolder.getResponse().setContentType("application/msexcel");
	                ExcelUtil.buildExcelFile(bean.getBean(), bean.getErrors(), HttpContextHolder.getResponse().getOutputStream());
	                return null;
	            }
	       }
	       return output;
	 }
}
