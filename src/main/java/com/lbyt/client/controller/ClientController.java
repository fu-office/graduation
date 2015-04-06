package com.lbyt.client.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.bean.ClientSearchBean;
import com.lbyt.client.bean.CustomerImportJsonBean;
import com.lbyt.client.bean.ExcelOutputJsonBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.service.ClientService;
import com.lbyt.client.util.ExcelUtil;

@Controller
@RequestMapping("/customer")
public class ClientController {

	@Autowired
	private ClientService clientService; 
	
	@RequestMapping(value="/import.json", method=RequestMethod.POST)
	@ResponseBody
	public ClientSearchBean importCustomers(final CustomerImportJsonBean fileBean) throws IOException{
		return clientService.importExcel(fileBean.getFile());
	}
	
	@RequestMapping("/export.json")
	@ResponseBody
	public ExcelOutputJsonBean export(@RequestBody ClientSearchBean bean, HttpServletResponse response){
		ExcelOutputJsonBean jsonBean = new ExcelOutputJsonBean();
		jsonBean.getCells().addAll(clientService.getCells(bean));
		jsonBean.setSpreadHeads(clientService.getHeads());
		jsonBean.setHeads(clientService.getHeads());
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
	public ClientSearchBean search(@RequestBody ClientSearchBean bean) {
		return clientService.search(bean);
	}
	
	@RequestMapping("/deleteById.json")
	@ResponseBody
	public JsonBean delete(@RequestBody ClientBean bean) {
		return clientService.delete(bean);
	}
	
	@RequestMapping("/save.json")
	@ResponseBody
	public ClientBean save(@RequestBody ClientBean bean) throws Exception {
		return clientService.save(bean);
	}
}
