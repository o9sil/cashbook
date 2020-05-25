package com.gdu.cashbook.vo;

public class Board {
	private int boardNo;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private int boardLevel;
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}	
	public int getBoardLevel() {
		return boardLevel;
	}
	public void setBoardLevel(int boardLevel) {
		this.boardLevel = boardLevel;
	}
	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", memberId=" + memberId + ", boardTitle=" + boardTitle + ", boardContent="
				+ boardContent + ", boardDate=" + boardDate + ", boardLevel=" + boardLevel + "]";
	}
}
