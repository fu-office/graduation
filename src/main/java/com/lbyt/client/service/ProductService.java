package com.lbyt.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.ProductBean;
import com.lbyt.client.entity.ProductEntity;
import com.lbyt.client.persistservice.ProductPersistService;

@Service
public class ProductService {
	@Autowired
	private ProductPersistService persistservice;
	
	public JsonBean save(ProductBean bean){
		ProductEntity entity = bulidEntity(bean);
		persistservice.save(entity);
		bean.setId(entity.getId());
		bean.setSuccess(true);
		return bean;
	}
	
	public JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setSuccess(true);
		List<ProductEntity> entities = persistservice.findAll();
		for (ProductEntity entity : entities) {
			bean.getDatas().add(bulidBean(entity));
		}
		bean.setSuccess(true);
		return bean;
	}

	private ProductBean bulidBean(ProductEntity entity) {
		ProductBean bean = new ProductBean();
		bean.setId(entity.getId());
		bean.setCreateDate(entity.getDate());
		bean.setPrice(entity.getPrice());
		bean.setProductName(entity.getName());
		return bean;
	}

	private ProductEntity bulidEntity(ProductBean bean) {
		ProductEntity entity = new ProductEntity();
		entity.setId(bean.getId());
		entity.setDate(bean.getCreateDate());
		entity.setPrice(bean.getPrice());
		entity.setName(bean.getProductName());
		return entity;
	}
	
}
