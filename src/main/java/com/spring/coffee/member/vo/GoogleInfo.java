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
public class GoogleInfo {
	private String id;
	private String email;
	private String verified_email;
	private String name;
	private String given_name;
	private String picture;
	private String locale;
}
