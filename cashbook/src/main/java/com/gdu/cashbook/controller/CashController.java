package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	
	@Autowired private CashService cashService;
	
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, @RequestParam(value = "yearMonth", required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {		
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		//이전, 다음달 넘길수있도록
		model.addAttribute("yearMonthBefore", yearMonth.minus(Period.ofMonths(1)));
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("yearMonthAfter", yearMonth.plus(Period.ofMonths(1)));
		
//		System.out.println(yearMonth.lengthOfMonth());
//		
//		//매달 1일, 마지막일
//		String firstDate = yearMonth + "-01";
//		String lastDate = yearMonth + "-" + yearMonth.lengthOfMonth();
//		
//		LocalDate firstLocalDate = LocalDate.parse(firstDate, DateTimeFormatter.ISO_DATE);
//		LocalDate lastLocalDate = LocalDate.parse(lastDate, DateTimeFormatter.ISO_DATE);
//		System.out.println(firstLocalDate.getDayOfWeek());	//MONDAY
//		System.out.println(lastLocalDate.getDayOfWeek());	//TUESDAY
		
		
		return "getCashListByMonth";
	}
	
	//회원별 입출금 내역 리스트 삭제
	@GetMapping("/removeCashOne")
	public String removeCashOne(HttpSession session, @RequestParam(value = "cashNo") int cashNo) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		LoginMember loginMember = null;
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			loginMember = (LoginMember)session.getAttribute("loginMember");			
		}
		
		Cash cash = new Cash();
		cash.setCashNo(cashNo);
		cash.setMemberId(loginMember.getMemberId());
		
		cashService.removeCashOne(cash);
		
		return "redirect:/getCashListByDate";
	}
	
	
	//회원별 일자별 입출금 내역 리스트
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model, @RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		LoginMember loginMember = null;
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			loginMember = (LoginMember)session.getAttribute("loginMember");			
		}		
	
		//값 안들어오면 오늘날짜
		if(day == null) {
			day = LocalDate.now();			
		}
		
		model.addAttribute("day", day);
		
		// 년,월만 추출
		YearMonth yearMonth = YearMonth.from(day);
		model.addAttribute("yearMonth", yearMonth);
		
		
		Cash cash = new Cash();
		cash.setMemberId(loginMember.getMemberId());
		cash.setCashDate(day);
		
		Map<String, Object> map = cashService.getCashListByDate(cash);
		
		model.addAttribute("cashList", map.get("cashList"));
		
		//일자별 수입, 지출 합계	
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		
		return "getCashListByDate";
	}
}
