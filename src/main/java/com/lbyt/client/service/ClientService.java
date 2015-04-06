package com.lbyt.client.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lbyt.client.bean.ClientBean;
import com.lbyt.client.bean.ClientSearchBean;
import com.lbyt.client.bean.JsonBean;
import com.lbyt.client.bean.PageBean;
import com.lbyt.client.constant.ClientConstants;
import com.lbyt.client.entity.ClientEntity;
import com.lbyt.client.error.ErrorBean;
import com.lbyt.client.persistservice.ClientPersistService;
import com.lbyt.client.util.BeanUtil;
import com.lbyt.client.util.CommUtil;
import com.lbyt.client.util.DateUtil;
import com.lbyt.client.util.ExcelUtil;
import com.lbyt.client.util.ExcelUtil.Cell;
import com.lbyt.client.util.ExcelUtil.Sheet;

@Service
public class ClientService {
	
	private static final String REGIST_DATE = "登记日期";
	
	private static final String CARD_NO = "卡号";
	
	private static final String CLIENT_NAME = "姓名";
	
	private static final String CLIENT_PHONE = "手机号";
	
	private static final String CLIENT_TEL = "电话";
	
	private static final String CLIENT_ADDR = "联系地址";
	
	private static final String POST_CODE = "邮编";
	
	private static final String BIRTHDAY = "生日";
	
	private static final String AREA = "区域市";
	
	private static final String PROVINCE = "区域省份";
	
	private static final String SHOP_NAME = "专柜名称";
	
	private static final int CONTENT_START_INDEX = 1;
	
	private int regist_date_index = -1;
	
	private int card_no_index = -1;
	
	private int client_name_index = -1;
	
	private int client_phone_index = -1;
	
	private int client_tel_index = -1;
	
	private int client_addr_index = -1;
	
	private int post_code_index = -1;
	
	private int birthday_index = -1;
	
	private int area_index = -1;
	
	private int province_index = -1;
	
	private int shop_name_index = -1;
	
	private int saveTimes = 0;
	
	private int maxSaveTimes = 7;
	
	@Autowired
	ClientPersistService clientPersistService;
	
	public ClientSearchBean importExcel(MultipartFile file){
		ClientSearchBean jsonBean = new ClientSearchBean();
		jsonBean.setSuccess(false);
		List<ClientEntity> entities = new ArrayList<ClientEntity>();
		Date modifyDate = new Date();
		try {
			List<Sheet> sheets = ExcelUtil.parseExcel(file.getInputStream());
			for (Sheet st : sheets) {
				restIndex();
				int last = st.getLastNumber();
				Cell[] titles = st.getRow(0);
				setCellIndexs(titles);
				for (int i = CONTENT_START_INDEX; i < last; i ++) {
					Cell[] cells = st.getRow(i);
					ClientEntity entity = new ClientEntity();
					entity.setCardNum(card_no_index == -1 || cells[card_no_index] == null ? null  :  cells[card_no_index].getValue());
					entity.setAddress(client_addr_index == -1 || cells[client_addr_index] == null ? null : cells[client_addr_index].getValue());
					entity.setCity(area_index == -1 || cells[area_index] == null ?  null : cells[area_index].getValue());
					entity.setProvince(province_index == -1 || cells[province_index] == null ?  null : cells[province_index].getValue());
					entity.setPostCode(post_code_index == -1 || cells[post_code_index] == null ? null : cells[post_code_index].getValue());
					entity.setName(client_name_index == -1 || cells[client_name_index] == null ? null : cells[client_name_index].getValue());
					entity.setPhoneNumber(client_phone_index == -1 || cells[client_phone_index] == null ? null : cells[client_phone_index].getValue());
					entity.setTelNumber(client_tel_index == -1 || cells[client_tel_index] == null ? null : cells[client_tel_index].getValue());
					entity.setShopName(shop_name_index == -1 || cells[shop_name_index] == null  ? null : cells[shop_name_index].getValue());
					try {
						entity.setBirthday(birthday_index == -1 || cells[birthday_index] == null  ? null : DateUtil.parseDate(cells[birthday_index].getValue()));
					} catch (ParseException e) {}
					try {
						entity.setRegisterDate(regist_date_index == -1 || cells[regist_date_index] == null  ? modifyDate : DateUtil.parseDate(cells[regist_date_index].getValue()));
					} catch (ParseException e) {}
					entity.setModifyDate(modifyDate);
					entity.setCardNum(generateCardNo());
//					clientPersistService.save(entity);
					entities.add(entity);
				}
			}
			jsonBean.setList(bulidClientList(entities));
			jsonBean.setSuccess(true);
		} catch (IOException e) {
			ErrorBean error = new ErrorBean();
			error.setMessage("文件读取失败，请确保文件内容");
			jsonBean.getErrors().add(error);
			jsonBean.setSuccess(false);
		}
		return jsonBean;
	}
	
