package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;

@Service
public class CashService {
	
	@Autowired private CashMapper cashMapper;
	
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
