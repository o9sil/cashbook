package com.gdu.cashbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Member;

@Controller
public class AdminController {
	
	@Autowired AdminService adminService;
	@Autowired BoardService boardService;
	
	@GetMapping("/adminGetMemberList")
	public String adminGetMemberList(HttpSession session, Model model) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("adminLogin") == null) {
			return "redirect:/";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
				
		map = adminService.getMemberList(1);
		
		model.addAttribute("memberList", map.get("memberList"));
		model.addAttribute("pagingList", map.get("pagingList"));
		
		
		return "adminGetMemberList";
	}
	
	
	@PostMapping("/adminAddBoardReply")
	public String adminAddBoardReply(HttpSession session, Board board) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("adminLogin") == null) {
			return "redirect:/";
		}
		
		board.setMemberId((String) session.getAttribute("adminLogin"));
		
		//System.out.println("addBoardReply Post = " + board);
		
		boardService.addBoardReply(board);
				
		return "redirect:/adminBoardList";
	}
	
	
	@GetMapping("/adminAddBoardReply")
	public String adminAddBoardReply(HttpSession session, @RequestParam(value = "boardNo") int boardNo) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("adminLogin") == null) {
			return "redirect:/";
		}
		
		return "adminAddBoardReply";
	}
	
	@GetMapping("/adminBoardInfo")
	public String adminBoardInfo(HttpSession session, Model model, @RequestParam(value = "boardNo") int boardNo) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("adminLogin") == null) {
			return "redirect:/";
		}
		
		Board board = adminService.getAdminBoardOne(boardNo);		
		model.addAttribute("board", adminService.getAdminBoardOne(boardNo));
		
		return "adminBoardInfo";
	}
	
	@GetMapping("/adminBoardList")
	public String adminBoardList(HttpSession session, Model model, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage, 
			@RequestParam(value = "search", defaultValue = "") String search) {
		//관리자만 접근 가능
		if(session.getAttribute("adminLogin") == null) {
			return "redirect:/";
		}		
		
		int cPage = 1;
		if(currentPage != 0) {
			cPage = currentPage;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map = boardService.getBoardList(cPage, search);	//currentPage 넣기
				
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pagingList", map.get("pagingList"));
		
		//System.out.println("getBoardList = " + boardList);
		//System.out.println("getPagingList = " + pagingList);		
		
		return "adminBoardList";
	}
	
	@GetMapping("/adminHome")
	public String adminHome(HttpSession session) {
		if(session.getAttribute("adminLogin") == null) {
			return "redirect:/";
		}
		
		return "adminHome";
	}
	
	@GetMapping("/adminLogin")
	public String adminLogin() {
		
		return "adminLogin";
	}
	
	@PostMapping("/adminLogin")
	public String adminLogin(HttpSession session, Admin admin) {
		
		String adminId = adminService.adminLogin(admin);
		
		session.setAttribute("adminLogin", adminId);
		
		
		return "redirect:/adminHome";
	}
}
