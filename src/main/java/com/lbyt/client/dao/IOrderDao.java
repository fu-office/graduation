package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	Page<OrderEntity> findAll(Specification<OrderEntity> specification, Pageable page);
	
	List<OrderEntity> findAll(Specification<OrderEntity> specification);
	
	long count();
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Query("delete from OrderEntity a where a.id = ?1")
	void deleteById(Integer id);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from OrderEntity a where a.id = ?1")
	OrderEntity findById(Integer id);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from OrderEntity a where a.clientId = ?1 order by a.id desc")
	List<OrderEntity> findByClientId(Integer id);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from OrderEntity a where a.name = ?1")
	List<OrderEntity> findByName(String name);

	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Query("update OrderEntity a set a.payStatus = ?1 where a.id = ?2")
	int updatePayStatus(String payStatus, Integer id);
}
