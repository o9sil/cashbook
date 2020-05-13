package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	//로그인 폼
	@GetMapping("/login")
	public String login(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		//로그인 되어있지 않을때만
		return "login";		
	}
	
	//로그인 액션
	@PostMapping("/login")
	public String login(Model model, LoginMember loginMember, HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		LoginMember returnLoginMember = memberService.login(loginMember);
		//로그인 실패시 id는 사용자가 입력값 출력, 하단에 메세지 출력
		if(returnLoginMember == null) {
			model.addAttribute("id", loginMember.getMemberId());
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요.");
			return "login";
		}else {	//로그인 성공시
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/";
		}		
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		//로그인 한 상황이 아닐때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		session.removeAttribute("loginMember");
		return "redirect:/";
	}
	
	//회원가입 폼
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {		
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "addMember";
	}
	
	//회원가입 액션
	@PostMapping("/addMember")
	public String addMember(Member member, HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		memberService.addMember(member);
		return "redirect:/index";
	}
	
	//ID 중복 체크
	@ResponseBody
	@PostMapping("/memberIdCheck")
	public int idCheck(@RequestParam("memberId") String memberId, HttpSession session) {
		//System.out.println("IDCHECK");
		//System.out.println(memberService.checkMemberId(memberId));
		return memberService.getMemberIdCnt(memberId);
	}
}
