package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
	
	//카테고리 리스트 가져오기
	public List<String> selectCategoryList();
}
