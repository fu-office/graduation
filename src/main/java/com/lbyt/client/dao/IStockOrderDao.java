package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.StockOrderEntity;

public interface IStockOrderDao extends Repository<StockOrderEntity, Integer>{
	
	void save(StockOrderEntity entity);
	
	List<StockOrderEntity> findAll();

	@Transactional(propagation = Propagation.REQUIRED)
	@Query("select a from StockOrderEntity a where a.productId = ?1")
	List<StockOrderEntity> findByProductId(Integer productId);

}
