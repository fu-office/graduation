package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.OrderEntity;

public interface IOrderDao extends Repository<OrderEntity, Integer>{
	
	void save(OrderEntity entity);
	
	List<OrderEntity> findAll();
	
	List<OrderEntity> findAll(Specification<OrderEntity> specification);
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Query("delete from OrderEntity a where a.id = ?1")
	void deleteById(Integer id);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from OrderEntity a where a.id = ?1")
	OrderEntity findById(Integer id);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from OrderEntity a where a.clientId = ?1")
	List<OrderEntity> findByClientId(Integer id);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from OrderEntity a where a.name = ?1")
	List<OrderEntity> findByName(String name);
}
