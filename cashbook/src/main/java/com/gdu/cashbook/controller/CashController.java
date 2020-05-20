package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.common.CalendarOutPutCommon;
import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.CalendarOutput;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	
	@Autowired private CashService cashService;
		
	@GetMapping("/addCashOne")
	public String addCashOne(HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		return "addCashOne";
	}
	
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, @RequestParam(value = "day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {		
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		if(day == null) {
			day = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
		}
		LoginMember loginMember = null;
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			loginMember = (LoginMember)session.getAttribute("loginMember");			
		}
		
		model.addAttribute("day", day);		
		
		//회원별 월별 각각의 일자 수입,지출 합계금액 가져오기
		List<DayAndPrice> dayAndPrice = cashService.getDayAndPriceList(day, loginMember.getMemberId());
		
		model.addAttribute("dayAndPrice", dayAndPrice);		
		
		//매달 1~마지막일을 List 형식으로 생성
		CalendarOutPutCommon cal = new CalendarOutPutCommon();		
		model.addAttribute("output", cal.CalendarList(day));
		
		CalendarOutput rr = cal.CalendarList(day);
		System.out.println(rr.getCalendar().get(1).get(2).getDayOfMonth());
		
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
		
		LocalDate localDate = cashService.getDateOne(cashNo);		
		
		Cash cash = new Cash();
		cash.setCashNo(cashNo);
		cash.setMemberId(loginMember.getMemberId());
		
		cashService.removeCashOne(cash);
		
		return "redirect:/getCashListByDate?day=" + localDate;
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
