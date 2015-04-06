package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.lbyt.client.entity.AreaEntity;
import com.lbyt.client.entity.GiftEntity;

public interface IGiftDao extends Repository<GiftEntity, Integer>{
	
	GiftEntity save(GiftEntity entity);

	void delete(GiftEntity entity);

	GiftEntity findByPhone(String phone);

	Page<GiftEntity> findAll(Specification<AreaEntity> specification, Pageable page);
	
	List<GiftEntity> findAll(Specification<AreaEntity> specification);

	GiftEntity findById(Integer id);
	
}

