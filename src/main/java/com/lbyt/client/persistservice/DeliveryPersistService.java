package com.lbyt.client.persistservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.dao.IDeliveryDao;
import com.lbyt.client.entity.DeliveryEntity;

@Repository
public class DeliveryPersistService {

	@Autowired
	private IDeliveryDao deliveryDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public DeliveryEntity save(DeliveryEntity entity){
		deliveryDao.save(entity);
		return entity;
	}
	
	@Transactional
	public List<DeliveryEntity> findAll(){
		 return deliveryDao.findAll();
	}

}
