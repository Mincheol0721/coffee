package com.spring.coffee.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.coffee.member.vo.MemberVO;

@Mapper
public interface MemberMapper {
	
	@Select("SELECT * FROM member WHERE id=#{id} and password=#{password, jdbcType=VARCHAR}")
	MemberVO login(MemberVO member); 
	
	@Insert("INSERT INTO member(id, password, name, ssn, nickname, email, mobile, zipcode, roadAddr, detailAddr, jibunAddr, fileName) "
			+ "    VALUES (#{id}, #{password, jdbcType=VARCHAR}, #{name}, #{ssn}, #{nickname}, #{email}, #{mobile}, #{zipcode}, #{roadAddr}, #{detailAddr}, #{jibunAddr}, #{fileName})")
	void addMember(Map map);
	
	@Select("SELECT * FROM member WHERE id=#{id, jdbcType=VARCHAR}")
	MemberVO getMemberById(String id);
	
	@Update("UPDATE member SET name=#{name}, nickname=#{nickname}, email=#{email}, mobile=#{mobile}, zipcode=#{zipcode}, roadAddr=#{roadAddr}, detailAddr=#{detailAddr}, jibunAddr=#{jibunAddr}, fileName=#{fileName} WHERE id=#{id}")
	void modMember(Map map);
	
	@Update("UPDATE member SET password=#{password} WHERE id=#{id}")
	void modPassword(String id, String password);
	
	@Insert("INSERT INTO member(id, socialId, password, name, ssn, nickname, email, mobile, zipcode, roadAddr, detailAddr, jibunAddr, fileName) "
			+ "    VALUES (#{id}, #{socialId}, #{password, jdbcType=VARCHAR}, #{name}, #{ssn}, #{nickname}, #{email}, #{mobile}, #{zipcode}, #{roadAddr}, #{detailAddr}, #{jibunAddr}, #{fileName})")
	void addSocialMember(Map map);

	@Select("SELECT * FROM member WHERE socialId=#{id}")
	MemberVO getMemberBySocialId(String id);
	
	@Select("SELECT nickname FROM member WHERE nickname=#{nickname}")
	String nickValidate(String nickname);
	
	@Select("SELECT id FROM member WHERE id=#{id}")
	String idValidate(String id);

	@Delete("DELETE FROM member WHERE id=#{id}")
	void delMember(String id);
}
