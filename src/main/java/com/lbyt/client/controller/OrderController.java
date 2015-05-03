package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.OrderBean;
import com.lbyt.client.bean.OrderSearchBean;
import com.lbyt.client.service.OrderService;
import com.lbyt.client.util.TokenGenerator;

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
		if (null == order.getId()) {
			ClientBean client = TokenGenerator.getClientByToken(order.getToken());
			order.setClientId(client.getId());
			order.setName(client.getRegistName());
			order.setPhone(client.getPhone());
		}
		return orderService.saveOrUpdate(order);
	}
	
	@RequestMapping("/delete.json")
	@ResponseBody
	public OrderBean delete(@RequestBody OrderBean order) {
		return orderService.delete(order);
	}
	
	@RequestMapping("/cancel.json")
	@ResponseBody
	public JsonBean cancelOrder(@RequestBody OrderBean order){
		return orderService.cancelOrder(order);
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
	
	@RequestMapping("/getOrderByClient.json")
	@ResponseBody
	public OrderSearchBean findByClient(@RequestBody OrderSearchBean bean){
		ClientBean clientBean = TokenGenerator.getClientByToken(bean.getToken());
		if (null != clientBean) {
			bean.setClientId(clientBean.getId());
		}
		return orderService.getOrdersByClient(bean);
	}
	
	@RequestMapping("/scheduleOrder.json")
	@ResponseBody
	public OrderSearchBean findScheduleOrder(@RequestBody OrderSearchBean bean){
		return orderService.getScheduleOrder(bean);
	}
	
	@RequestMapping("/updatePayStatus.json")
	@ResponseBody
	public OrderBean updatePayStatus(@RequestBody OrderBean bean){
		return orderService.updatePayStatus(bean);
	}
	
	@RequestMapping("/updateOrderStatus.json")
	@ResponseBody
	public OrderBean updateOrderStatus(@RequestBody OrderBean bean){
		return orderService.updateOrderStatus(bean);
	}
	
	@RequestMapping("/orderDelivery.json")
	@ResponseBody
	public OrderBean orderDelivery(@RequestBody OrderBean order){
		return orderService.distributionDelivery(order);
	}
}
