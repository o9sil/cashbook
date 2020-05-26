package com.gdu.cashbook.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Board;

@RestController
public class BoardRestController {

	@Autowired BoardService boardService;
	
	@GetMapping("/removeBoard")
	public int removeBoard(@RequestParam(value = "boardNo") int boardNo, @RequestParam(value = "memberId") String memberId) {
		
		//System.out.println(boardNo + "boardNo");
		//System.out.println(memberId + "memberId");
		
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setMemberId(memberId);
		
		int row = boardService.removeBoardOne(board);
		
		return row;
	}
}
