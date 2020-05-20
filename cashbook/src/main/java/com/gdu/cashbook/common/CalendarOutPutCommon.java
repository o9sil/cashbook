package com.gdu.cashbook.common;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import com.gdu.cashbook.vo.CalendarOutput;

public class CalendarOutPutCommon {

	//달력 생성(매달 1일 ~ 마지막일을 ArrayList 형식으로 생성)
	public CalendarOutput CalendarList(LocalDate day) {
		
		LocalDate firstDayOfMonth =	day.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate lastDayOfMonth = day.with(TemporalAdjusters.lastDayOfMonth());
		
		List<List<LocalDate>> calendar = new ArrayList<List<LocalDate>>();
		List<LocalDate> weekList = null;		
		
		for(int i = 0; i < 100; i++) {
			LocalDate d = firstDayOfMonth.plusDays(i);
			if(d.isAfter(lastDayOfMonth)) {
				break;
			}
			
			if(weekList == null) {
				weekList = new ArrayList<LocalDate>();				
				calendar.add(weekList);
			}
			
			if(d.isBefore(firstDayOfMonth) || d.isAfter(lastDayOfMonth)) {
				weekList.add(firstDayOfMonth.minusDays(1));
			}else {
				weekList.add(d);
			}
						
			int week = d.getDayOfWeek().getValue();
			if(week == 6) {
				weekList = null;
			}
			
		}
		
		LocalDate nextMonth = firstDayOfMonth.plusMonths(1);
		LocalDate prevMonth = firstDayOfMonth.minusMonths(1);
		
		CalendarOutput output = new CalendarOutput();
		output.setCalendar(calendar);
		output.setFirstDayOfMonth(firstDayOfMonth);
		output.setYearOfNextMonth(nextMonth.getYear());
		output.setMonthOfNextMonth(nextMonth.getMonthValue());
	    output.setYearOfPrevMonth(prevMonth.getYear());
	    output.setMonthOfPrevMonth(prevMonth.getMonthValue());
	    
	    return output;
	}
}
