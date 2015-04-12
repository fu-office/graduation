package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.ClientAddressBean;
import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.service.ClientAddressService;
import com.lbyt.client.service.ClientService;
import com.lbyt.client.util.TokenGenerator;

@Controller
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private ClientService clientservice;
	
	@Autowired
	private ClientAddressService addressService;
	
	@RequestMapping(value="/save.json")
	@ResponseBody
	public JsonBean save(@RequestBody ClientAddressBean client){
		ClientBean clientBean = TokenGenerator.getClientByToken(client.getToken());
		if (null != clientBean) {
			client.setClientId(clientBean.getId());
		} else {
			client.setSuccess(false);
			client.getErrors().add(new ErrorBean("当前未登录", null));
			return client;
		}
		return addressService.saveOrUpdate(client);
	}
	
	@RequestMapping(value="/client.json")
	@ResponseBody
	public JsonBean findByToken(@RequestBody ClientAddressBean client){
		// get currentUser by token
		ClientBean bean = TokenGenerator.getClientByToken(client.getToken());
		if (null != bean) {
			client.setClientId(bean.getId());
		}
		return addressService.findByClient(client);
	}
	
}
