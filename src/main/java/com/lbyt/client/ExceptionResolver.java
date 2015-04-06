package com.lbyt.client;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lbyt.client.error.ErrorBean;

public class ExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception e) {
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
