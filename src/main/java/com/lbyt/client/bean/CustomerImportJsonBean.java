package com.lbyt.client.bean;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class CustomerImportJsonBean extends FileUploadJsonBean {

	private static final long serialVersionUID = 7136919029242968412L;
	
	private MultipartFile file;

	@JsonIgnore
	public MultipartFile getFile() {
		return file;
	}

	@JsonProperty("file")
	public void setFile(final MultipartFile file) {
		this.file = file;
	}

}
