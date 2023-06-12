package com.order.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.exceptions.ItemNotFoundException;
import com.order.model.Item;
import com.order.repository.ItemRepository;

@Service
public class ItemService {

	private final ItemRepository itemRepository;

	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

	public Item getItemById(Long id) {
		return itemRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Item not found with ID: " + id));
	}

	public List<Item> getAllItemsByIds(List<Long> itemIds) {
		return itemRepository.findAllById(itemIds);
	}

}
