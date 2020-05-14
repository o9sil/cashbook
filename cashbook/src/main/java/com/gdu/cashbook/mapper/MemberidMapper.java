package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberidMapper {
	public int insertMemberidOne(String memberid);
}
