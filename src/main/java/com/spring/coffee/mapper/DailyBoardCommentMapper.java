package com.spring.coffee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.spring.coffee.dailyboard.vo.DailyBoardCommentVO;

@Mapper
public interface DailyBoardCommentMapper {
	
	void insertComment(DailyBoardCommentVO comment);
	
	@Select("SELECT boardNo, nickname, LEVEL, LPAD(' ', 2*(LEVEL-1)) || content AS content, no, parentNo, writeDate FROM dailyBoard_comment "
			+ " WHERE boardNo = #{no} START WITH parentNo = 0 CONNECT BY PRIOR no = parentNO")
	List<DailyBoardCommentVO> getCommentList(int no);
	
}
