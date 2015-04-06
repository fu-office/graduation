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

import com.lbyt.client.dao.IAreaDao;
import com.lbyt.client.entity.AreaEntity;
import com.lbyt.client.util.CommUtil;

@Repository
public class AreaPersistService {
	@Autowired
	private IAreaDao areaDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void save(List<AreaEntity> list) {
		for (AreaEntity entity : list) {
			save(entity);
		}
	}
	
	@Transactional
	public void save(AreaEntity entity) {
		areaDao.save(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AreaEntity> findAll() {
		return areaDao.findAll();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public AreaEntity findByDetail(AreaEntity entity) {
		List<AreaEntity> list = areaDao.findByDetail(entity.getDetail());
		return list == null || list.size() == 0 ? null : list.get(0);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AreaEntity> findByProvAndCityAndShopState(final AreaEntity entity) {
		return areaDao.findAll(new Specification<AreaEntity>(){

			@Override
			public Predicate toPredicate(Root<AreaEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> prov = root.get("prov");
				Path<String> city = root.get("city");
				Path<String> state = root.get("shopState");
				Predicate predicate = null;
				if (!CommUtil.isEmpty(entity.getProv())) {
					predicate = cb.equal(prov, entity.getProv());
				}
				if (!CommUtil.isEmpty(entity.getCity())) {
					predicate = predicate == null ? cb.equal(city, entity.getCity()) : cb.and(predicate, cb.equal(city, entity.getCity()));		
				}
				if (!CommUtil.isEmpty(entity.getShopState())) {
					predicate = predicate == null ? cb.equal(state, entity.getShopState()) : cb.and(predicate, cb.equal(state, entity.getShopState()));
				}
 				return predicate;
			}
			
		});
	}

	@Transactional
	public void deleteById(AreaEntity entity) {
		areaDao.deleteById(entity.getId());
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public AreaEntity findById(AreaEntity entity){
		return areaDao.findById(entity.getId());
	}
	
}
