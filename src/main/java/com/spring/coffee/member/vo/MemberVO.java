package com.spring.coffee.member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	private String id;
	private String socialId;
	private String password;
	private String name;
	private String ssn;
	private String nickname;
	private String email;
	private String mobile;
	private String zipcode;
	private String roadAddr;
	private String detailAddr;
	private String jibunAddr;
	private String regDate;
	private String fileName;
}
