package com.gdu.cashbook.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.LoginMember;


@Controller
public class BoardController {
	
	@Autowired BoardService boardService;	
	
	//게시글 수정 액션
	@PostMapping("/modifyBoardOne")
	public String modifyBoardOne(HttpSession session, Board board) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		LoginMember loginMember = null;
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			loginMember = (LoginMember)session.getAttribute("loginMember");			
		}
		
		
		
		
		
		board.setMemberId(loginMember.getMemberId());
		boardService.modifyBoardOne(board);
		
		return "redirect:/boardInfo?boardNo="+board.getBoardNo();
	}
	
	//게시글 수정
	@GetMapping("/modifyBoardOne")
	public String modifyBoardOne(HttpSession session, Model model, @RequestParam(value = "boardNo") int boardNo) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
			
		
		LoginMember loginMember = null;
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			loginMember = (LoginMember)session.getAttribute("loginMember");			
		}
		
		//작성자와 로그인한 사용자가 동일하지 않을경우
		if(!boardService.getBoardMemberId(boardNo).equals(loginMember.getMemberId())) {
			return "redirect:/boardList";
		}
				
		
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setMemberId(loginMember.getMemberId());
		
		model.addAttribute("board", boardService.getBoardOne(board));
		
		return "modifyBoardOne";
	}
	
	//게시글 등록 액션
	@PostMapping("/addBoardOne")
	public String addBoardOne(HttpSession session, Board board) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		LoginMember loginMember = null;
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			loginMember = (LoginMember)session.getAttribute("loginMember");			
		}
		board.setMemberId(loginMember.getMemberId());
		System.out.println(board);
		boardService.addBoardOne(board);
				
		return "redirect:/boardList";
	}
	
	
	//게시글 등록
	@GetMapping("/addBoardOne")
	public String addBoardOne(HttpSession session) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		return "addBoardOne";
	}
	
	//게시글 상세보기
	@GetMapping("/boardInfo")
	public String boardInfo(HttpSession session, Model model, @RequestParam(value = "boardNo") int boardNo, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		LoginMember loginMember = null;
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			loginMember = (LoginMember)session.getAttribute("loginMember");			
		}
		
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setMemberId(loginMember.getMemberId());
		
		//조회불가능
		Board resultBoard = boardService.getBoardOne(board);
		if(resultBoard != null) {
			model.addAttribute("board", boardService.getBoardOne(board));
		}else {
			//조회 불가능시
			return "redirect:/boardList?currentPage="+currentPage;
		}
		
		return "boardInfo";
	}
	
	//게시글 리스트
	@GetMapping("/boardList")
	public String boardList(HttpSession session, Model model, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}		
		
		System.out.println("currentPage = " + currentPage);
		
		int cPage = 1;
		if(currentPage != 0) {
			cPage = currentPage;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map = boardService.getBoardList(cPage);	//currentPage 넣기
				
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pagingList", map.get("pagingList"));
		
		//System.out.println("getBoardList = " + boardList);
		//System.out.println("getPagingList = " + pagingList);		
		
		return "boardList";
	}
}
