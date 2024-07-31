package com.spring.coffee.dailyboard.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@Alias("dailyBoardFilesInfo")
public class DailyBoardFilesInfoVo {
	private int boardNo;
	private String fileName;
}
