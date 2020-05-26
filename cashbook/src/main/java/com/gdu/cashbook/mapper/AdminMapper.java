package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface AdminMapper {
	
	public List<Member> selectMemberList();
	
	public String selectLoginAdmin(Admin admin);
}
