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
import com.lbyt.client.dao.IStockOrderDao;
import com.lbyt.client.entity.StockOrderEntity;
import com.lbyt.client.util.StringUtil;

@Repository
public class StockOrderPersistService {
	@Autowired
	private IStockOrderDao stockOrderDao;
	
	@Transactional
	public void save(StockOrderEntity entity) {
		stockOrderDao.save(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StockOrderEntity> findAll() {
		return stockOrderDao.findAll();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StockOrderEntity> findByProductId(Integer id){
		return stockOrderDao.findByProductId(id);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public Page<StockOrderEntity> search(final StockOrderEntity entity, PageBean pageBean){
		return stockOrderDao.findAll(new Specification<StockOrderEntity>(){

			@Override
			public Predicate toPredicate(Root<StockOrderEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				Path<Date> createDate = root.get("createDate");
				Path<String> type = root.get("type");
				if (!StringUtil.isEmpty(entity.getType())) {
					predicate = cb.equal(type, entity.getType());
				}
				if (entity.getCreateDate() != null) {
					predicate = predicate == null ? cb.equal(createDate, entity.getCreateDate()) :  cb.and(predicate, cb.equal(createDate, entity.getCreateDate()));
				}
				return predicate;
			}
			
		}, new PageRequest(pageBean.getPageNumber() - 1, pageBean.getPageSize(), new Sort(Direction.ASC, "createDate")));
	}
}
