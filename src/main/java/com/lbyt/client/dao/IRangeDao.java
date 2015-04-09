package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.RangeEntity;

public interface IRangeDao extends Repository<RangeEntity, Integer>{
	
	void save(RangeEntity entity);
	
	List<RangeEntity> findAll();
	
	List<RangeEntity> findAll(Specification<RangeEntity> specification);
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Query("delete from RangeEntity a where a.id = ?1")
	void deleteById(Integer id);
	
}
