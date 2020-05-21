package com.gdu.cashbook.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;

@Service
public class CashService {
	
	@Autowired private CashMapper cashMapper;
	
	//특정 가계부 수정하기
	public int modifyCashOne(Cash cash) {
		if(cash.getCashKind().equals("1")) {
			cash.setCashKind("수입");
		}else {
			cash.setCashKind("지출");
		}
		return cashMapper.updateCashOne(cash);
	}
	
	//특정 가계부 가져오기
	public Cash getCashOne(int cashNo) {
		return cashMapper.selectCashOne(cashNo);
	}
	
	public int addCashOne(Cash cash) {
		if(cash.getCashKind().equals("1")) {
			cash.setCashKind("수입");
		}else {
			cash.setCashKind("지출");
		}
		return cashMapper.insertCashOne(cash);
	}
	
	public LocalDate getDateOne(int cashNo) {
		return cashMapper.selectDateOne(cashNo);
	}
	
	public List<DayAndPrice> getDayAndPriceList(LocalDate localDate, String memberId){
		HashMap<String, Object> map = new HashMap<String, Object>();
		//System.out.println("Service = " + localDate.getYear());
		//System.out.println("Service = " + localDate.getMonthValue());
		map.put("memberId", memberId);
		map.put("year", localDate.getYear());
		map.put("month", localDate.getMonthValue());
				
		return cashMapper.selectDayAndPriceList(map);
	}	
	
	public int removeCashOne(Cash cash) {
		return cashMapper.deleteCashOne(cash);
	}
	
	public Map<String, Object> getCashListByDate(Cash cash){
		List<Cash> cashList = cashMapper.selectCashListByDate(cash); 
		int cashKindSum = cashMapper.selectCashKindSum(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);
		
		return map;
	}
}
