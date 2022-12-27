package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author yoshikikumazawa
 *
 */
@Service
@Transactional
public class ShowItemListService {
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 名前から商品情報一覧を検索します.
	 * 
	 * @param name 商品名
	 * @return　商品情報一覧
	 */
	public List<Item> showItemList(String name) {
		if (name == null) {
			List<Item> itemList = itemRepository.findAll();
			return itemList;
		} else {
			
			List<Item> itemList = itemRepository.findByItemName(name);
			return itemList;
		}
	}
}
