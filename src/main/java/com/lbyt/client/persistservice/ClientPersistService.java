package com.lbyt.client.persistservice;

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
import com.lbyt.client.constant.ClientConstants;
import com.lbyt.client.dao.IClientDao;
import com.lbyt.client.entity.AreaEntity;
import com.lbyt.client.entity.ClientEntity;
import com.lbyt.client.util.CommUtil;

@Repository
public class ClientPersistService {
	
	@Autowired
	private IClientDao clientDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void save(List<ClientEntity> list) {
		for (ClientEntity entity : list) {
			save(entity);
		}
	}
	
	@Transactional
	public void delete(ClientEntity entity) {
		clientDao.delete(entity);
	}
	
	@Transactional
	public ClientEntity save(ClientEntity entity) {
		return clientDao.save(entity);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public Page<ClientEntity> search(final ClientEntity entity, final String shopState, final PageBean page) {
		return clientDao.findAll(new Specification<AreaEntity>(){

			@Override
			public Predicate toPredicate(Root<AreaEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> prov = root.get("province");
				Path<String> city = root.get("city");
				Path<String> shopName = root.get("shopName");
				Path<String> name = root.get("name");
				Predicate predicate = null;
				if (!CommUtil.isEmpty(entity.getProvince())) {
					predicate = cb.equal(prov, entity.getProvince());
				}
				if (!CommUtil.isEmpty(entity.getCity())) {
					predicate = predicate == null ? cb.equal(city, entity.getCity()) : 
							cb.and(predicate, cb.equal(city, entity.getCity()));
				}
				if (!CommUtil.isEmpty(shopState)) {
					if (ClientConstants.HAS_SHOP.equals(shopState)) {
						predicate = predicate == null ? cb.isNotNull(shopName) : cb.and(predicate, cb.isNotNull(shopName));
					} else {
						predicate = predicate == null ? cb.isNull(shopName) : cb.and(predicate, cb.isNull(shopName));
					}
				}
				if (!CommUtil.isEmpty(entity.getName())) {
					predicate = predicate == null ? cb.like(name, CommUtil.filterLikeString(entity.getName()), '\\') : 
						cb.and(predicate, cb.like(name, CommUtil.filterLikeString(entity.getName()), '\\')); // 转义字符  '\'
				}
				return predicate;
			}
			
		}, new PageRequest(page.getPageNumber() - 1, page.getPageSize(), new Sort(Direction.ASC,
				ClientEntity.ID)));
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ClientEntity> findAll(final ClientEntity entity, final String shopState){
		return clientDao.findAll(new Specification<AreaEntity>(){

			@Override
			public Predicate toPredicate(Root<AreaEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> prov = root.get("province");
				Path<String> city = root.get("city");
				Path<String> shopName = root.get("shopName");
				Path<String> name = root.get("name");
				Predicate predicate = null;
				if (!CommUtil.isEmpty(entity.getProvince())) {
					predicate = cb.equal(prov, entity.getProvince());
				}
				if (!CommUtil.isEmpty(entity.getCity())) {
					predicate = predicate == null ? cb.equal(city, entity.getCity()) : 
							cb.and(predicate, cb.equal(city, entity.getCity()));
				}
				if (!CommUtil.isEmpty(shopState)) {
					if (ClientConstants.HAS_SHOP.equals(shopState)) {
						predicate = predicate == null ? cb.isNotNull(shopName) : cb.and(predicate, cb.isNotNull(shopName));
					} else {
						predicate = predicate == null ? cb.isNull(shopName) : cb.and(predicate, cb.isNull(shopName));
					}
				}
				if (!CommUtil.isEmpty(entity.getName())) {
					predicate = predicate == null ? cb.like(name, CommUtil.filterLikeString(entity.getName()), '\\') : 
						cb.and(predicate, cb.like(name, CommUtil.filterLikeString(entity.getName()), '\\')); // 转义字符  '\'
				}
				return predicate;
			}
			
		});
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ClientEntity> findByNameAndPhoneAndAddr(ClientEntity client){
		return clientDao.findByNameAndPhoneNumberAndAddress(client.getName(), client.getPhoneNumber(), client.getAddress());
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public ClientEntity findByCardNo(ClientEntity entity) {
		return clientDao.findByCardNo(entity.getCardNum());
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public ClientEntity findById(Integer id) {
		return clientDao.findById(id);
	}
	
}
