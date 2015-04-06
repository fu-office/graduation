package com.lbyt.client.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.AreaBean;
import com.lbyt.client.bean.AreaImportBean;
import com.lbyt.client.bean.AreaSearchBean;
import com.lbyt.client.bean.ExcelOutputJsonBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.service.AreaService;
import com.lbyt.client.util.ExcelUtil;

@Controller
@RequestMapping("/area")
public class AreaController {
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/import.json", method = RequestMethod.POST)
	@ResponseBody
	public AreaSearchBean importArea(AreaImportBean importBean) {
		return areaService.importArea(importBean.getFile());
	}
	
	@RequestMapping("/export.json")
	@ResponseBody
	public ExcelOutputJsonBean export(@RequestBody AreaSearchBean json, HttpServletResponse response) {
		ExcelOutputJsonBean jsonBean = new ExcelOutputJsonBean();
		jsonBean.getCells().addAll(areaService.getCells(json));
		jsonBean.setSpreadHeads(areaService.getHeads());
		jsonBean.setHeads(areaService.getHeads());
		response.setContentType("application/msexcel");
		try {
			ExcelUtil.buildExcelFile(jsonBean.getBean(), jsonBean.getErrors(), response.getOutputStream());
		} catch (IOException e) {
			jsonBean.setSuccess(false);
			ErrorBean error = new ErrorBean();
			error.setMessage(e.getMessage());
			jsonBean.getErrors().add(error);
		}
		return jsonBean;
	}
	
	@RequestMapping("/search.json")
	@ResponseBody
	public AreaSearchBean search(@RequestBody AreaSearchBean json) {
		return areaService.search(json);
	}
	
	@RequestMapping("/saveOrUpdate.json")
	@ResponseBody
	public AreaBean saveOrUpdate(@RequestBody AreaBean area) {
		return areaService.saveOrUpdate(area);
	}
	
	@RequestMapping("/delete.json")
	@ResponseBody
	public JsonBean delete(@RequestBody AreaBean area) {
		return areaService.delete(area);
	}
	
	@RequestMapping("/findAll.json")
	@ResponseBody
	public AreaSearchBean findAll() {
		AreaSearchBean json = new AreaSearchBean();
		json.setList(areaService.findAll());
		json.setSuccess(true);
		return json;
	}
	
	@RequestMapping("/findById.json")
	@ResponseBody
	public AreaBean findById(@RequestBody AreaBean area) {
		return areaService.findById(area);
	}
}
