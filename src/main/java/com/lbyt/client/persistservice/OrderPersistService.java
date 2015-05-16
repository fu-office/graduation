package com.lbyt.client.persistservice;

import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.bean.PageBean;
import com.lbyt.client.dao.IOrderDao;
import com.lbyt.client.entity.OrderEntity;
import com.lbyt.client.enums.OrderStatusEnum;
import com.lbyt.client.util.StringUtil;

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
	public Page<OrderEntity> search(final OrderEntity entity, final PageBean pageBean) {
		return orderDao.findAll(new Specification<OrderEntity>(){

			@Override
			public Predicate toPredicate(Root<OrderEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Integer> id = root.get("id");
				Path<Date> createDate = root.get("date");
				Path<String> status = root.get("status");
				Path<String> area = root.get("area");
				Path<String> name = root.get("name");
				Predicate predicate = null;
				if (null != entity.getId()) {
					return cb.equal(id, entity.getId());
				}
				if (null != entity.getDate()) {
					predicate = predicate == null ? cb.equal(createDate, new Date(entity.getDate().getTime())) : cb.and(predicate, cb.equal(createDate, new Date(entity.getDate().getTime())));
				}
				if (!StringUtil.isEmpty(entity.getStatus())) {
					predicate = predicate == null ? cb.equal(status, entity.getStatus()) : cb.and(predicate, cb.equal(status, entity.getStatus()));
				}
				if (!StringUtil.isEmpty(entity.getName())) {
					predicate = predicate == null ? cb.equal(name, entity.getName()) : cb.and(predicate, cb.equal(name, entity.getName()));
				}
				if (!StringUtil.isEmpty(entity.getArea())) {
					predicate = predicate == null ? cb.equal(area, entity.getArea()) : cb.and(predicate, cb.equal(area, entity.getArea()));
				}
				if (predicate == null) {
					predicate = cb.isNotNull(root.get("id"));
				}
 				return predicate;
			}
			
		}, new PageRequest(pageBean.getPageNumber() - 1, pageBean.getPageSize(), new Sort(Direction.ASC, "date")));
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

	@Transactional(propagation = Propagation.SUPPORTS)
	public Page<OrderEntity> searchSchedule(final OrderEntity entity,
			final PageBean pageBean) {
		return orderDao.findAll(new Specification<OrderEntity>(){

			@Override
			public Predicate toPredicate(Root<OrderEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> status = root.get("status");
				Predicate predicate = null;
				if (StringUtil.isEmpty(entity.getStatus())) {
					predicate = cb.equal(status, OrderStatusEnum.UNORDER.toString());
					predicate = cb.or(predicate, cb.equal(status, OrderStatusEnum.ORDERED.toString()));
					predicate = cb.or(predicate, cb.equal(status, OrderStatusEnum.DELIVERED.toString()));
				} else {
					predicate = cb.equal(status, entity.getStatus());
				}
 				return predicate;
			}
			
		}, new PageRequest(pageBean.getPageNumber() - 1, pageBean.getPageSize(), new Sort(Direction.ASC, "deliveryDate")));
	}

	public OrderEntity updatePayStatus(OrderEntity entity) {
		if (orderDao.updatePayStatus(entity.getPayStatus(), entity.getId()) > 1) {
			return entity;
		}
		return null;
	}
	
}
