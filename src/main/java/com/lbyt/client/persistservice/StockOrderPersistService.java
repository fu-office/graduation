package com.lbyt.client.persistservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.dao.IStockOrderDao;
import com.lbyt.client.entity.StockOrderEntity;

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

	public List<StockOrderEntity> findByProductId(Integer id){
		return stockOrderDao.findByProductId(id);
	}
	
}
