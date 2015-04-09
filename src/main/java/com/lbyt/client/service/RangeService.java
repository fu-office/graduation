package com.lbyt.client.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.RangeBean;
import com.lbyt.client.entity.RangeEntity;
import com.lbyt.client.persistservice.RangePersistService;

@Service
public class RangeService {
	
	@Autowired
	private RangePersistService rangePersist;
	
	
	public List<RangeBean> findAll() {
		List<RangeEntity> entities = rangePersist.findAll();
		List<RangeBean> list = new ArrayList<RangeBean>();
		for (RangeEntity entity : entities) {
			list.add(bulidBean(entity));
		}
		return list;
	}
	
	public RangeBean saveOrUpdate(RangeBean range) {
		RangeEntity entity = bulidEntity(range); 
		rangePersist.save(entity);
		range.setId(entity.getId());
		range.setSuccess(true);
		return range;
	}

	
	public JsonBean save(List<RangeBean> list) {
		JsonBean json = new JsonBean();
		for (RangeBean order : list) {
			saveOrUpdate(order);
		}
		json.setSuccess(true);
		return json;
	}
	
	public RangeBean delete(RangeBean client) {
		rangePersist.deleteById(bulidEntity(client));
		client.setSuccess(true);
		return client;
	}
	
	private RangeEntity bulidEntity (RangeBean client) {
		RangeEntity entity = new RangeEntity();
		entity.setId(client.getId());
		entity.setName(client.getName());
		entity.setParent(client.getParent());
		return entity;
	}
	
	public RangeBean bulidBean(RangeEntity entity) {
		RangeBean bean = new RangeBean();
		bean.setId(entity.getId());
		bean.setName(entity.getName());
		bean.setParent(entity.getParent());
		return bean;
	}
	
}
