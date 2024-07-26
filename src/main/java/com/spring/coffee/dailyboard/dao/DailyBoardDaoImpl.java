//package com.spring.coffee.dailyboard.dao;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.stereotype.Repository;
//
//import com.spring.coffee.dailyboard.vo.DailyBoardVO;
//
//
//@Repository
//public class DailyBoardDaoImpl {
//
//	protected SqlSession sqlSession;
//
//	@Resource(name = "sqlSessionFactoryBean")
//	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
//		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
//	}
//
//	public List<DailyBoardVO> selectDailyBoardInfoList() {
//		return sqlSession.selectList("dailyBoard.selectDailyBoardInfo");
//	}
//
//	public List<DailyBoardVO> selectDailyBoardInfoList(Map paramMap) {
//		return sqlSession.selectList("dailyBoard.selectDailyBoardInfo", paramMap);
//	}
//
//	public DailyBoardVO selectDailyBoardInfoRow(DailyBoardVO dailyBoardVo) {
//		return sqlSession.selectOne("dailyBoard.selectDailyBoardInfo", dailyBoardVo);
//	}
//
//	public DailyBoardVO selectDailyBoardDetailInfoRow(DailyBoardVO dailyBoardVo) {
//		return sqlSession.selectOne("dailyBoard.selectDailyBoardDetailInfo", dailyBoardVo);
//	}
//
//	public int insertDailyBoardInfoRow(DailyBoardVO dailyBoardVo) {
//		return sqlSession.insert("dailyBoard.insertDailyBoardInfo", dailyBoardVo);
//	}
//
//	public int updateDailyBoardInfoRow(DailyBoardVO dailyBoardVo) {
//		return sqlSession.update("dailyBoard.updateDailyBoardInfo", dailyBoardVo);
//	}
//
//	public int deleteDailyBoardInfoRow(DailyBoardVO dailyBoardVo) {
//		return sqlSession.delete("dailyBoard.deleteDailyBoardInfo", dailyBoardVo);
//	}
//
//	public int selectDailyBoardCount() {
//		return sqlSession.delete("dailyBoard.selectDailyBoardCountInfo");
//	}
//
//	public void insertFileInfo(Map<String, Object> fileMap) {
//		sqlSession.insert("dailyBoard.insertDailyBoardFile");
//	}
//}
