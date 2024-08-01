package com.spring.coffee.dailyboard.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.coffee.dailyboard.vo.DailyBoardFilesInfoVo;
import com.spring.coffee.dailyboard.vo.DailyBoardVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class DailyBoardDaoImpl {

	protected SqlSession sqlSession;

	@Resource(name = "sqlSessionFactoryBean")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}

	public List<DailyBoardVO> selectDailyBoardInfoList(Map paramMap) {
		return sqlSession.selectList("dailyBoard.selectDailyBoardInfoList", paramMap);
	}

	public DailyBoardVO selectDailyBoardInfoRow(DailyBoardVO dailyBoardVo) {
		return sqlSession.selectOne("dailyBoard.selectDailyBoardInfoRow", dailyBoardVo);
	}

	public int insertDailyBoardInfoRow(DailyBoardVO dailyBoardVo) {
		return sqlSession.insert("dailyBoard.insertDailyBoardInfo", dailyBoardVo);
	}

	public int updateDailyBoardInfoRow(DailyBoardVO dailyBoardVo) {
		return sqlSession.update("dailyBoard.updateDailyBoardInfo", dailyBoardVo);
	}

	public int deleteDailyBoardInfoRow(DailyBoardVO dailyBoardVo) {
		return sqlSession.delete("dailyBoard.deleteDailyBoardInfo", dailyBoardVo);
	}

	public int selectDailyBoardCountInfo(Map countMap) {
		return sqlSession.selectOne("dailyBoard.selectDailyBoardCountInfo", countMap);
	}

	public List<DailyBoardFilesInfoVo> selectFileInfo(DailyBoardFilesInfoVo filesInfoVo) {
		return sqlSession.selectList("dailyBoard.selectDailyBoardFilesInfo", filesInfoVo);
	}

	public int insertFileInfo(DailyBoardFilesInfoVo filesInfoVo) {
		return sqlSession.insert("dailyBoard.insertDailyBoardFilesInfo", filesInfoVo);
	}

	public void updateReadCount(int no) {
		sqlSession.update("dailyBoard.updateReadCountInfo", no);
	}

	public int selectDailyBoardCountInfo(String id) {
		return sqlSession.selectOne("dailyBoard.selectDailyBoardCountInfoById", id);
	}

	public int selectMaxNo() {
		return sqlSession.selectOne("dailyBoard.selectMaxNoInfo");
	}

	public int deleteFileInfo(DailyBoardFilesInfoVo dailyBoardFilesInfoVo) {
		return sqlSession.delete("dailyBoard.deleteDailyBoardFilesInfo", dailyBoardFilesInfoVo);
	}
}
