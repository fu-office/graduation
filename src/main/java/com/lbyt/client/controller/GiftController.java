package com.lbyt.client.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.ExcelOutputJsonBean;
import com.lbyt.client.bean.GiftBean;
import com.lbyt.client.bean.GiftImportBean;
import com.lbyt.client.bean.GiftSearchBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.service.GiftService;
import com.lbyt.client.util.ExcelUtil;

@Controller
@RequestMapping("/gift")
public class GiftController {
	
	@Autowired
	private GiftService giftService;
	
	@RequestMapping("/save.json")
	@ResponseBody
	public GiftBean save(@RequestBody GiftBean bean) {
		return giftService.save(bean);
	}
	
	@RequestMapping("/search.json")
	@ResponseBody
	public GiftSearchBean search(@RequestBody GiftSearchBean bean) {
		return giftService.search(bean);
	}
	
	@RequestMapping("/import.json")
	@ResponseBody
	public GiftSearchBean importExcel(GiftImportBean bean) {
		return giftService.importExcel(bean.getFile());
	}
	
	@RequestMapping("/export.json")
	@ResponseBody
	public ExcelOutputJsonBean exportExcel(@RequestBody GiftSearchBean bean, HttpServletResponse response){
		ExcelOutputJsonBean jsonBean = new ExcelOutputJsonBean();
		jsonBean.getCells().addAll(giftService.getCells(bean));
		jsonBean.setSpreadHeads(giftService.getHeads());
		jsonBean.setHeads(giftService.getHeads());
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
	
	@RequestMapping("/delete.json")
	@ResponseBody
	public JsonBean delete(@RequestBody GiftBean gift){
		return giftService.delete(gift);
	}
}
