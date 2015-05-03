package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.DeliveryBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.service.DeliveryService;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
	
	@Autowired
	private DeliveryService service;
	
	@RequestMapping(value="/add.json")
	@ResponseBody
	public JsonBean add(@RequestBody DeliveryBean delivery){
		return service.save(delivery);
	}
	
	@RequestMapping(value="/search.json")
	@ResponseBody
	public JsonBean search(@RequestBody JsonBean bean){
		return service.findAll();
	}
}
