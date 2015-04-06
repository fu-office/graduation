package com.lbyt.client.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lbyt.client.bean.GiftBean;
import com.lbyt.client.bean.GiftSearchBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.PageBean;
import com.lbyt.client.entity.GiftEntity;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.persistservice.GiftPersistService;
import com.lbyt.client.util.CommUtil;
import com.lbyt.client.util.DateUtil;
import com.lbyt.client.util.ExcelUtil;
import com.lbyt.client.util.ExcelUtil.Cell;
import com.lbyt.client.util.ExcelUtil.Sheet;

@Service
public class GiftService {
	
	private static final int HEAD_TITLE_INDEX = 0;
	
	private static final int CONTENT_START_INDEX = 1;
	
	private static final String CLIENT_NAME = "姓名";
	
	private static final String CLIENT_PHONE = "电话";
	
	private static final String CLIENT_DATE = "登记日期";
	
	private static final String EXIST_PHONE_MESSAGE = "该联系电话已存在";
	
	private int name_index = -1;
	
	private int phone_index = -1;
	
	private int date_index = -1;
	
	@Autowired
	private GiftPersistService giftPersist;
	
	public GiftBean save(GiftBean gift) {
		if (gift.getId() != null) {
			return update(gift);
		}
		String name = gift.getName();
		String phone = gift.getPhone();
		if (CommUtil.isEmpty(phone) || CommUtil.isEmpty(name)) {
			gift.setSuccess(false);
			ErrorBean e = new ErrorBean();
			e.setMessage("姓名和电话不能为空");
			gift.getErrors().add(e);
		} else {
			GiftEntity entity = bulidEntity(gift);
			GiftEntity storeEntity = giftPersist.findByPhone(entity.getPhone());
			if (storeEntity != null && storeEntity.getId() != null) {
				gift.setSuccess(false);
				ErrorBean e = new ErrorBean();
				e.setMessage(EXIST_PHONE_MESSAGE);
				gift.getErrors().add(e);
			} else {
				entity = giftPersist.save(entity);
				gift.setId(entity.getId());
				gift.setSuccess(true);
			}
		}
		return gift;
	}
	
	
	public GiftBean update(GiftBean gift){
//		GiftEntity entityByPhone = giftPersist.findByPhone(gift.getPhone());
//		if (entityByPhone != null && !entityByPhone.getId().equals(gift.getId())) {
//			ErrorBean error = new ErrorBean();
//			error.setMessage(EXIST_PHONE_MESSAGE);
//			gift.setSuccess(false);
//			gift.getErrors().add(error);
//			return gift;
//		}
		GiftEntity entity = giftPersist.findById(bulidEntity(gift));
		try {
			entity.setName(gift.getName() == null ? entity.getName() : gift.getName());
			entity.setDate(gift.getDate() == null ? entity.getDate() : gift.getDate());
			entity.setPhone(gift.getPhone() == null ? entity.getPhone() : gift.getPhone());
			entity = giftPersist.save(entity);
			gift = bulidBean(entity);
			gift.setSuccess(true);
			return gift;
		} catch(DataAccessException e){
			System.out.print(e.getClass().getName());
			ErrorBean error = new ErrorBean();
			error.setMessage(EXIST_PHONE_MESSAGE);
			gift.setSuccess(false);
			gift.getErrors().add(error);
			return gift;
		}
	}
	
