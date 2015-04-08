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
	
	public ClientBean saveOrUpdate(ClientBean client) {
		if (client.getId() == null) {
			client.setRegistDate(new Date());
			return regist(client);
		} else {
			return update(client);
		}
	}

	public ClientBean regist(ClientBean client) {
		client.setRegistDate(new Date());
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
			saveOrUpdate(order);
		}
		json.setSuccess(true);
		return json;
	}
	
	public ClientBean update(ClientBean client) {
		ClientBean storeBean = findById(client);
		if (storeBean != null) {
			saveOrUpdate(storeBean);
			storeBean.setSuccess(true);
			return storeBean;
		} else {
			client.setSuccess(false);
			ErrorBean error = new ErrorBean();
			error.setMessage("该订单不存在，或已删除");
			client.getErrors().add(error);
		}
		return client;
	}
	
	public ClientSearchBean search(ClientSearchBean client) {
		ClientSearchBean json = new ClientSearchBean();
		ClientEntity entity = new ClientEntity();
		List<ClientEntity> list =  clientPersist.findByProvAndCityAndShopState(entity);
		for (ClientEntity enti : list) {
			json.getList().add(bulidBean(enti));
		}
		json.setSuccess(true);
		return json;
	}
	
	public ClientBean delete(ClientBean client) {
		clientPersist.deleteById(bulidEntity(client));
		client.setSuccess(true);
		return client;
	}
	
	public ClientBean findById(ClientBean client) {
		ClientEntity entity = clientPersist.findById(bulidEntity(client));
		return bulidBean(entity);
	}
	
	public ClientBean findByRegistName(ClientBean client){
		List<ClientEntity> list = clientPersist.findByRegistName(bulidEntity(client));
		if (0 == list.size()) {
			return null;
		}
		return bulidBean(list.get(0));
	}
	
	private boolean isRegisted(String registName){
		ClientEntity entity = new ClientEntity();
		entity.setRegistName(registName);
		List<ClientEntity> list = clientPersist.findByRegistName(entity);
		return 0 != list.size();
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
