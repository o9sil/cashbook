package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.UpdateMemberPw;

@Mapper
public interface MemberMapper {
	
	//ID 찾기
	public String[] selectMemberIdByMember(Member member);
	
	//비밀번호 변경
	public int updateMemberPwOne(UpdateMemberPw updateMemberPw);
	
	//회원탈퇴시 멤버테이블에서 삭제후 멤버아이디테이블에 추가
	public int deleteMemberOne(LoginMember loginMember);
	
	public int updateMemberOne(Member member);
	public Member selectMemberOne(LoginMember loginMember);	
	public int updateMemberRandPw(Member member);
	public LoginMember selectLoginMember(LoginMember loginMember);
	public int insertMember(Member member);
	public int selectMemberIdCnt(String memberId);
}
