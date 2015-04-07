package com.lbyt.client.persistservice;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.dao.IOrderDao;
import com.lbyt.client.entity.OrderEntity;

@Repository
public class OrderPersistService {
	@Autowired
	private IOrderDao orderDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void save(List<OrderEntity> list) {
		for (OrderEntity entity : list) {
			save(entity);
		}
	}
	
	@Transactional
	public void save(OrderEntity entity) {
		orderDao.save(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderEntity> findAll() {
		return orderDao.findAll();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderEntity> findByProvAndCityAndShopState(final OrderEntity entity) {
		return orderDao.findAll(new Specification<OrderEntity>(){

			@Override
			public Predicate toPredicate(Root<OrderEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> prov = root.get("prov");
				Path<String> city = root.get("city");
				Path<String> state = root.get("shopState");
				Predicate predicate = null;
 				return predicate;
			}
			
		});
	}

	@Transactional
	public void deleteById(OrderEntity entity) {
		orderDao.deleteById(entity.getId());
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public OrderEntity findById(OrderEntity entity){
		return orderDao.findById(entity.getId());
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderEntity> findByClientId(OrderEntity entity){
		return orderDao.findByClientId(entity.getClientId());
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderEntity> findByClientName(OrderEntity entity){
		return orderDao.findByName(entity.getName());
	}
	
}
