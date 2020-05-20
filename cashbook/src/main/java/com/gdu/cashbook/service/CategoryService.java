package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CategoryMapper;

@Service
public class CategoryService {
	
	@Autowired private CategoryMapper categoryMapper;
	
	public List<String> getCategoryList() {
		return categoryMapper.selectCategoryList();
	}
}
