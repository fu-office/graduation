package com.lbyt.client.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.entity.AreaEntity;
import com.lbyt.client.entity.ClientEntity;

public interface IClientDao extends Repository<ClientEntity, Integer>{
	
	@Transactional
	ClientEntity save(ClientEntity entity);
	
	Page<ClientEntity> findAll(Specification<AreaEntity> specification, Pageable page);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	List<ClientEntity> findAll(Specification<AreaEntity> specification);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("Select a From ClientEntity a where a.cardNum = ?1")
	ClientEntity findByCardNo(String cardNum);
	
	@Transactional(propagation = Propagation.SUPPORTS)
	long count();

	void delete(ClientEntity entity);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("Select a From ClientEntity a where a.id = ?1")
	ClientEntity findById(Integer id);

	@Transactional(propagation = Propagation.SUPPORTS)
	@Query("Select a From ClientEntity a where a.name = ?1 and a.phoneNumber = ?2 and a.address = ?3")
	List<ClientEntity> findByNameAndPhoneNumberAndAddress(String name, String phoneNumber, String address);
}
