package com.hyecheon.jpashop.service;

import com.hyecheon.jpashop.domain.item.Item;
import com.hyecheon.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Id가 잘못임."));
    }
}
