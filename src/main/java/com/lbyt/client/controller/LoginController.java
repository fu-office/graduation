package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.service.ClientService;

@Controller
public class LoginController {
	
	@Autowired
	private ClientService clientservice;
	
	// 注册
	@RequestMapping(value="/sign_up.json")
	@ResponseBody
	public JsonBean signUp(ClientBean client){
		return clientservice.save(client);
	}
	
	// 登录
	@RequestMapping(value="/sign_in.json")
	@ResponseBody
	public JsonBean signIn(ClientBean client){
		return new JsonBean();
	}
	
	// 退出
	@RequestMapping(value="/sign_out.json")
	@ResponseBody
	public JsonBean signOut(ClientBean client){
		return new JsonBean();
	}
	
	// 检测是否已注册
	@RequestMapping(value="/sign_out.json")
	@ResponseBody
	public JsonBean isRegist(ClientBean client){
		return clientservice.isRegisted(client);
	}
}