	public ClientBean findByCardNo(ClientBean bean){
		ClientEntity entity = clientPersistService.findByCardNo(bulidEntity(bean));
		return bulidBean(entity);
	}
	
	public String[] getHeads() {
		return new String[]{
				REGIST_DATE,
				CARD_NO,
				CLIENT_NAME,
				CLIENT_PHONE,
				CLIENT_TEL,
				CLIENT_ADDR,
				POST_CODE,
				BIRTHDAY,
				PROVINCE,
				AREA,
				SHOP_NAME
		};
	}
	
	public List<List<Object>> getCells(ClientSearchBean bean){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<ClientEntity> entities = findAll(bean);
		// order 
		for (ClientEntity entity : entities) {
			List<Object> row = new ArrayList<Object>();
			row.add(DateUtil.date2String(entity.getRegisterDate()));
			row.add(entity.getCardNum());
			row.add(entity.getName());
			row.add(entity.getPhoneNumber());
			row.add(entity.getTelNumber());
			row.add(entity.getAddress());
			row.add(entity.getPostCode());
			row.add(DateUtil.date2String(entity.getBirthday()));
			row.add(entity.getProvince());
			row.add(entity.getCity());
			row.add(entity.getShopName());
			list.add(row);
		}
		return list; 
	}
	
	private List<ClientEntity> findAll(ClientSearchBean bean){
		ClientBean client = new ClientBean();
		client.setName(bean.getName());
		client.setProvince(bean.getProvince());
		client.setCity(bean.getCity());
		return clientPersistService.findAll(bulidEntity(client), bean.getShopState());
	}
	
	public ClientSearchBean search(ClientSearchBean bean){
		PageBean page = new PageBean();
		page.setPageNumber(bean.getPageNumber());
		page.setPageSize(bean.getPageSize());
		ClientBean client = new ClientBean();
		client.setName(bean.getName());
		client.setProvince(bean.getProvince());
		client.setCity(bean.getCity());
		client.setShopState(bean.getShopState());
		return search(client, page);
	}
	
	public ClientSearchBean search(ClientBean bean, PageBean page){
		Page<ClientEntity> pageEntity = clientPersistService.search(bulidEntity(bean), bean.getShopState(), page);
		List<ClientEntity> list = pageEntity.getContent();
		ClientSearchBean searchBean = new ClientSearchBean();
		for (ClientEntity entity : list) {
			searchBean.getList().add(bulidBean(entity));
		}
		searchBean.setSuccess(true);
		searchBean.setCount(pageEntity.getTotalElements());
		searchBean.setPageNumber(pageEntity.getNumber() + 1);
		searchBean.setPageSize(pageEntity.getSize());
		searchBean.setTotalPages(pageEntity.getTotalPages());
		return searchBean;
	}
	
	public ClientBean save(ClientBean bean) throws Exception {
		Date today = new Date();
		bean.setModifyDate(today);
		if (bean.getId() != null) {
			return update(bean);
		}
		bean.setRegisterDate(today);
		bean.setCardNum(generateCardNo());
		try {
			ClientEntity entity = clientPersistService.save(bulidEntity(bean));
			bean.setId(entity.getId());
			bean.setSuccess(true);
			saveTimes = 0;
		} catch(Exception e) {
			// try again
			if (saveTimes < maxSaveTimes) {
				saveTimes ++;
				save(bean);
			} else {
				throw new Exception("保存失败");
			}
		}
		return bean;
	}
	
	public ClientBean update(ClientBean bean) {
		ClientEntity entity = clientPersistService.findById(bean.getId());
		entity.setAddress(CommUtil.isEmpty(bean.getAddress()) ? entity.getAddress() : bean.getAddress());
		entity.setName(CommUtil.isEmpty(bean.getName()) ? entity.getName() : bean.getName());
		entity.setPhoneNumber(CommUtil.isEmpty(bean.getPhoneNumber()) ? entity.getPhoneNumber() : bean.getPhoneNumber());
		entity.setPostCode(CommUtil.isEmpty(bean.getPostCode()) ? entity.getPostCode() : bean.getPostCode());
		entity.setCity(CommUtil.isEmpty(bean.getCity()) ? entity.getCity() : bean.getCity());
		entity.setProvince(CommUtil.isEmpty(bean.getProvince()) ? entity.getProvince() : bean.getProvince());
		entity.setShopName(CommUtil.isEmpty(bean.getShopName()) ? entity.getShopName() : bean.getShopName());
		entity.setTelNumber(CommUtil.isEmpty(bean.getTelNumber()) ? entity.getTelNumber() :bean.getTelNumber());
		entity.setBirthday(bean.getBirthday() == null ? entity.getBirthday() : bean.getBirthday());
		bean = bulidBean(clientPersistService.save(entity));
		bean.setSuccess(true);
		return bean;
	}
	
