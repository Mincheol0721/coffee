package com.spring.coffee.dailyboard.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyBoardVO {
	private int no;
	private String title;
	private String content;
	private String writeDate;
	private int readCount;
	private String[] fileNames;
}
