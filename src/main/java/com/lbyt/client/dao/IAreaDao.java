package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.AreaEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

public interface IAreaDao extends Repository<AreaEntity, Integer>{
	
	void save(AreaEntity entity);
	
	List<AreaEntity> findAll();
	
	List<AreaEntity> findAll(Specification<AreaEntity> specification);
	
	List<AreaEntity> findByDetail(AreaEntity entity);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("SELECT a FROM AreaEntity a Where a.detail = ?1")
	List<AreaEntity> findByDetail(String detail);

	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Query("delete from AreaEntity a where a.id = ?1")
	void deleteById(Integer id);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from AreaEntity a where a.id = ?1")
	AreaEntity findById(Integer id);
	
}
