package com.gdu.cashbook.common;

import java.util.ArrayList;

public class Pagination {

	//하단 페이징
	public ArrayList<Paging> groupPaging(int currentPage, int pagePerGroup, int lastPage) {		
		int groupStartPage = 0;
		
		ArrayList<Paging> list = new ArrayList<Paging>();		
		
		//10의 배수일 경우
		if(currentPage % pagePerGroup == 0) {
			groupStartPage = currentPage / pagePerGroup - 1;
			//System.out.println(groupStartPage+"<--- 10의 배수일경우값");
			for(int i=(groupStartPage*pagePerGroup)+1; i<=(groupStartPage*pagePerGroup)+pagePerGroup; i=i+1) {
				//현재 페이지 색 다름
				if(i==currentPage) {
					Paging p = new Paging();
					p.setPageNum(i);
					p.setPageSelect(true);
					list.add(p);
				}else {
					Paging p = new Paging();
					p.setPageNum(i);
					p.setPageSelect(false);
					list.add(p);
				}
			}
		}else {
			groupStartPage = currentPage / pagePerGroup;
			//System.out.println(groupStartPage+"<--- 10의 배수가 아닐 경우");
			if(lastPage < (groupStartPage*pagePerGroup)+pagePerGroup) {
				for(int i=(groupStartPage*pagePerGroup)+1; i<=lastPage; i=i+1) {
					//현재 페이지 색 다르게
					if(i==currentPage) {
						Paging p = new Paging();
						p.setPageNum(i);
						p.setPageSelect(true);
						list.add(p);
					}else {
						Paging p = new Paging();
						p.setPageNum(i);
						p.setPageSelect(false);
						list.add(p);
					}
				}
			}else {
				for(int i=(groupStartPage*pagePerGroup)+1; i<=(groupStartPage*pagePerGroup)+pagePerGroup; i=i+1) {
					//현재 페이지 색 다르게
					if(i==currentPage) {
						Paging p = new Paging();
						p.setPageNum(i);
						p.setPageSelect(true);
						list.add(p);
					}else {
						Paging p = new Paging();
						p.setPageNum(i);
						p.setPageSelect(false);
						list.add(p);
					}
				}
			}
			
		}

		return list;
	}
	
	public class Paging {
		private int pageNum;
		private Boolean pageSelect;
		
		public int getPageNum() {
			return pageNum;
		}
		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}
		public Boolean getPageSelect() {
			return pageSelect;
		}
		public void setPageSelect(Boolean pageSelect) {
			this.pageSelect = pageSelect;
		}
		@Override
		public String toString() {
			return "Paging [pageNum=" + pageNum + ", pageSelect=" + pageSelect + "]";
		}
		
	}
}