	public JsonBean delete(ClientBean bean) {
		JsonBean json = new JsonBean();
		clientPersistService.delete(bulidEntity(bean));
		json.setSuccess(true);
		return json;
	}
	
	private ClientEntity bulidEntity(ClientBean bean) {
		ClientEntity entity = new ClientEntity();
		entity.setAddress(bean.getAddress());
		entity.setBirthday(bean.getBirthday());
		entity.setCardNum(bean.getCardNum());
		entity.setCity(bean.getCity());
		entity.setId(bean.getId());
		entity.setModifyDate(bean.getModifyDate());
		entity.setRegisterDate(bean.getRegisterDate());
		entity.setName(bean.getName());
		entity.setPhoneNumber(bean.getPhoneNumber());
		entity.setPostCode(bean.getPostCode());
		entity.setProvince(bean.getProvince());
		entity.setRemark(bean.getRemark());
		entity.setShopName(bean.getShopName());
		entity.setTelNumber(bean.getTelNumber());
		return entity;
	}
	
	public ClientBean bulidBean(ClientEntity entity){
		ClientBean bean = new ClientBean();
		bean.setAddress(entity.getAddress());
		bean.setBirthday(entity.getBirthday());
		bean.setCardNum(entity.getCardNum());
		bean.setCity(entity.getCity());
		bean.setId(entity.getId());
		bean.setModifyDate(entity.getModifyDate());
		bean.setName(entity.getName());
		bean.setPhoneNumber(entity.getPhoneNumber());
		bean.setPostCode(entity.getPostCode());
		bean.setProvince(entity.getProvince());
		bean.setRegisterDate(entity.getRegisterDate());
		bean.setRemark(entity.getRemark());
		bean.setShopName(entity.getShopName());
		bean.setTelNumber(entity.getTelNumber());
		return bean;
	}

	private void restIndex() {
		this.area_index = -1;
		this.birthday_index = -1;
		this.card_no_index = -1;
		this.client_addr_index = -1;
		this.client_name_index = -1;
		this.client_phone_index = -1;
		this.client_tel_index = -1;
		this.regist_date_index = -1;
		this.post_code_index = -1;
		this.shop_name_index = -1;
		this.province_index = -1;
	}
	
	private void setCellIndexs(Cell[] cells) {
		int length = cells.length, i =0;
		for (; i < length; i++) {
			Cell cell = cells[i];
			String str = cell.getValue();
			if  (str != null) {
				str = str.replaceAll(" ", "");
				if (REGIST_DATE.equals(str)) {
					this.regist_date_index = i;
				} else if (CARD_NO.equals(str)) {
					this.card_no_index = i;
				} else if (CLIENT_NAME.equals(str)) {
					this.client_name_index = i;
				} else if (CLIENT_PHONE.equals(str)) {
					this.client_phone_index = i;
				} else if (CLIENT_TEL.equals(str)) {
					this.client_tel_index = i;
				} else if (CLIENT_ADDR.equals(str)) {
					this.client_addr_index = i;
				} else if (POST_CODE.equals(str)) {
					this.post_code_index = i;
				} else if (BIRTHDAY.equals(str)) {
					this.birthday_index = i;
				} else if (AREA.equals(str)) {
					this.area_index = i;
				} else if (SHOP_NAME.equals(str)) {
					this.shop_name_index = i;
				} else if (PROVINCE.equals(str)) {
					this.province_index = i;
				}
			} 
		}
	}
	
	private String generateCardNo(){
		long timeStamp = (new Date()).getTime();
		String time = String.valueOf(timeStamp);
		time = time.substring(Math.max(time.length() - 10, 0));
		String str = "";
		int len = ClientConstants.TOKENS.length;
		for (int i = 0; i < 6; i++) {
			str += ClientConstants.TOKENS[(int) (Math.random() * len)];
		}
		str += time;
		return str;
	}
	
	private List<ClientBean> bulidClientList(List<ClientEntity> entities) {
		List<ClientBean> list = new ArrayList<ClientBean>();
		for (ClientEntity e : entities) {
			try {
				list.add((ClientBean) BeanUtil.bulidBean(new ClientBean(), e));
			} catch (IllegalAccessException | SecurityException
					| ClassNotFoundException | NoSuchMethodException
					| InvocationTargetException e1) {
				list.add(null);
			}
		}
		return list;
	}

}
