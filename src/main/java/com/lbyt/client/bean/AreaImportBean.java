package com.lbyt.client.bean;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class AreaImportBean extends FileUploadJsonBean {
	private static final long serialVersionUID = 8593654481992450658L;

	private MultipartFile file;
	
	@JsonIgnore
	public MultipartFile getFile() {
		return this.file;
	}

	@JsonProperty("file")
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
