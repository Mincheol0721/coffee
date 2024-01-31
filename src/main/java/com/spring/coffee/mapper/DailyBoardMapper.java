package com.spring.coffee.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.spring.coffee.dailyboard.vo.DailyBoardVO;

@Mapper
public interface DailyBoardMapper {
	
	@Select("SELECT * FROM ( SELECT ROWNUM AS rn, b.* FROM dailyBoard b WHERE ROWNUM <= #{endRow} ORDER BY no DESC) WHERE rn > #{startRow}")
	List<DailyBoardVO> getDailyBoardList(int startRow, int endRow);
	
	@Select("SELECT COUNT(*) FROM dailyBoard")
	int getDailyBoardCount();
	
	@Insert("INSERT INTO dailyBoard(no, title, content, nickname) "
			+ " VALUES (db_no.nextval, #{title}, #{content}, #{nickname})")
	void addDailyBoard(Map map);
	
	@Select("SELECT MAX(no) FROM dailyboard")
	int getDailyBoardNo();

	void insertDailyBoardImg(List<String> fileList);
	
	
}
