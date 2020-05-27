package com.gdu.cashbook.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.common.Pagination;
import com.gdu.cashbook.common.Pagination.Paging;
import com.gdu.cashbook.mapper.AdminMapper;
import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Member;

@Service
public class AdminService {
	
	@Autowired AdminMapper adminMapper;
	@Autowired BoardMapper boardMapper;
		
	
	public Map<String, Object> getMemberList(int currentPage){
		
		Map<String, Object> mapReturn = new HashMap<String ,Object>();
		
		List<Member> memberList = new ArrayList<Member>();
		
		int rowPerPage = 20;
		int beginRow = (currentPage - 1) * rowPerPage;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		
		memberList = adminMapper.selectMemberList(map);
		
		mapReturn.put("memberList", memberList);
		
		//하단 페이지 10 고정
		int pagePerGroup = 10;
		int lastPage = 0;	//마지막 페이지
		int memberCnt = adminMapper.selectMemberCnt();
		
		if(memberCnt % pagePerGroup == 0) {
			lastPage = memberCnt / pagePerGroup;
		}else {
			lastPage = (memberCnt / pagePerGroup) + 1;
		}
		
		Pagination pagination = new Pagination();
		
		List<Paging> pagingList = pagination.groupPaging(currentPage, pagePerGroup, lastPage);
		mapReturn.put("pagingList", pagingList);
		
		//return adminMapper.selectMemberList();
		return mapReturn;
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
