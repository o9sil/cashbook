package com.gdu.cashbook.vo;

public class UpdateMemberPw {
	private String memberId;
	private String memberPw;
	private String updateMemberPw;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getUpdateMemberPw() {
		return updateMemberPw;
	}
	public void setUpdateMemberPw(String updateMemberPw) {
		this.updateMemberPw = updateMemberPw;
	}
	@Override
	public String toString() {
		return "updateMemberPw [memberId=" + memberId + ", memberPw=" + memberPw + ", updateMemberPw=" + updateMemberPw
				+ "]";
	}
	
}
