package com.lbyt.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbyt.client.bean.DeliveryBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.entity.DeliveryEntity;
import com.lbyt.client.persistservice.DeliveryPersistService;

@Service
public class DeliveryService {

	@Autowired
	private DeliveryPersistService persistService;
	
	public JsonBean save(DeliveryBean bean){
		DeliveryEntity entity = bulidEntity(bean);
		persistService.save(entity);
		bean.setId(entity.getId());
		bean.setSuccess(true);
		return bean;
	}
	
	public JsonBean findAll(){
		JsonBean bean = new JsonBean();
		List<DeliveryEntity> entities = this.persistService.findAll();
		for (DeliveryEntity entity : entities) {
			bean.getDatas().add(bulidBean(entity));
		}
		return bean;
	}

	private DeliveryEntity bulidEntity(DeliveryBean bean) {
		DeliveryEntity entity = new DeliveryEntity();
		entity.setId(bean.getId());
		entity.setSex(bean.getSex());
		entity.setName(bean.getName());
		return entity;
	}
	
	public DeliveryBean bulidBean(DeliveryEntity entity){
		DeliveryBean bean = new DeliveryBean();
		bean.setId(entity.getId());
		bean.setName(entity.getName());
		bean.setSex(entity.getSex());
		return bean;
	}
}
