package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Item;
import com.example.demo.service.ShowDetailService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author inagakisaia
 *
 */
@Controller
@RequestMapping("/show-detail")
public class ShowDetailController {

	@Autowired
	private ShowDetailService showDetailService;

	/**
	 * 商品詳細画面を表示します.
	 * 
	 * @param id ID
	 * @return 商品詳細画面
	 */
	@RequestMapping("/")
	public String showDetail(Integer itemId, Model model) {
		System.out.println(itemId);
		Item item = showDetailService.showDetail(itemId);
		model.addAttribute("item", item);
		return "item_detail";
	}

}
