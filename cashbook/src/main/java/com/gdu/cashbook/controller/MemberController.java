package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.UpdateMemberPw;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	//아이디 찾기 폼
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}		
		return "findMemberId";
	}
	
	//아이디 찾기 액션
	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session, Model model, Member member) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		String[] memberIdPart = memberService.getMemberIdByMember(member);
		if(memberIdPart.length == 0) {
			model.addAttribute("memberIdPart", "해당하는 아이디가 없습니다.");
		}else {
			model.addAttribute("memberIdPart", memberIdPart);
		}
		return "memberIdView";
	}
	
	
	//비밀번호 찾기 폼으로 이동
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {	
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		return "findMemberPw";
	}
	
	//비밀번호 찾기 post(메일 전송)
	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session, Member member, RedirectAttributes redirect) {			
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String msg = "";
		if(memberService.modifyMemberRandPw(member) == 0) {
			msg = "아이디와 메일을 확인하세요.";
		}else {
			msg = "입력한 메일로 임시 비밀번호를 전송하였습니다.";
		}
	
		redirect.addAttribute("msg", msg);				
		
		return "redirect:/";
	}
	
	//비밀번호 수정 액션
	@PostMapping("/modifyPassword")
	public String modifyPassword(HttpSession session, UpdateMemberPw updateMemberPw) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		
		//비밀번호 수정 실패시 다시 수정폼으로
		if(memberService.modifyMemberPwOne(updateMemberPw) != 1) {
			return "redirect:/modifyPassword?pw=false";
		}
		
		
		
		
		
		return "redirect:/memberInfo";
	}
	
	//비밀번호 수정 폼
	@GetMapping("/modifyPassword")
	public String modifyPassword(HttpSession session, @RequestParam(value = "pw", required = false) String pw) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}		
		
		return "modifyPassword";
	}
	
	//회원 탈퇴 액션
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, LoginMember loginMember) {		
		//성공
		if(memberService.removeInsertMemberOne(loginMember) == 1) {
			//로그인 되어있는 세션 삭제
			session.removeAttribute("loginMember");
			return "redirect:/";
		}
		
		return "redirect:/removeMember";
	}
	
	//회원 탈퇴 폼
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session, Model model) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			Member member = memberService.getMemberOne((LoginMember)session.getAttribute("loginMember"));
			model.addAttribute("member", member);
		}
		
		return "removeMember";
	}
	
	//회원정보 수정 액션
	@PostMapping("/modifyMember")
	public String modifyMember(HttpSession session, MemberForm memberForm) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		memberService.modifyMemberOne(memberForm);
				
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		loginMember.setMemberPic(memberService.getMemberPic(loginMember.getMemberId()));
		
		session.setAttribute("loginMember", loginMember);		
		
				
		return "redirect:/memberInfo";
	}
	
	//회원정보 수정 폼
	@GetMapping("/modifyMember")
	public String modifyMember(HttpSession session, Model model) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}		

		//session 내부의 loginMember type 확인절차 필요
		if (session.getAttribute("loginMember") instanceof LoginMember) {
			Member member = memberService.getMemberOne((LoginMember) session.getAttribute("loginMember"));
			model.addAttribute("member", member);
		}
		
		return "modifyMember";
	}
	
	//로그인 후 회원정보 폼
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		//로그인 중일때만 접근가능
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}		

		//session 내부의 loginMember type 확인절차 필요
		if (session.getAttribute("loginMember") instanceof LoginMember) {
			Member member = memberService.getMemberOne((LoginMember) session.getAttribute("loginMember"));
			model.addAttribute("member", member);
		}
		//Member member = memberService.getMemberOne((LoginMember)session.getAttribute("loginMember"));
		//model.addAttribute("member", member);
		
		return "memberInfo";
	}
	
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
			return "redirect:/home";
		}		
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		//로그인 한 상황이 아닐때		
		session.invalidate();
//		if(session.getAttribute("loginMember") == null) {
//			return "redirect:/";
//		}
//		session.removeAttribute("loginMember");
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
	public String addMember(MemberForm memberForm, HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}		
				
		memberService.addMember(memberForm);
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
