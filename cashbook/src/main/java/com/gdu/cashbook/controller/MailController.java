package com.gdu.cashbook.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MailService;
import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MemberService memberService;
	
	//비밀번호 찾기 폼으로 이동
	@GetMapping("/searchPassword")
	public String searchPassword() {				
		return "searchPassword";
	}
	
	//비밀번호 찾기 post(메일 전송)
	@PostMapping("/searchPassword")
	public String searchPassword(Member member) {		
		//System.out.println(member.getMemberId());
		//System.out.println(member.getMemberEmail());
		
		//랜덤으로 생성된 비밀번호 10자리
		String updatePw = RandomStringUtils.randomAlphanumeric(10);
		
		member.setMemberPw(updatePw);
		
		//String memberPw = memberService.getMemberPw(member);
		if(memberService.modifyMemberRandPw(member) == 0) {
			return "redirect:/";
		}else {
			mailService.send(member.getMemberEmail(), "비밀번호 찾기", "비밀번호는 " + updatePw + " 입니다.");
		}
				
		return "redirect:/";
	}
}
