package com.spring.coffee.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.coffee.dailyboard.vo.DailyBoardVO;
import com.spring.coffee.member.vo.MemberVO;

@Mapper
public interface DailyBoardMapper {
	
	List<DailyBoardVO> getDailyBoardList(Map<String, Object> paramMap);
	
	@Insert("INSERT INTO dailyBoard(no, title, content, nickname) "
			+ " VALUES (db_no.nextval, #{title}, #{content}, #{nickname})")
	void addDailyBoard(Map map);
	
	@Select("SELECT MAX(no) FROM dailyboard")
	int getDailyBoardNo();
	
	@Select("SELECT * FROM dailyBoard WHERE no=${no}")
	DailyBoardVO getDailyBoardDetail(int no);
	
	@Update("UPDATE dailyBoard SET readCount = readCount + 1 WHERE no = #{no}")
	void readCountUpdate(int no);

	@Select("SELECT * FROM member WHERE nickname=#{nickname}")
	MemberVO getOwnerDetail(String nickname);
	
	@Update("UPDATE dailyBoard SET title=#{title}, content=#{content} WHERE no=#{no}")
	void updateDailyBoard(Map map);
	
	@Delete("DELETE FROM dailyBoard WHERE no=#{no}") 
	void delDailyBoard(int no);
	
	void addFile(Map<String, Object> fileMap);
	
	int getDailyBoardCount(Map<String, Object> paramMap);
	
	
}
