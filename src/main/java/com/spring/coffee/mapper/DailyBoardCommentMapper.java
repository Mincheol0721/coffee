package com.spring.coffee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.coffee.dailyboard.vo.DailyBoardCommentVO;

@Mapper
public interface DailyBoardCommentMapper {
	
	@Select("SELECT boardNo, nickname, LEVEL, LPAD(' ', 2*(LEVEL-1)) || content AS content, no, parentNo, writeDate FROM dailyBoard_comment "
			+ " WHERE boardNo = #{no} START WITH parentNo = 0 CONNECT BY PRIOR no = parentNO")
	List<DailyBoardCommentVO> getCommentList(int no);

	void insertComment(DailyBoardCommentVO comment);

	@Update("UPDATE dailyboard_comment SET content=#{content} WHERE boardNo = #{boardNo} and no=#{no}")
	void updateComment(DailyBoardCommentVO comment);
	
	@Select("SELECT * FROM dailyboard_comment WHERE boardNo=#{boardNo} and no=#{no}")
	DailyBoardCommentVO getComment(int boardNo, int no);

	@Delete("DELETE FROM dailyboard_comment WHERE boardNo=#{boardNo} and no=#{no}")
	void delComment(int no, int boardNo);
	
	
}
