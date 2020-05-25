package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {

	//게시글 작성자 가져오기
	public String selectBoardMemberId(int boardNo);
	
	//게시글 수정
	public int updateBoardOne(Board board);
	
	//게시글 등록
	public int insertBoardOne(Board board);
	
	//게시글 접근 레벨 확인하기
	public int selectBoardLevel(int boardNo);
	
	//게시글 상세보기
	public Board selectBoardOne(Map<String, Object> map);
	
	//게시글 리스트 가져오기(페이징 필요)
	public List<Board> selectBoardList(Map<String, Integer> map);
	
	//게시글 전체 개수 가져오기
	public int selectBoardCount();
}
