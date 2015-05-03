package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.lbyt.client.entity.DeliveryEntity;


public interface IDeliveryDao extends Repository<DeliveryEntity, Integer> {
	
	void save(DeliveryEntity entity);
	
	List<DeliveryEntity> findAll();
}
