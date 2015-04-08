package com.lbyt.client;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lbyt.client.constant.HttpStatusCode;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.exception.NoLoginInException;
import com.lbyt.client.exception.WaterException;

public class ExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) {
		// handler exception
		if (e instanceof WaterException) {
			if (e instanceof NoLoginInException) {
				// 未登录异常， 返回401
				response.setStatus(HttpStatusCode.AUTHOR_ERR);
				response.setContentType("application/json;charset=UTF-8");
			}
		}
		ModelAndView mv = new ModelAndView();
		List<ErrorBean> errors = new ArrayList<ErrorBean>();
		ErrorBean error = new ErrorBean();
		error.setMessage(e.getMessage());
		error.setErrorCode(e.getClass().getName());
		errors.add(error);
		mv.addObject("success", false);
		mv.addObject("errors", errors);
		return mv.addObject(error);
	}

}
