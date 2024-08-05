package com.spring.coffee.member.vo;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Component
@Alias("memberInfo")
public class MemberVO {
	// 유저 ID
	private String id;

	// SNS ID
	private String socialId;

	// 비밀번호
	private byte[] password;

	// 유저명
	private String name;

	// 유저 주민등록번호
	private String ssn;

	// 유저 닉네임
	private String nickname;

	// 유저 이메일
	private String email;

	// 유저 전화번호
	private String mobile;

	// 유저 등록일
	private LocalDateTime regDate;

	// 우편번호
	private String zipcode;

	// 도로명주소
	private String roadAddr;

	// 상세주소
	private String detailAddr;

	// 지번주소
	private String jibunAddr;

	// 파일명
	private String fileName;

	// 삭제여부
	private int delFlg;

	// 삭제 일자
	private LocalDateTime delDtm;
}
