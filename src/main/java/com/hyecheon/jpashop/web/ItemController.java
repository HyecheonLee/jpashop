package com.hyecheon.jpashop.web;

import com.hyecheon.jpashop.domain.item.Book;
import com.hyecheon.jpashop.domain.item.Item;
import com.hyecheon.jpashop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items/new")
    public String createForm() {
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(Book item) {
        itemService.saveItem(item);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        final List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        final Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "items/updateItemForm";
    }

    @PatchMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, @ModelAttribute("item") Book book) {
        book.setId(itemId);
        itemService.saveItem(book);
        return "redirect:/items";
    }
}

