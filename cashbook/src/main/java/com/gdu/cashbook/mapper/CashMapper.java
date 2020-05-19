package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;

@Mapper
public interface CashMapper {
	
	//가계부 내역 삭제
	public int deleteCashOne(Cash cash);
	
	//일자별 금액 합계 구하기
	public int selectCashKindSum(Cash cash);
	
	//로그인 사용자의 특정날짜 cash 목록
	public List<Cash> selectCashListByDate(Cash cash);
}
