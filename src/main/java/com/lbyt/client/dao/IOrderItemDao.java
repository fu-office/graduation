package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.OrderItemEntity;

public interface IOrderItemDao extends Repository<OrderItemEntity, Integer>{
	
	void save(OrderItemEntity entity);
	
	List<OrderItemEntity> findAll();
	
	List<OrderItemEntity> findAll(Specification<OrderItemEntity> specification);
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Query("delete from OrderItemEntity a where a.id = ?1")
	void deleteById(Integer id);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from OrderItemEntity a where a.id = ?1")
	OrderItemEntity findById(Integer id);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from OrderItemEntity a where a.orderId = ?1")
	List<OrderItemEntity> findByOrderId(Integer id);
	
}
