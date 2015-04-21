package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.service.ClientService;
import com.lbyt.client.util.TokenGenerator;

@Controller
public class LoginController {
	
	@Autowired
	private ClientService clientservice;
	
	// 注册
	@RequestMapping(value="/sign_up.json")
	@ResponseBody
	public JsonBean signUp(@RequestBody ClientBean client){
		JsonBean bean = clientservice.regist(client);
		if (bean.isSuccess()) {
			bean.setToken(TokenGenerator.generate(client));
		}
		return bean;
	}
	
	// 登录
	@RequestMapping(value="/sign_in.json")
	@ResponseBody
	public JsonBean signIn(@RequestBody ClientBean client){
		ClientBean stroeBean = clientservice.findByRegistName(client);
		if (null != stroeBean && stroeBean.getPassword().equals(client.getPassword())) {
			client.setSuccess(true);
			client.setToken(TokenGenerator.generate(stroeBean));
		} else {
			client.setSuccess(false);
		}
		return client;
	}
	
	// 退出
	@RequestMapping(value="/sign_out.json")
	@ResponseBody
	public JsonBean signOut(@RequestBody ClientBean client){
		return new JsonBean();
	}
	
	// 检测是否已注册
	@RequestMapping(value="/is_registed.json")
	@ResponseBody
	public JsonBean isRegist(@RequestBody ClientBean client){
		return clientservice.isRegisted(client);
	}
	
}
