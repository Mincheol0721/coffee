package com.spring.coffee.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.spring.coffee.dailyboard.vo.DailyBoardVO;

@Mapper
public interface DailyBoardMapper {
	
	@Select("SELECT * FROM dailyBoard")
	List<DailyBoardVO> getDailyBoardList();
	
	@Insert("INSERT INTO dailyBoard(no, title, content, nickname, fileName) "
			+ " VALUES (#{no}, #{title}, #{content}, #{nickname}, #{fileName})")
	void addDailyBoard(Map map);
	
}
