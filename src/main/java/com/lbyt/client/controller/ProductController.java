package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.ProductBean;
import com.lbyt.client.service.ProductService;

@Controller
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@RequestMapping(value="/save.json")
	@ResponseBody
	public JsonBean add(@RequestBody ProductBean bean){
		return service.save(bean);
	}
	
	@RequestMapping(value="/findAll.json")
	@ResponseBody
	public JsonBean findAll(@RequestBody JsonBean bean){
		return service.findAll();
	}
}
