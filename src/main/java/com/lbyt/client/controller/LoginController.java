package com.lbyt.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.JsonBean;

@Controller
public class LoginController {
	
	@RequestMapping(value="/sign_in.json")
	@ResponseBody
	public JsonBean signIn(){
		return new JsonBean();
	}
	
	@RequestMapping(value="/sign_out.json")
	@ResponseBody
	public JsonBean signOut(){
		return new JsonBean();
	}
}
