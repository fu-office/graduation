package com.lbyt.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.bean.ClientSearchBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.SimpleBean;
import com.lbyt.client.entity.ClientEntity;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.persistservice.ClientPersistService;
import com.lbyt.client.util.CommUtil;

@Service
public class ClientService {
	
	@Autowired
	private ClientPersistService clientPersist;
	
	
	public List<ClientBean> findAll() {
		List<ClientEntity> entities = clientPersist.findAll();
		List<ClientBean> list = new ArrayList<ClientBean>();
		for (ClientEntity entity : entities) {
			list.add(bulidBean(entity));
		}
		return list;
	}
	
	public ClientBean saveOrUpdate(ClientBean order) {
		if (order.getId() == null) {
			String address = order.getAddress();
			if (CommUtil.isEmpty(address)) {
				order.setSuccess(false);
				ErrorBean e = ErrorBean.getInstance();
				e.setMessage("配送地址不能为空");
				order.getErrors().add(e);
				return order;
			}
			order.setRegistDate(new Date());
			return save(order);
		} else {
			return update(order);
		}
	}

	public ClientBean save(ClientBean client) {
		ClientEntity entity = bulidEntity(client);
		if (this.isRegisted(client.getRegistName())) {
			client.setSuccess(false);
			client.getErrors().add(new ErrorBean("该学号已注册", null));
		} else {
			clientPersist.save(entity);
			client.setSuccess(true);
			client.setId(entity.getId());
		}
		return client;
	}
	
	public JsonBean save(List<ClientBean> list) {
		JsonBean json = new JsonBean();
		for (ClientBean order : list) {
			save(order);
		}
		json.setSuccess(true);
		return json;
	}
	
	public ClientBean update(ClientBean order) {
		ClientBean storeBean = findById(order);
		if (storeBean != null) {
			save(storeBean);
			storeBean.setSuccess(true);
			return storeBean;
		} else {
			order.setSuccess(false);
			ErrorBean error = new ErrorBean();
			error.setMessage("该订单不存在，或已删除");
			order.getErrors().add(error);
		}
		return order;
	}
	
	public ClientSearchBean search(ClientSearchBean order) {
		ClientSearchBean json = new ClientSearchBean();
		ClientEntity entity = new ClientEntity();
		List<ClientEntity> list =  clientPersist.findByProvAndCityAndShopState(entity);
		for (ClientEntity enti : list) {
			json.getList().add(bulidBean(enti));
		}
		json.setSuccess(true);
		return json;
	}
	
	public ClientBean delete(ClientBean order) {
		clientPersist.deleteById(bulidEntity(order));
		order.setSuccess(true);
		return order;
	}
	
	public ClientBean findById(ClientBean order) {
		ClientEntity entity = clientPersist.findById(bulidEntity(order));
		return bulidBean(entity);
	}
	
	private boolean isRegisted(String registName){
		ClientEntity entity = new ClientEntity();
		entity.setRegistName(registName);
		List<ClientEntity> list = clientPersist.findByRegistName(entity);
		return 0 == list.size();
	}
	
	public SimpleBean isRegisted(ClientBean bean){
		SimpleBean simpleBean = new SimpleBean();
		simpleBean.setResult(String.valueOf(this.isRegisted(bean.getRegistName())));
		return simpleBean;
	}
	
	private ClientEntity bulidEntity (ClientBean client) {
		ClientEntity entity = new ClientEntity();
		entity.setRegistDate(client.getRegistDate());
		entity.setId(client.getId());
		entity.setAddress(client.getAddress());
		entity.setRegistName(client.getRegistName());
		entity.setPassword(client.getPassword());
		entity.setRemark(client.getRemark());
		return entity;
	}
	
	public ClientBean bulidBean(ClientEntity entity) {
		ClientBean bean = new ClientBean();
		bean.setRegistDate(entity.getRegistDate());
		bean.setId(entity.getId());
		bean.setAddress(entity.getAddress());
		bean.setRegistName(entity.getRegistName());
		bean.setPassword(entity.getPassword());
		bean.setRemark(entity.getRemark());
		return bean;
	}
	
}
