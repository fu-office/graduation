package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.OrderBean;
import com.lbyt.client.bean.OrderSearchBean;
import com.lbyt.client.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping("/search.json")
	@ResponseBody
	public OrderSearchBean search(@RequestBody OrderSearchBean json) {
		return orderService.search(json);
	}
	
	@RequestMapping("/saveOrUpdate.json")
	@ResponseBody
	public OrderBean saveOrUpdate(@RequestBody OrderBean order) {
		return orderService.saveOrUpdate(order);
	}
	
	@RequestMapping("/delete.json")
	@ResponseBody
	public OrderBean delete(@RequestBody OrderBean order) {
		return orderService.delete(order);
	}
	
	@RequestMapping("/findAll.json")
	@ResponseBody
	public OrderSearchBean findAll() {
		OrderSearchBean json = new OrderSearchBean();
		json.setList(orderService.findAll());
		json.setSuccess(true);
		return json;
	}
	
	@RequestMapping("/findById.json")
	@ResponseBody
	public OrderBean findById(@RequestBody OrderBean order) {
		return orderService.findById(order);
	}
}
