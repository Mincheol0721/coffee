package com.spring.coffee.member.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.coffee.member.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberDaoImpl {

	@Autowired
	protected SqlSession sqlSession;

//	@Resource(name = "sqlSessionFactoryBean")
//	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
//		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
//	}

	public List<MemberVO> selectMemberInfoList(MemberVO memberVo) {
		return sqlSession.selectList("member.selectMemberInfo", memberVo);
	}

	public MemberVO selectMemberInfoRow(MemberVO memberVo) {
		return sqlSession.selectOne("member.selectMemberInfo", memberVo);
	}

	public int insertMemberInfoRow(MemberVO memberVo) {
		return sqlSession.insert("member.insertMemberInfo", memberVo);
	}

	public int updateMemberInfoRow(MemberVO memberVo) {
		return sqlSession.update("member.updateMemberInfo", memberVo);
	}

	public int deleteMemberInfoRow(MemberVO memberVo) {
		return sqlSession.delete("member.deleteMemberInfo", memberVo);
	}

	public int idValidate(String id) {
		return sqlSession.selectOne("member.idValidate", id);
	}

	public int nickValidate(String nickname) {
		return sqlSession.selectOne("member.nickValidate", nickname);
	}

	public int updatePasswordInfo(MemberVO memberVo) {
		return sqlSession.update("member.updatePasswordInfo", memberVo);
	}

}
