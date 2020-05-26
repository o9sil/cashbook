package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.AdminMapper;
import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Member;

@Service
public class AdminService {
	
	@Autowired AdminMapper adminMapper;
	@Autowired BoardMapper boardMapper;
	
	public List<Member> getMemberList(){
		return adminMapper.selectMemberList();
	}
	
	public Board getAdminBoardOne(int boardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardNo", boardNo);
		
		return boardMapper.selectBoardOne(map);
	}
	
	public String adminLogin(Admin admin) {
		return adminMapper.selectLoginAdmin(admin);
	}
}
