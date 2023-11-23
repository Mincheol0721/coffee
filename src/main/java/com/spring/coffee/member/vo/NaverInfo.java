package com.spring.coffee.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfo {
	private String resultCode;
	private String message;
	private Response response;
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Response {
		private String id;
		private String nickname;
		private String profile_image;
		private String email;
		private String mobile;
		private String mobile_e164;
		private String name;
	}
}
