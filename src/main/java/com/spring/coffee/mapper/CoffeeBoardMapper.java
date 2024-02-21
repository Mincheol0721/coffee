package com.spring.coffee.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.coffee.coffeeboard.vo.CoffeeBoardVO;
import com.spring.coffee.member.vo.MemberVO;

@Mapper
public interface CoffeeBoardMapper {

	@Select("SELECT * FROM ( SELECT ROWNUM AS rn, b.* FROM coffeeBoard b WHERE ROWNUM <= #{endRow} ORDER BY no DESC) WHERE rn > #{startRow}")
	List<CoffeeBoardVO> getCoffeeBoardList(Map<String, Object> paramMap);

	@Select("SELECT COUNT(*) FROM coffeeBoard")
	int getCoffeeBoardCount();

	List<CoffeeBoardVO> searchCoffeeBoardList(Map<String, Object> paramMap);

	int getSearchCoffeeBoardCount(Map<String, Object> countMap);
	
	@Update("UPDATE coffeeboard SET readCount = readCount + 1 WHERE no = #{no}")
	void readCountUpdate(int no);

//	@Select("SELECT no, title, content, nickName, writeDate, readCount, CAST(MULTISET (SELECT column_value FROM TABLE(fileName)) AS sys.odcivarchar2list) AS fileName "
//			+ "	        FROM coffeeBoard WHERE no = #{no}")
	CoffeeBoardVO getCoffeeBoardDetail(int no);
	
	@Select("SELECT * FROM member WHERE nickname = #{nickname}")
	MemberVO getOwnerDetail(String nickname);
	
	void insertCoffeeBoard(Map map);

	@Select("SELECT MAX(no) FROM coffeeboard")
	int getNewBoardNo();
	

}
