package com.spring.coffee.smarteditor.vo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImgUploadVO {
	
	//photo_uploader.html의 form태그 내에 존재하는 file 태그의 name명과 일치시켜줌
	private MultipartFile filedata;
	//callback URL
	private String callback;
	//callback Function
	private String callback_fn;
	
	public MultipartFile getFiledata() {
		return filedata;
	}
	public void setFiledata(MultipartFile filedata) {
		this.filedata = filedata;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getCallback_fn() {
		return callback_fn;
	}
	public void setCallback_fn(String callback_fn) {
		this.callback_fn = callback_fn;
	}
	
	
}
