package com.lbyt.client.persistservice;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.dao.IClientAddressDao;
import com.lbyt.client.entity.ClientAddrEntity;
import com.lbyt.client.entity.ClientEntity;

@Repository
public class ClientAddressPersistService {
	@Autowired
	private IClientAddressDao clientAddressDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void save(List<ClientAddrEntity> list) {
		for (ClientAddrEntity entity : list) {
			save(entity);
		}
	}
	
	@Transactional
	public void save(ClientAddrEntity entity) {
		clientAddressDao.save(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ClientAddrEntity> findAll() {
		return clientAddressDao.findAll();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ClientAddrEntity> findByProvAndCityAndShopState(final ClientAddrEntity entity) {
		return clientAddressDao.findAll(new Specification<ClientAddrEntity>(){

			@Override
			public Predicate toPredicate(Root<ClientAddrEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
 				return predicate;
			}
			
		});
	}

	@Transactional
	public void deleteById(ClientAddrEntity entity) {
		clientAddressDao.deleteById(entity.getId());
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public ClientAddrEntity findById(ClientAddrEntity entity){
		return clientAddressDao.findById(entity.getId());
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public ClientAddrEntity findByClient(ClientEntity client){
		return clientAddressDao.findByClient(client);
	}
	
}
