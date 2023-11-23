package com.spring.coffee.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
