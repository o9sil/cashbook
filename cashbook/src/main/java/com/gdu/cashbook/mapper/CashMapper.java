package com.gdu.cashbook.mapper;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.MonthPrice;

@Mapper
public interface CashMapper {
	
	//한 해의 전체 합계 가져오기
	public int selectCashSumYear(Map<String, Object> map);
	
	//한 해의 월별 가계부 합산금액 가져오기
	public List<MonthPrice> selectCashMonthPrice(Map<String, Object> map);
	
	//가계부 관리 페이지 연도별 데이터 가져오기
	public List<Year> selectCashYearList(String memberId);	
	
	//가계부 내역 수정
	public int updateCashOne(Cash cash);
	
	//특정 가계부 내역 가져오기
	public Cash selectCashOne(int cashNo);
	
	//가계부 내역 추가
	public int insertCashOne(Cash cash);
	
	//현재 날짜 가져오기
	public LocalDate selectDateOne(int cashNo);
	
	//월별 달력 수입,지출 금액 리스트
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	
	//가계부 내역 삭제
	public int deleteCashOne(Cash cash);
	
	//일자별 금액 합계 구하기
	public int selectCashKindSum(Cash cash);
	
	//로그인 사용자의 특정날짜 cash 목록
	public List<Cash> selectCashListByDate(Cash cash);
}
