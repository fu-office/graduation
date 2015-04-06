package com.lbyt.client.persistservice;

import java.util.Date;
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
import com.lbyt.client.dao.IGiftDao;
import com.lbyt.client.entity.AreaEntity;
import com.lbyt.client.entity.GiftEntity;

@Repository
public class GiftPersistService {
	@Autowired
	private IGiftDao giftDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void save(List<GiftEntity> list) {
		for (GiftEntity entity : list) {
			save(entity);
		}
	}
	
	@Transactional
	public GiftEntity save(GiftEntity entity) {
		return giftDao.save(entity);
	}
	
	@Transactional
	public void delete(GiftEntity entity) {
		giftDao.delete(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public GiftEntity findByPhone(String phone) {
		return giftDao.findByPhone(phone);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public Page<GiftEntity> findByStartDateAndEndDate(final Date startDate, final Date endDate, PageBean page){
		return giftDao.findAll(new Specification<AreaEntity>(){

			@Override
			public Predicate toPredicate(Root<AreaEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				Path<Date> date = root.get("date");
				if (startDate != null) {
					predicate = cb.greaterThanOrEqualTo(date, startDate);
				}
				if (endDate != null) {
					predicate = predicate == null ?  cb.greaterThanOrEqualTo(date, startDate) : 
						cb.and(predicate, cb.lessThanOrEqualTo(date, endDate));
				}
				return predicate;
			}
			
		},new PageRequest(page.getPageNumber() - 1, page.getPageSize(), new Sort(Direction.ASC,
				GiftEntity.ID)));
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<GiftEntity> findByStartDateAndEndDate(final Date startDate, final Date endDate){
		return giftDao.findAll(new Specification<AreaEntity>(){

			@Override
			public Predicate toPredicate(Root<AreaEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				Path<Date> date = root.get("date");
				if (startDate != null) {
					predicate = cb.greaterThanOrEqualTo(date, startDate);
				}
				if (endDate != null) {
					predicate = predicate == null ?  cb.greaterThanOrEqualTo(date, startDate) : 
						cb.and(predicate, cb.lessThanOrEqualTo(date, endDate));
				}
				return predicate;
			}
			
		});
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public GiftEntity findById(GiftEntity gift) {
		return giftDao.findById(gift.getId());
	}
	
}
