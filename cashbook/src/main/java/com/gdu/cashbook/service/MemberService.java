package com.gdu.cashbook.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.UpdateMemberPw;

@Service
public class MemberService {

	@Autowired private MemberMapper memberMapper;	
	@Autowired private MemberidMapper memberidMapper;	
	//@Autowired private MailService mailService;	
	@Autowired private JavaMailSender javaMailSender;
	
	//ID 찾기
	public String[] getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	
	//회원정보(비밀번호) 수정하기
	public int modifyMemberPwOne(UpdateMemberPw updateMemberPw) {
		return memberMapper.updateMemberPwOne(updateMemberPw);
	}
	
	@Transactional
	//회원탈퇴(트랜잭션 처리필요) - member 테이블에서 삭제, memberid 테이블에 추가
	public int removeInsertMemberOne(LoginMember loginMember) {
		//트랜잭션 처리를 위한 제어문
		if(memberMapper.deleteMemberOne(loginMember) == 1) {
			return memberidMapper.insertMemberidOne(loginMember.getMemberId());			
		}		
		return 0;
	}
	
	
	//회원정보 수정하기(패스워드 제외)
	public int modifyMemberOne(Member member) {		
		return memberMapper.updateMemberOne(member);
	}
	
	//로그인 된 멤버의 정보 가져오기
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	
	//비밀번호 찾기(랜덤으로 비밀번호 수정)
	public int modifyMemberRandPw(Member member) {
		//랜덤으로 생성된 비밀번호 10자리
		String updatePw = RandomStringUtils.randomAlphabetic(10);
		member.setMemberPw(updatePw);
		int row = memberMapper.updateMemberRandPw(member);
		if(row == 1) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail());
			simpleMailMessage.setFrom("JSsoft");
			simpleMailMessage.setSubject("CashBook 비밀번호 찾기");	
			simpleMailMessage.setText("비밀번호는 " + updatePw + " 입니다.");
			
			javaMailSender.send(simpleMailMessage);
		}
		
		return row;
	}
	
	//로그인
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	//회원가입
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
	
	//회원가입 전 ID 존재 여부 확인
	public int getMemberIdCnt(String memberId) {
		return memberMapper.selectMemberIdCnt(memberId);
	}
}
