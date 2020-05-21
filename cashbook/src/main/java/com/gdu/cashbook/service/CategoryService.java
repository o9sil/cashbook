package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CategoryMapper;

@Service
public class CategoryService {
	
	@Autowired private CategoryMapper categoryMapper;
	
	public List<String> getCategoryList(int categoryDesc) {
		//1 = 수입, 0 = 지출
		String cat = "";
		if(categoryDesc == 1) {
			cat = "수입";
		}else if(categoryDesc == 0){ 
			cat = "지출";
		}
		return categoryMapper.selectCategoryList(cat);
	}
}
