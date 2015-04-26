package com.lbyt.client.persistservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.dao.IStockDao;
import com.lbyt.client.entity.StockEntity;
import com.lbyt.client.util.CommUtil;

@Repository
public class StockPersistService {
	@Autowired
	private IStockDao stockDao;
	
	@Transactional
	public void save(StockEntity entity) {
		stockDao.save(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<StockEntity> findAll() {
		return stockDao.findAll();
	}

	public StockEntity findByProductId(Integer id) {
		return stockDao.findByProductId(id);
	}

	public List<StockEntity> findByProdName(String productName) {
		return stockDao.findByProductName(CommUtil.filterLikeString(productName));
	}
	
	public StockEntity findById(Integer id){
		return stockDao.findById(id);
	}
	
}
