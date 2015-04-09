package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.ClientAddressBean;
import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.bean.JsonBean;
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
