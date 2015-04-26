package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.StockBean;
import com.lbyt.client.bean.StockOrderBean;
import com.lbyt.client.service.StockService;

@Controller
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private StockService service;
	
	@RequestMapping(value="/findAll.json")
	@ResponseBody
	public JsonBean findAll(@RequestBody JsonBean bean){
		return service.findAll();
	}
	
	@RequestMapping(value="/findByProdName.json")
	public JsonBean findByProdName(@RequestBody StockBean bean){
		return service.findByProdName(bean);
	}
	
	@RequestMapping(value="/saveStockOrder.json")
	public JsonBean saveStockOrderBean(@RequestBody StockOrderBean bean){
		return service.saveStockOrder(bean);
	}
}
