package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.ClientEntity;

public interface IClientDao extends Repository<ClientEntity, Integer>{
	
	void save(ClientEntity entity);
	
	List<ClientEntity> findAll();
	
	List<ClientEntity> findAll(Specification<ClientEntity> specification);
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Query("delete from ClientEntity a where a.id = ?1")
	void deleteById(Integer id);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from ClientEntity a where a.id = ?1")
	ClientEntity findById(Integer id);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from ClientEntity a where a.registName = ?1")
	List<ClientEntity> findByRegistName(String name);
}
