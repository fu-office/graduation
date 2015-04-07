package com.lbyt.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.OrderBean;
import com.lbyt.client.bean.OrderSearchBean;
import com.lbyt.client.entity.OrderEntity;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.persistservice.OrderPersistService;
import com.lbyt.client.util.CommUtil;

@Service
public class OrderService {
	
	@Autowired
	private OrderPersistService orderPersist;
	
	
	public List<OrderBean> findAll() {
		List<OrderEntity> entities = orderPersist.findAll();
		List<OrderBean> list = new ArrayList<OrderBean>();
		for (OrderEntity entity : entities) {
			list.add(bulidBean(entity));
		}
		return list;
	}
	
	public OrderBean saveOrUpdate(OrderBean order) {
		if (order.getId() == null) {
			String address = order.getAddress();
			if (CommUtil.isEmpty(address)) {
				order.setSuccess(false);
				ErrorBean e = ErrorBean.getInstance();
				e.setMessage("配送地址不能为空");
				order.getErrors().add(e);
				return order;
			}
			order.setDate(new Date());
			return save(order);
		} else {
			return update(order);
		}
	}

	public OrderBean save(OrderBean order) {
		OrderEntity entity = bulidEntity(order);
		orderPersist.save(entity);
		order.setSuccess(true);
		order.setId(entity.getId());
		return order;
	}
	
	public JsonBean save(List<OrderBean> list) {
		JsonBean json = new JsonBean();
		for (OrderBean order : list) {
			save(order);
		}
		json.setSuccess(true);
		return json;
	}
	
	public OrderBean update(OrderBean order) {
		OrderBean storeBean = findById(order);
		if (storeBean != null) {
			save(storeBean);
			storeBean.setSuccess(true);
			return storeBean;
		} else {
			order.setSuccess(false);
			ErrorBean error = new ErrorBean();
			error.setMessage("该订单不存在，或已删除");
			order.getErrors().add(error);
		}
		return order;
	}
	
	public OrderSearchBean search(OrderSearchBean order) {
		OrderSearchBean json = new OrderSearchBean();
		OrderEntity entity = new OrderEntity();
		List<OrderEntity> list =  orderPersist.findByProvAndCityAndShopState(entity);
		for (OrderEntity enti : list) {
			json.getList().add(bulidBean(enti));
		}
		json.setSuccess(true);
		return json;
	}
	
	public OrderBean delete(OrderBean order) {
		orderPersist.deleteById(bulidEntity(order));
		order.setSuccess(true);
		return order;
	}
	
	public OrderBean findById(OrderBean order) {
		OrderEntity entity = orderPersist.findById(bulidEntity(order));
		return bulidBean(entity);
	}
	
	private OrderEntity bulidEntity (OrderBean order) {
		OrderEntity entity = new OrderEntity();
		entity.setDate(order.getDate());
		entity.setId(order.getId());
		entity.setAddress(order.getAddress());
		entity.setDeliveryTime(order.getDeliveryTime());
		entity.setClientId(order.getClientId());
		entity.setStatus(order.getStatus());
		entity.setPhone(order.getPhone());
		entity.setPayStatus(order.getPayStatus());
		entity.setPayMethod(order.getPayMethod());
		entity.setName(order.getName());
		return entity;
	}
	
	public OrderBean bulidBean(OrderEntity entity) {
		OrderBean bean = new OrderBean();
		bean.setDate(entity.getDate());
		bean.setId(entity.getId());
		bean.setAddress(entity.getAddress());
		bean.setDeliveryTime(entity.getDeliveryTime());
		bean.setClientId(entity.getClientId());
		bean.setStatus(entity.getStatus());
		bean.setPhone(entity.getPhone());
		bean.setPayStatus(entity.getPayStatus());
		bean.setPayMethod(entity.getPayMethod());
		bean.setName(entity.getName());
		return bean;
	}
	
}
