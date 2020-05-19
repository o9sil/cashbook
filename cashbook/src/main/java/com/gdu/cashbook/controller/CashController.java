package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model, @RequestParam(value = "date", defaultValue = "today") String date) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		LoginMember loginMember = null;
		if(session.getAttribute("loginMember") instanceof LoginMember) {
			loginMember = (LoginMember)session.getAttribute("loginMember");			
		}
		
		LocalDate cashDate = LocalDate.now();
		if(!date.equals("today")) {
			cashDate = LocalDate.parse(date);			
		}
		
		
		
		model.addAttribute("cashDate", cashDate);
		
		Cash cash = new Cash();
		cash.setMemberId(loginMember.getMemberId());
		cash.setCashDate(cashDate);
		
		List<Cash> cashList = cashService.getCashListByDate(cash);
		model.addAttribute("cashList", cashList);

		//일자별 수입, 지출 합계
		int cashSum = 0;
		for(Cash c : cashList) {
			if(c.getCashKind().equals("수입")) {
				cashSum += c.getCashPrice();
			}else if(c.getCashKind().equals("지출")) {
				cashSum -= c.getCashPrice();
			}
		}
		
		model.addAttribute("cashSum", cashSum);
		
		return "getCashListByDate";
	}
}
