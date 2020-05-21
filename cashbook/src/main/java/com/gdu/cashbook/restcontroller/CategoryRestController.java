package com.gdu.cashbook.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.cashbook.service.CategoryService;


@RestController
public class CategoryRestController {
	@Autowired private CategoryService categoryService;
	
	@PostMapping("/getCategory")
	public List<String> getCategory(@RequestParam(value = "categoryDesc") int categoryDesc) { // List<String> ck
//		System.out.println("/getCategory 요청");
//		System.out.println(categoryDesc);
		
		//1 = 수입, 0 = 지출
		
		List<String> categoryList = new ArrayList<String>();
		
		categoryList = categoryService.getCategoryList(categoryDesc);
		
		return categoryList;
	}
}
