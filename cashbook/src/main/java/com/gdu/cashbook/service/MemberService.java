package com.gdu.cashbook.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.UpdateMemberPw;

@Service
@Transactional
public class MemberService {

	@Autowired private MemberMapper memberMapper;	
	@Autowired private MemberidMapper memberidMapper;
	@Autowired private JavaMailSender javaMailSender;
	
	@Value("D:/git-cashbook/cashbook/src/main/resources/static/styles/upload/")
	private String path;
	
	//사진 가져오기
	public String getMemberPic(String memberId) {
		return memberMapper.selectMemberPic(memberId);
	}
	
	//ID 찾기
	public String[] getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	
	//회원정보(비밀번호) 수정하기
	public int modifyMemberPwOne(UpdateMemberPw updateMemberPw) {
		return memberMapper.updateMemberPwOne(updateMemberPw);
	}
		
	//회원탈퇴(트랜잭션 처리필요) - member 테이블에서 삭제, memberid 테이블에 추가
	public int removeInsertMemberOne(LoginMember loginMember) {
		// 회원탈퇴시 이미지 파일 삭제
		// 파일이름 select member_pic from member
		String memberPic = memberMapper.selectMemberPic(loginMember.getMemberId());
		// 파일 삭제
		File file = new File(path + memberPic);
//		if(file.exists()) {
//			file.delete();
//		}
		
		//트랜잭션 처리를 위한 제어문
		if(memberMapper.deleteMemberOne(loginMember) == 1) {
			if(memberidMapper.insertMemberidOne(loginMember.getMemberId()) == 1) {
				if(!memberPic.equals("default.jpg")) {
					if(file.exists()) {
						file.delete();
					}
				}				
				return 1;
			}						
		}		
		return 0;
	}
	
	
	//회원정보 수정하기(패스워드 제외)
	public int modifyMemberOne(MemberForm memberForm) {		
		MultipartFile mf = memberForm.getMemberPic();
		String memberPic = null;
		
		// 확장자 필요
		String originName = mf.getOriginalFilename();		
		
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		
		
		if(originName.length() == 0) {
			//System.out.println("파일없음");
			//기존 사진파일 그대로 사용
			//member.setMemberPic(memberPic);		// null 사용
		}else {
			//기존 사진 삭제 후 재업로드
			String memPic = memberMapper.selectMemberPic(memberForm.getMemberId());
			// 파일 삭제
						
			if(!memPic.equals("default.jpg")) {
				//기본이미지가 아니면 삭제
				File file = new File(path + memPic);
				if(file.exists()) {
					file.delete();
				}
			}
				
						
			
			//사진 수정
			int lastDot = originName.lastIndexOf(".");
			String extension = originName.substring(lastDot);
			memberPic = memberForm.getMemberId() + extension;
			
			// 2. 파일 저장			
			File file = new File(path + memberPic);
			try {
				mf.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				throw new RuntimeException();	//예외를 강제로 발생시켜주어야 함(catch 절에서 종료가 되면 트랜잭션 처리가 안됨)
				// Exception
				// 1. 예외처리를 해야만 문법적으로 이상없는 예외
				// 2. 예외처리를 코드에서 구현하지 않아도 아무문제 없는 예외 RuntimeException
			}
			
			member.setMemberPic(memberPic);			
		}
		
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
	public int addMember(MemberForm memberForm) {
		
		//System.out.println(memberForm.getMemberPic() + "PIC");
		
		//사진 업로드 했을때만		
		MultipartFile mf = memberForm.getMemberPic();
		String memberPic = null;
		
		// 확장자 필요
		String originName = mf.getOriginalFilename();
		//System.out.println("originName = " + originName);
		
		if(originName.length() == 0) {
			//System.out.println("파일없음");
			memberPic = "default.jpg";			
		}else {
			//System.out.println("사진 업로드");
			int lastDot = originName.lastIndexOf(".");
			String extension = originName.substring(lastDot);
			memberPic = memberForm.getMemberId() + extension;
			
			// 2. 파일 저장			
			File file = new File(path + memberPic);
			try {
				mf.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				throw new RuntimeException();	//예외를 강제로 발생시켜주어야 함(catch 절에서 종료가 되면 트랜잭션 처리가 안됨)
				// Exception
				// 1. 예외처리를 해야만 문법적으로 이상없는 예외
				// 2. 예외처리를 코드에서 구현하지 않아도 아무문제 없는 예외 RuntimeException
			}
		}
		
		
		// 1. DB 저장
		Member member = new Member();		
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPic(memberPic);
				
		int row = memberMapper.insertMember(member);	
		
		return row;
	}
	
	//회원가입 전 ID 존재 여부 확인
	public int getMemberIdCnt(String memberId) {
		return memberMapper.selectMemberIdCnt(memberId);
	}
}
