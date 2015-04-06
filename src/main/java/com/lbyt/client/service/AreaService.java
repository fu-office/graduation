package com.lbyt.client.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lbyt.client.bean.AreaBean;
import com.lbyt.client.bean.AreaSearchBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.entity.AreaEntity;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.persistservice.AreaPersistService;
import com.lbyt.client.util.CommUtil;
import com.lbyt.client.util.ExcelUtil;
import com.lbyt.client.util.ExcelUtil.Cell;
import com.lbyt.client.util.ExcelUtil.Sheet;

@Service
public class AreaService {
	
	private static final String HAS_SHOP = "有专柜";
	
	private static final String NO_SHOP = "无专柜";
	
	private static final int HEAD_TITLE_INDEX = 0;
	
	private static final int CONTENT_START_INDEX = 1;
	
	private static final String SHOP_STATE_NAME = "专柜状态";
	
	private static final String PROV_NAME = "区域省份";
	
	private static final String CITY_NAME = "区域市";
	
	private int shop_state_index = -1;
	
	private int prov_index = -1;
	
	private int city_index = -1;
	
	@Autowired
	private AreaPersistService areaPersist;
	
	public AreaSearchBean importArea(MultipartFile file) {
		AreaSearchBean json = new AreaSearchBean();
		List<AreaBean> list = json.getList();
		Date date = new Date();
		try {
			List<Sheet> sheets = ExcelUtil.parseExcel(file.getInputStream());
			for (Sheet sheet : sheets) {
				resetIndex();
				Cell[] title = sheet.getRow(HEAD_TITLE_INDEX);
				setCellIndex(title);
				int last = sheet.getLastNumber();
				for (int i = CONTENT_START_INDEX; i < last; i++) {
					Cell[] cell = sheet.getRow(i);
					AreaBean area = new AreaBean();
					area.setCity(city_index >= 0 && cell[city_index] != null ?cell[city_index].getValue() : null);
					area.setProv(prov_index >= 0 && cell[prov_index] != null ?cell[prov_index].getValue() : null);
					area.setShopState(shop_state_index >= 0 && cell[shop_state_index] != null ? cell[shop_state_index].getValue() : NO_SHOP);
					area.setDetail(area.getProv() + "-" + area.getCity());
					area.setDate(date);
					save(area);
					list.add(area);
				}
			}
			json.setSuccess(true);
		} catch (IOException e) {
			ErrorBean error = new ErrorBean();
			error.setMessage("文件解析失败，请检查文件格式和内容");
			error.setErrorCode("invildate file");
			json.getErrors().add(error);
			error = new ErrorBean();
			error.setMessage(e.getMessage());
			json.getErrors().add(error);
			json.setSuccess(false);
		}
		return json;
	}
	
	public List<AreaBean> findAll() {
		List<AreaEntity> entities = areaPersist.findAll();
		List<AreaBean> list = new ArrayList<AreaBean>();
		for (AreaEntity entity : entities) {
			list.add(bulidBean(entity));
		}
		return list;
	}
	
	public AreaBean saveOrUpdate(AreaBean area) {
		if (area.getId() == null) {
			String prov = area.getProv();
			String city = area.getCity();
			area.setShopState(area.getShopState() == null ? NO_SHOP : area.getShopState());
			if (CommUtil.isEmpty(prov) || CommUtil.isEmpty(city)) {
				area.setSuccess(false);
				ErrorBean e = ErrorBean.getInstance();
				e.setMessage("省,市不能为空");
				area.getErrors().add(e);
				return area;
			}
			area.setDate(new Date());
			area.setDetail(prov + "-" + city);
			return save(area);
		} else {
			return update(area);
		}
	}

	public AreaBean save(AreaBean area) {
		AreaEntity entity = bulidEntity(area);
		AreaEntity storeEntity = areaPersist.findByDetail(entity);
		if (storeEntity != null && storeEntity.getId() != null) {
			storeEntity.setShopState(entity.getShopState());
			entity = storeEntity;
		}
		areaPersist.save(entity);
		area.setSuccess(true);
		area.setId(entity.getId());
		return area;
	}
	
	public JsonBean save(List<AreaBean> list) {
		JsonBean json = new JsonBean();
		for (AreaBean area : list) {
			save(area);
		}
		json.setSuccess(true);
		return json;
	}
	
	public AreaBean update(AreaBean area) {
		AreaBean storeBean = findById(area);
		if (storeBean != null) {
			storeBean.setShopState(area.getShopState());
			save(storeBean);
			storeBean.setSuccess(true);
			return storeBean;
		} else {
			area.setSuccess(false);
			ErrorBean error = new ErrorBean();
			error.setMessage("找不到该地址，已删除或不存在");
			area.getErrors().add(error);
		}
		return area;
	}
	
	public AreaSearchBean search(AreaSearchBean area) {
		AreaSearchBean json = new AreaSearchBean();
		AreaEntity entity = new AreaEntity();
		entity.setProv(area.getProv());
		entity.setCity(area.getCity());
		entity.setShopState(area.getShopState());
		List<AreaEntity> list =  areaPersist.findByProvAndCityAndShopState(entity);
		for (AreaEntity enti : list) {
			json.getList().add(bulidBean(enti));
		}
		json.setSuccess(true);
		return json;
	}
	
	public AreaBean delete(AreaBean area) {
		areaPersist.deleteById(bulidEntity(area));
		area.setSuccess(true);
		return area;
	}
	
	public AreaBean findById(AreaBean area) {
		AreaEntity entity = areaPersist.findById(bulidEntity(area));
		return bulidBean(entity);
	}
	
	private AreaEntity bulidEntity (AreaBean area) {
		AreaEntity entity = new AreaEntity();
		entity.setCity(area.getCity());
		entity.setDate(area.getDate());
		entity.setDetail(area.getDetail());
		entity.setId(area.getId());
		entity.setProv(area.getProv());
		entity.setShopState(area.getShopState());
		return entity;
	}
	
	public AreaBean bulidBean(AreaEntity entity) {
		AreaBean bean = new AreaBean();
		bean.setCity(entity.getCity());
		bean.setDate(entity.getDate());
		bean.setId(entity.getId());
		bean.setDetail(entity.getDetail());
		bean.setProv(entity.getProv());
		bean.setShopState(entity.getShopState());
		return bean;
	}
	
	private void setCellIndex(Cell[] cells) {
		int length = cells.length, i = 0;
		for (; i < length; i ++) {
			Cell cell = cells[i];
			if (cell != null && cell.getValue() != null) {
				String str = cell.getValue().replaceAll(" ", "");
				if (PROV_NAME.equals(str)) {
					this.prov_index = i;
				} else if (CITY_NAME.equals(str)) {
					this.city_index = i;
				} else if (SHOP_STATE_NAME.equals(str)) {
					this.shop_state_index = i;
				}
			}
		}
	}
	
	private void resetIndex(){
		this.city_index = -1;
		this.prov_index = -1;
		this.shop_state_index = -1;
	}
	
	public List<List<Object>> getCells(AreaSearchBean json) {
		List<List<Object>> rows = new ArrayList<List<Object>>();
		List<AreaBean> list = search(json).getList();
		for (AreaBean bean : list) {
			List<Object> cells = new ArrayList<Object>();
			cells.add(bean.getProv());
			cells.add(bean.getCity());
			cells.add(bean.getShopState());
			rows.add(cells);
		}
		return rows;
	}
	
	public String[] getHeads(){
		String[] heads = new String[3];
		heads[0] = PROV_NAME;
		heads[1] = CITY_NAME;
		heads[2] = SHOP_STATE_NAME;
		return heads;
	}

}
