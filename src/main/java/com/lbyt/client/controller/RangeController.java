package com.lbyt.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.RangeBean;
import com.lbyt.client.service.RangeService;

@Controller
@RequestMapping("/range")
public class RangeController {
	
	@Autowired
	private RangeService rangeService;
	
	@RequestMapping(value="/save.json")
	@ResponseBody
	public JsonBean save(@RequestBody RangeBean range){
		return rangeService.saveOrUpdate(range);
	}
	
	@RequestMapping(value="/delete.json")
	@ResponseBody
	public JsonBean findByToken(@RequestBody RangeBean range){
		return rangeService.delete(range);
	}
	
}
