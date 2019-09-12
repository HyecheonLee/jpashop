package com.hyecheon.jpashop.web;

import com.hyecheon.jpashop.domain.Member;
import com.hyecheon.jpashop.domain.Order;
import com.hyecheon.jpashop.domain.OrderSearch;
import com.hyecheon.jpashop.domain.item.Item;
import com.hyecheon.jpashop.service.ItemService;
import com.hyecheon.jpashop.service.MemberService;
import com.hyecheon.jpashop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    public OrderController(OrderService orderService, MemberService memberService, ItemService itemService) {
        this.orderService = orderService;
        this.memberService = memberService;
        this.itemService = itemService;
    }

    @GetMapping("/orders")
    public String orders(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        final List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @GetMapping("/order")
    public String createForm(Model model) {
        final List<Member> members = memberService.findMembers();
        final List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") Long count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }
}
