package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Item;
import com.example.demo.service.ShowItemListService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author yoshikikumazawa
 *
 */
@Controller
@RequestMapping("/")
public class ShowItemListController {
	@Autowired
	private ShowItemListService showItemListService;

	/**
	 * 商品情報一覧を表示します.
	 * 
	 * @param name 商品名
	 * @return 商品一覧画面
	 */
	@RequestMapping("")
	public String showItemList(String name, Model model) {
		List<Item> itemList = showItemListService.showItemList(name);
		model.addAttribute("itemList", itemList);
		return "item_list";
	}

}
