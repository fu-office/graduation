package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.lbyt.client.entity.ProductEntity;

public interface IProductDao extends Repository<ProductEntity, Integer>{
	
	void save(ProductEntity entity);
	
	List<ProductEntity> findAll();
	
}
