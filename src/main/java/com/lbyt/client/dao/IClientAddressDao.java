package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.ClientAddrEntity;

public interface IClientAddressDao extends Repository<ClientAddrEntity, Integer>{
	
	void save(ClientAddrEntity entity);
	
	List<ClientAddrEntity> findAll();
	
	List<ClientAddrEntity> findAll(Specification<ClientAddrEntity> specification);
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Query("delete from ClientAddrEntity a where a.id = ?1")
	void deleteById(Integer id);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from ClientAddrEntity a where a.id = ?1")
	ClientAddrEntity findById(Integer id);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("select a from ClientAddrEntity a where a.clientId = ?1")
	List<ClientAddrEntity> findByClientId(Integer clientId);
}
