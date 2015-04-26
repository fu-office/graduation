package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.StockEntity;

public interface IStockDao extends Repository<StockEntity, Integer>{
	
	void save(StockEntity entity);
	
	List<StockEntity> findAll();
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Query("select a from StockEntity a where a.id = ?1")
	StockEntity findById(Integer id);

	@Transactional(propagation = Propagation.REQUIRED)
	@Query("select a from StockEntity a where a.productId = ?1")
	StockEntity findByProductId(Integer productId);

	@Query("select a from StockEntity a where a.productName like ?1")
	List<StockEntity> findByProductName(String productName);
	
}
