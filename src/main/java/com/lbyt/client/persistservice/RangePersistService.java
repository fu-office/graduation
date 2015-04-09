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

import com.lbyt.client.dao.IRangeDao;
import com.lbyt.client.entity.RangeEntity;

@Repository
public class RangePersistService {
	@Autowired
	private IRangeDao rangeDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void save(List<RangeEntity> list) {
		for (RangeEntity entity : list) {
			save(entity);
		}
	}
	
	@Transactional
	public void save(RangeEntity entity) {
		rangeDao.save(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RangeEntity> findAll() {
		return rangeDao.findAll();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RangeEntity> findByProvAndCityAndShopState(final RangeEntity entity) {
		return rangeDao.findAll(new Specification<RangeEntity>(){

			@Override
			public Predicate toPredicate(Root<RangeEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
 				return predicate;
			}
			
		});
	}

	@Transactional
	public void deleteById(RangeEntity entity) {
		rangeDao.deleteById(entity.getId());
	}
	
}
