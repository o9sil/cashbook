package com.gdu.cashbook.vo;

public class Board {
	private int boardNo;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private int boardLevel;
	private int boardParent;
	private int boardReNo;
	private int boardReLevel;
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
	public int getBoardParent() {
		return boardParent;
	}
	public void setBoardParent(int boardParent) {
		this.boardParent = boardParent;
	}
	public int getBoardReNo() {
		return boardReNo;
	}
	public void setBoardReNo(int boardReNo) {
		this.boardReNo = boardReNo;
	}
	public int getBoardReLevel() {
		return boardReLevel;
	}
	public void setBoardReLevel(int boardReLevel) {
		this.boardReLevel = boardReLevel;
	}
	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", memberId=" + memberId + ", boardTitle=" + boardTitle + ", boardContent="
				+ boardContent + ", boardDate=" + boardDate + ", boardLevel=" + boardLevel + ", boardParent="
				+ boardParent + ", boardReNo=" + boardReNo + ", boardReLevel=" + boardReLevel + "]";
	}
	
	
}
