package com.gdu.cashbook.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.common.Pagination;
import com.gdu.cashbook.common.Pagination.Paging;
import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Board;

@Service
public class BoardService {
	
	@Autowired private BoardMapper boardMapper;
	
	//게시글 답글달기
	public int addBoardReply(Board board) {
		return boardMapper.insertBoardReply(board);
	}
	
	
	//게시글 삭제
	public int removeBoardOne(Board board) {
		return boardMapper.deleteBoardOne(board);
	}
	
	
	//게시글 작성자 가져오기
	public String getBoardMemberId(int boardNo) {
		return boardMapper.selectBoardMemberId(boardNo);
	}
	
	//게시글 수정
	public int modifyBoardOne(Board board) {
		return boardMapper.updateBoardOne(board);
	}
	
	//게시글 등록
	public int addBoardOne(Board board) {		
		boardMapper.insertBoardOne(board);
		//System.out.println("addBoardOne = " + board);
		
		return boardMapper.updateBoardParent(board);
	}
	
	//게시글 상세보기
	public Board getBoardOne(Board board) {
		Board resultBoard = new Board();
		
		Map<String, Object> map = new HashMap<String, Object>();
		//System.out.println("board = " + board);
		//레벨 0일시 모두가 조회 가능 1일시 본인만 조회 가능
		if(boardMapper.selectBoardLevel(board.getBoardNo()) == 0) {
			//데이터 가져오기			
			//System.out.println("보드 레벨 0");
			map.put("boardNo", board.getBoardNo());
			
			resultBoard = boardMapper.selectBoardOne(map);
		}else {
			//System.out.println("보드 레벨 1");			
			String memberId = boardMapper.selectMemberIdBoard(board);
			//System.out.println(memberId + " memberId");
			//System.out.println(board.getMemberId() + " board.getMemberId()");
			
			//관리자 글인지 확인후 관리자 글이면 원글 작성자, 관리자만 열람 가능			
			if(memberId == null) {
				map.put("memberId", board.getMemberId());
			}else if(memberId.equals(board.getMemberId())){				
				map.put("memberId", boardMapper.selectBoardMemberId(board.getBoardNo()));
			}else {
				map.put("memberId", board.getMemberId());
			}
			
			map.put("boardNo", board.getBoardNo());
			
			resultBoard = boardMapper.selectBoardOne(map);
		}
		
		return resultBoard;	
	}
	
	//게시글 가져오기(페이징)
	public Map<String, Object> getBoardList(int currentPage, String search){
		
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		
		List<Board> boardList = new ArrayList<Board>();
		
		int rowPerPage = 10;
		int beginRow = (currentPage - 1) * rowPerPage;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		map.put("search", search);
		
		//System.out.println("beginRow = " + beginRow);
		//System.out.println("rowPerPage = " + rowPerPage);
		boardList = boardMapper.selectBoardList(map);	//beginRow, rowPerPage
		
		//리스트 추가
		mapReturn.put("boardList", boardList);
		
//		for(Board b : boardList) {
//			System.out.println(b.getBoardTitle());
//		}
		
		//10페이지씩 고정
		int pagePerGroup = 10;
		int lastPage = 0;	//마지막 페이지 가져오기
		int boardCnt = boardMapper.selectBoardCount(search);
		
		if(boardCnt % pagePerGroup == 0) {
			lastPage = boardCnt / pagePerGroup;
		}else {
			lastPage = (boardCnt / pagePerGroup) + 1;
		}
		
		Pagination pagination = new Pagination();
		
		List<Paging> pagingList = pagination.groupPaging(currentPage, pagePerGroup, lastPage);
		mapReturn.put("pagingList", pagingList);
		
//		System.out.println(pagingList + "페이징리스트");
//		System.out.println(lastPage + "라스트페이지");
		
		return mapReturn;
	}
}
