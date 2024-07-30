package com.spring.coffee.dailyboard.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@Alias("dailyBoardInfo")
public class DailyBoardVO {
	// 글번호
	private int no;

	// 글제목
	private String title;

	// 글내용
	private String content;

	// 작성자
	private String id;
	private String nickname; // 작성자의 닉네임으로 표기하기 위한 임시 변수

	// 작성일
	private LocalDateTime writeDate;

	// 조회수
	private int readCount;

	// 파일명
	private String fileName;
}
