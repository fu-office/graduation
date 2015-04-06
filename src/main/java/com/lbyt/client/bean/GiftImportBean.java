package com.lbyt.client.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class GiftImportBean extends FileUploadJsonBean{

	private static final long serialVersionUID = -4875460481969224696L;
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	@JsonProperty("file")
	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
