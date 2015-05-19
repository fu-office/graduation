package com.lbyt.client.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.OrderBean;
import com.lbyt.client.bean.OrderItemBean;
import com.lbyt.client.bean.OrderSearchBean;
import com.lbyt.client.bean.PageBean;
import com.lbyt.client.bean.StockOrderBean;
import com.lbyt.client.entity.OrderEntity;
import com.lbyt.client.entity.OrderItemEntity;
import com.lbyt.client.enums.OrderStatusEnum;
import com.lbyt.client.enums.StockTypeEnum;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.persistservice.OrderPersistService;
import com.lbyt.client.util.CommUtil;
import com.lbyt.client.util.StringUtil;

@Service
public class OrderService {
	
	@Autowired
	private OrderPersistService orderPersist;
	
	@Autowired
	private StockService stockService;
	
	public List<OrderBean> findAll() {
		List<OrderEntity> entities = orderPersist.findAll();
		List<OrderBean> list = new ArrayList<OrderBean>();
		for (OrderEntity entity : entities) {
			list.add(buildBean(entity));
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
		OrderEntity entity = buildEntity(order);
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
			storeBean.setStatus(order.getStatus());
			storeBean.setPayStatus(order.getPayStatus());
			storeBean.setDeliveryId(order.getDeliveryId());
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
		PageBean pageBean = new PageBean();
		pageBean.setPageNumber(order.getPageNumber());
		pageBean.setPageSize(order.getPageSize());
		Page<OrderEntity> page =  orderPersist.search(bulidEntity(order), pageBean);
		for (OrderEntity enti : page.getContent()) {
			json.getList().add(buildBean(enti));
		}
		json.setSuccess(true);
		return json;
	}
	
	public OrderBean delete(OrderBean order) {
		orderPersist.deleteById(buildEntity(order));
		order.setSuccess(true);
		return order;
	}
	
	public OrderBean findById(OrderBean order) {
		OrderEntity entity = orderPersist.findById(buildEntity(order));
		OrderBean bean = buildBean(entity);
		bean.setSuccess(true);
		return bean;
	}
	
	private OrderEntity buildEntity (OrderBean order) {
		OrderEntity entity = new OrderEntity();
		entity.setDate(order.getDate());
		entity.setId(order.getId());
		entity.setAddress(order.getAddress());
		entity.setDeliveryTime(order.getDeliveryTime());
		entity.setDeliveryDate(order.getDeliveryDate());
		entity.setClientId(order.getClientId());
		entity.setStatus(order.getStatus());
		entity.setPhone(order.getPhone());
		entity.setPayStatus(order.getPayStatus());
		entity.setPayMethod(order.getPayMethod());
		entity.setName(order.getName());
		entity.setArea(order.getArea());
		entity.setItems(bulidOrderItemEntities(order.getItems()));
		entity.setTotal(order.getTotal());
		entity.setDeliveryId(order.getDeliveryId());
		return entity;
	}
	
	public OrderBean buildBean(OrderEntity entity) {
		OrderBean bean = new OrderBean();
		bean.setDate(entity.getDate());
		bean.setId(entity.getId());
		bean.setAddress(entity.getAddress());
		bean.setDeliveryDate(entity.getDeliveryDate());
		bean.setDeliveryTime(entity.getDeliveryTime());
		bean.setClientId(entity.getClientId());
		bean.setStatus(entity.getStatus());
		bean.setPhone(entity.getPhone());
		bean.setPayStatus(entity.getPayStatus());
		bean.setPayMethod(entity.getPayMethod());
		bean.setName(entity.getName());
		bean.setArea(entity.getArea());
		bean.setItems(bulidOrderItemBeans(entity.getItems()));
		bean.setTotal(entity.getTotal());
		bean.setDeliveryId(entity.getDeliveryId());
		return bean;
	}
	
	public OrderItemBean bulidOrderItemBean(OrderItemEntity entity){
		OrderItemBean bean = new OrderItemBean();
		bean.setOrderId(entity.getOrderId());
		bean.setPrice(entity.getProdPrice());
		bean.setNum(entity.getNum());
		bean.setProductId(entity.getProdId());
		bean.setProductName(entity.getProdName());
		bean.setId(entity.getId());
		return bean;
	}
	
	public List<OrderItemBean> bulidOrderItemBeans(List<OrderItemEntity> enties){
		List<OrderItemBean> list = new ArrayList<OrderItemBean>();
		for (OrderItemEntity entity : enties) {
			list.add(bulidOrderItemBean(entity));
		}
		return list;
	}
	
	public OrderItemEntity bulidOrderItemEntity(OrderItemBean bean){
		OrderItemEntity entity = new OrderItemEntity();
		entity.setId(bean.getId());
		entity.setNum(bean.getNum());
		entity.setProdId(bean.getProductId());
		entity.setProdPrice(bean.getPrice());
		entity.setOrderId(bean.getOrderId());
		entity.setProdName(bean.getProductName());
		return entity;
	}
	
	public List<OrderItemEntity> bulidOrderItemEntities(List<OrderItemBean> beans){
		List<OrderItemEntity> list = new ArrayList<OrderItemEntity>();
		for (OrderItemBean bean : beans) {
			list.add(bulidOrderItemEntity(bean));
		}
		return list;
	}
	
	public OrderEntity bulidEntity(OrderSearchBean order){
		OrderEntity entity = new OrderEntity();
		entity.setDate(order.getCreateDate());
		entity.setId(order.getId());
		entity.setClientId(order.getClientId());
		entity.setStatus(order.getStatus());
		entity.setName(order.getClientName());
		entity.setArea(order.getArea());
		return entity;
	}

	public OrderSearchBean getOrdersByClient(OrderSearchBean order) {
		OrderSearchBean json = new OrderSearchBean();
		if (null != order.getClientId()) {
			List<OrderEntity> list = orderPersist.findByClientId(bulidEntity(order));
			for (OrderEntity entity : list) {
				json.getList().add(buildBean(entity));
			}
		}
		json.setSuccess(true);
		return json;
	}
	
	public OrderSearchBean getScheduleOrder(OrderSearchBean order){
		PageBean pageBean = new PageBean();
		pageBean.setPageNumber(order.getPageNumber());
		pageBean.setPageSize(order.getPageSize());
		Page<OrderEntity> list = orderPersist.searchSchedule(bulidEntity(order), pageBean);
		for (OrderEntity entity : list.getContent()) {
			order.getList().add(buildBean(entity));
		}
		order.setSuccess(true);
		return order;
	}

	public OrderBean updatePayStatus(OrderBean bean) {
		orderPersist.updatePayStatus(buildEntity(bean));
		bean.setSuccess(true);
		return bean;
	}

	public JsonBean cancelOrder(OrderBean order) {
		OrderEntity entity = orderPersist.findById(buildEntity(order));
		if (null != entity) {
			entity.setStatus(OrderStatusEnum.CANCEL.toString());
			orderPersist.save(entity);
			order.setSuccess(true);
		} else {
			order.setSuccess(false);
			order.getErrors().add(new ErrorBean("订单" + order.getId() + "不存在"));
		}
		return order;
	}

	public OrderBean updateOrderStatus(OrderBean bean) {
		OrderEntity entity = orderPersist.findById(buildEntity(bean));
		if (null != entity) {
			String status = StringUtil.isEmpty(bean.getStatus()) ? entity.getStatus() : bean.getStatus();
			if (OrderStatusEnum.isOneOfThem(status)) {
				entity.setStatus(status);
				orderPersist.save(entity);
				bean.setSuccess(true);
			} else {
				bean.setSuccess(false);
				bean.getErrors().add(new ErrorBean("订单状态属性不匹配"));
			}
		} else {
			bean.setSuccess(false);
			bean.getErrors().add(new ErrorBean("订单" + bean.getId() + "不存在"));
		}
		return bean;
	}
	
	public OrderBean distributionDelivery(OrderBean order){
			
		return order;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public OrderBean complete(OrderBean order) {
		OrderEntity entity = orderPersist.findById(buildEntity(order));
		entity.setStatus(OrderStatusEnum.COMPLETE.toString());
		orderPersist.save(entity);
		List<OrderItemEntity> items = entity.getItems();
		for (OrderItemEntity item : items) {
			StockOrderBean stockOrder = new StockOrderBean();
			stockOrder.setCreateDate(new Date());
			stockOrder.setNum(item.getNum());
			stockOrder.setProductId(item.getProdId());
			stockOrder.setProductName(item.getProdName());
			stockOrder.setRemark("订单出库:" + order.getId());
			stockOrder.setStockType(StockTypeEnum.OUTSTOCK.toString());
			stockService.saveStockOrder(stockOrder);
		}
		OrderBean json = buildBean(entity);
		json.setSuccess(true);
		return json;
	}

	
}
