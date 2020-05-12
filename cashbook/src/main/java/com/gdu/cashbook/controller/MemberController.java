package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	//회원가입 폼
	@GetMapping("/addMember")
	public String addMember() {		
		return "addMember";
	}
	
	//회원가입 액션
	@PostMapping("/addMember")
	public String addMember(Member member) {
		memberService.addMember(member);
		return "redirect:/index";
	}
	
	//ID 중복 체크
	@ResponseBody
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.GET)	
	public int idCheck(@RequestParam("memberId") String memberId) {
		//System.out.println("IDCHECK");
		//System.out.println(memberService.checkMemberId(memberId));
		return memberService.checkMemberId(memberId);
	}
}
