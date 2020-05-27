package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface AdminMapper {
	
	public int selectMemberCnt();
	
	public List<Member> selectMemberList(Map<String, Object> map);
	
	public String selectLoginAdmin(Admin admin);
}
