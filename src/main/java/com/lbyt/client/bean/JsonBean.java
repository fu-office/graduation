package com.lbyt.client.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.lbyt.client.error.ErrorBean;

/**
 * 基础jsonbean, 可以继承它添加额外属性
 * @author zhenglianfu
 *
 */
public class JsonBean implements Serializable {
	private static final long serialVersionUID = -7052655343120461443L;

	private List<ErrorBean> errors = new ArrayList<ErrorBean>();
	
	private List<JsonBean> datas = new ArrayList<JsonBean>();
	
	private boolean success;
	
	private String token;

	@JsonProperty("errors")
	public List<ErrorBean> getErrors() {
		return errors;
	}

	@JsonIgnore
	public void setErrors(List<ErrorBean> errors) {
		this.errors = errors;
	}

	@JsonProperty("success")
	public boolean isSuccess() {
		return success;
	}

	@JsonIgnore
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@JsonProperty("token")
	public String getToken() {
		return token;
	}

	@JsonProperty("token")
	public void setToken(String token) {
		this.token = token;
	}

	@JsonProperty("datas")
	public List<JsonBean> getDatas() {
		return datas;
	}

	@JsonProperty("datas")
	public void setDatas(List<JsonBean> list) {
		this.datas = list;
	}

}
