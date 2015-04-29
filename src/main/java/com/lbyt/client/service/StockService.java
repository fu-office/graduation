package com.lbyt.client.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.PageBean;
import com.lbyt.client.bean.StockBean;
import com.lbyt.client.bean.StockOrderBean;
import com.lbyt.client.bean.StockOrderSearchBean;
import com.lbyt.client.entity.StockEntity;
import com.lbyt.client.entity.StockOrderEntity;
import com.lbyt.client.enums.StockTypeEnum;
import com.lbyt.client.persistservice.StockOrderPersistService;
import com.lbyt.client.persistservice.StockPersistService;

@Service
public class StockService {
	
	@Autowired
	private StockPersistService stockPersistService;
	
	@Autowired
	private StockOrderPersistService stockOrderPersistService;
	
	public JsonBean save(StockBean stock){
		stock.setSuccess(true);
		return stock;
	}
	
	public JsonBean findAll(){
		JsonBean bean = new JsonBean();
		List<StockEntity> list = stockPersistService.findAll();
		for (StockEntity entity : list) {
			bean.getDatas().add(bulidBean(entity));	
		}
		bean.setSuccess(true);
		return bean;
	}
	
	public StockBean findByProductId(StockBean bean){
		StockBean storeBean = bulidBean(stockPersistService.findByProductId(bean.getProdcutId()));
		storeBean.setSuccess(true);
		return storeBean;
	}
	
	public StockEntity bulidEntity(StockBean bean){
		StockEntity entity = new StockEntity();
		return entity;
	}
	
	public StockBean bulidBean(StockEntity entity){
		StockBean bean = new StockBean();
		bean.setId(entity.getId());
		bean.setNumber(entity.getNumber());
		bean.setProdcutId(entity.getProductId());
		bean.setProductName(entity.getProductName());
		return bean;
	}

	public JsonBean findByProdName(StockBean bean) {
		JsonBean json = new JsonBean();
		List<StockEntity> entities = stockPersistService.findByProdName(bean.getProductName());
		for (StockEntity entity : entities) {
			json.getDatas().add(bulidBean(entity));
		}
		json.setSuccess(true);
		return json;
	}

	public JsonBean saveStockOrder(StockOrderBean bean) {
		bean.setCreateDate(new Date());
		StockEntity stockEntity = stockPersistService.findByProductId(bean.getProductId());
		if (null == stockEntity) {
			// add first
			stockEntity = new StockEntity();
			stockEntity.setProductId(bean.getProductId());
			stockEntity.setProductName(bean.getProductName());
			stockEntity.setNumber(0);
			this.stockPersistService.save(stockEntity);
		}
		bean.setStockId(stockEntity.getId());
		if (StockTypeEnum.INSTOCK.toString().equals(bean.getStockType())) {
			stockEntity.setNumber(stockEntity.getNumber() + bean.getNum());
		} else if (StockTypeEnum.OUTSTOCK.toString().equals(bean.getStockType())) {
			stockEntity.setNumber(stockEntity.getNumber() - bean.getNum());
		}
		StockOrderEntity entity = bulidStockOrderEntity(bean);
		stockOrderPersistService.save(entity);
		stockPersistService.save(stockEntity);
		bean = bulidStockOrderBean(entity);
		bean.setSuccess(true);
		return bean;
	}

	private StockOrderEntity bulidStockOrderEntity(StockOrderBean bean) {
		StockOrderEntity entity = new StockOrderEntity();
		entity.setCreateDate(bean.getCreateDate() != null ? new java.sql.Date(bean.getCreateDate().getTime()) : null);
		entity.setId(bean.getId());
		entity.setProductId(bean.getProductId());
		entity.setNumber(bean.getNum());
		entity.setProductName(bean.getProductName());
		entity.setType(bean.getStockType());
		entity.setStockId(bean.getStockId());
		entity.setRemark(bean.getRemark());
		return entity;
	}
	
	private StockOrderBean bulidStockOrderBean(StockOrderEntity entity){
		StockOrderBean bean = new StockOrderBean();
		bean.setCreateDate(null != entity.getCreateDate() ? new java.util.Date(entity.getCreateDate().getTime()): null);
		bean.setId(entity.getId());
		bean.setNum(entity.getNumber());
		bean.setStockId(entity.getStockId());
		bean.setProductId(entity.getProductId());
		bean.setProductName(entity.getProductName());
		bean.setStockType(entity.getType());
		bean.setRemark(entity.getRemark());
		return bean;
	}

	public JsonBean searchStockOrders(StockOrderSearchBean bean) {
		PageBean page = new PageBean();
		page.setPageNumber(bean.getPageNumber());
		page.setPageSize(bean.getPageSize());
		StockOrderEntity entity = new StockOrderEntity();
		entity.setCreateDate(bean.getCreateDate() != null ? new java.sql.Date (bean.getCreateDate().getTime()) : null);
		entity.setType(bean.getStockType());
		Page<StockOrderEntity> result = stockOrderPersistService.search(entity, page);
		for (StockOrderEntity enti : result.getContent()) {
			bean.getList().add(bulidStockOrderBean(enti));
		}
		bean.setSuccess(true);
		return bean;
	}
	
	
}