	public GiftSearchBean importExcel(MultipartFile file) {
		GiftSearchBean json = new GiftSearchBean();
		try {
			List<Sheet> sheets = ExcelUtil.parseExcel(file.getInputStream());
			for (Sheet sheet : sheets) {
				int lastRowIndex = sheet.getLastNumber(),
					i = CONTENT_START_INDEX;
				resetIndex();
				Cell[] title = sheet.getRow(HEAD_TITLE_INDEX);
				setCellIndex(title);
				for (; i < lastRowIndex; i ++) {
					Cell[] row = sheet.getRow(i);
					GiftBean giftBean = new GiftBean();
					giftBean.setName(name_index == -1 || row[name_index] == null ? null : row[name_index].getValue());
					giftBean.setPhone(phone_index == -1 || row[phone_index] == null ? null : row[phone_index].getValue());
					try {
						giftBean.setDate(date_index == -1 || row[date_index] == null ? null : DateUtil.parseDate(row[date_index].getValue()));
					} catch (ParseException e) {}
					save(giftBean);
				}
			}
			json.setSuccess(true);
		} catch (IOException e) {
			ErrorBean err = new ErrorBean();
			err.setMessage(e.getMessage());
			json.getErrors().add(err);
			json.setSuccess(false);
		}
		return json;
	}
	
	public JsonBean delete(GiftBean gift) {
		JsonBean json = new JsonBean();
		giftPersist.delete(bulidEntity(gift));
		json.setSuccess(true);
		return json;
	}
	
	public GiftSearchBean search(GiftSearchBean bean) {
		PageBean page = new PageBean();
		page.setPageNumber(bean.getPageNumber());
		page.setPageSize(bean.getPageSize());
		Page<GiftEntity> pageEntities = giftPersist.findByStartDateAndEndDate(bean.getStartDate(), bean.getEndDate(), page);
		List<GiftEntity> list = pageEntities.getContent();
		for (GiftEntity entity : list) {
			bean.getList().add(bulidBean(entity));
		}
		bean.setCount(pageEntities.getTotalElements());
		bean.setPageNumber(pageEntities.getNumber() + 1);
		bean.setPageSize(pageEntities.getSize());
		bean.setTotalPages(pageEntities.getTotalPages());
		bean.setSuccess(true);
		return bean;
	}
	
	public List<List<Object>> getCells(GiftSearchBean bean){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<GiftEntity> entities = findAllByDate(bean);
		for (GiftEntity entity : entities) {
			if (CommUtil.isEmpty(entity.getPhone())) {
				continue;
			}
			List<Object> row = new ArrayList<Object>();
			row.add(DateUtil.date2String(entity.getDate()));
			row.add(entity.getPhone());
			row.add(entity.getName());
			list.add(row);
		}
		return list;
	}
	
	public String[] getHeads(){
		return new String[]{
				CLIENT_DATE,
				CLIENT_PHONE,
				CLIENT_NAME
		};
	}
	
	private List<GiftEntity> findAllByDate(GiftSearchBean bean){
		return giftPersist.findByStartDateAndEndDate(bean.getStartDate(), bean.getEndDate());
	}
	
	private GiftEntity bulidEntity (GiftBean gift) {
		GiftEntity entity = new GiftEntity();
		entity.setClientId(gift.getClientId());
		entity.setDate(gift.getDate());
		entity.setId(gift.getId());
		entity.setName(gift.getName());
		entity.setPhone(gift.getPhone());
		return entity;
	}
	
	private GiftBean bulidBean(GiftEntity entity) {
		GiftBean bean = new GiftBean();
		bean.setDate(entity.getDate());
		bean.setId(entity.getId());
		bean.setClientId(entity.getClientId());
		bean.setName(entity.getName());
		bean.setPhone(entity.getPhone());
		return bean;
	}
	
	private void setCellIndex(Cell[] cells) {
		int length = cells.length, i = 0;
		for (; i < length; i ++) {
			Cell cell = cells[i];
			if (cell != null && cell.getValue() != null) {
				String t = cell.getValue();
				if (CLIENT_NAME.equals(t)) {
					this.name_index = i;
				} else if (CLIENT_DATE.equals(t)) {
					this.date_index = i;
				} else if (CLIENT_PHONE.equals(t)) {
					this.phone_index = i;
				}
			}
		}
	}
	
	private void resetIndex(){
		this.name_index = -1;
		this.phone_index = -1;
		this.date_index = -1;
	}


}
