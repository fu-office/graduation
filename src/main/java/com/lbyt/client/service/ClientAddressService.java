package com.lbyt.client.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbyt.client.bean.ClientAddressBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.entity.ClientAddrEntity;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.persistservice.ClientAddressPersistService;

@Service
public class ClientAddressService {
	
	@Autowired
	private ClientAddressPersistService clientAddressPersist;
	
	
	public List<ClientAddressBean> findAll() {
		List<ClientAddrEntity> entities = clientAddressPersist.findAll();
		List<ClientAddressBean> list = new ArrayList<ClientAddressBean>();
		for (ClientAddrEntity entity : entities) {
			list.add(bulidBean(entity));
		}
		return list;
	}
	
	public ClientAddressBean saveOrUpdate(ClientAddressBean client) {
		ClientAddrEntity entity = bulidEntity(client);
		clientAddressPersist.save(entity);
		client = bulidBean(entity);
		client.setSuccess(true);
		return client;
	}

	public JsonBean save(List<ClientAddressBean> list) {
		JsonBean json = new JsonBean();
		for (ClientAddressBean order : list) {
			saveOrUpdate(order);
		}
		json.setSuccess(true);
		return json;
	}
	
	public ClientAddressBean update(ClientAddressBean client) {
		ClientAddressBean storeBean = findById(client);
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
	
	public ClientAddressBean delete(ClientAddressBean client) {
		clientAddressPersist.deleteById(bulidEntity(client));
		client.setSuccess(true);
		return client;
	}
	
	public ClientAddressBean findById(ClientAddressBean client) {
		ClientAddrEntity entity = clientAddressPersist.findById(bulidEntity(client));
		return bulidBean(entity);
	}
	
	public ClientAddressBean findByClient(ClientAddressBean client){
		List<ClientAddrEntity> list = clientAddressPersist.findByClient(bulidEntity(client));
		if (list.size() > 0) {
			client = bulidBean(list.get(0));
		}
		client.setSuccess(true);
		return client;
	}
	
	private ClientAddrEntity bulidEntity (ClientAddressBean client) {
		ClientAddrEntity entity = new ClientAddrEntity();
		entity.setId(client.getId());
		entity.setClientId(client.getClientId());
		entity.setArea(client.getArea());
		entity.setDepartment(client.getDepartment());
		entity.setFloor(client.getFloor());
		entity.setRoom(client.getRoom());
		return entity;
	}
	
	public ClientAddressBean bulidBean(ClientAddrEntity entity) {
		ClientAddressBean bean = new ClientAddressBean();
		bean.setId(entity.getId());
		bean.setClientId(entity.getClientId());
		bean.setArea(entity.getArea());
		bean.setDepartment(entity.getDepartment());
		bean.setFloor(entity.getFloor());
		bean.setRoom(entity.getRoom());
		return bean;
	}
	
}
