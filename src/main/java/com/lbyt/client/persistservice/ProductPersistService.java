package com.lbyt.client.persistservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.dao.IProductDao;
import com.lbyt.client.entity.ProductEntity;

@Repository
public class ProductPersistService {
	
	@Autowired
	private IProductDao productDao;
	
	@Transactional
	public void save(ProductEntity entity){
		productDao.save(entity);
	}
	
	public List<ProductEntity> findAll(){
		return productDao.findAll()	;
	}
	
}
