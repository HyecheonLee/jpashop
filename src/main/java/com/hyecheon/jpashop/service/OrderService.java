package com.hyecheon.jpashop.service;

import com.hyecheon.jpashop.domain.*;
import com.hyecheon.jpashop.domain.item.Item;
import com.hyecheon.jpashop.repository.MemberRepository;
import com.hyecheon.jpashop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository, ItemService itemService) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    public Order order(Long memberId, Long itemId, Long count) {
        //엔티티 조회
        final Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("잘못된 id 입니다."));
        final Item item = itemService.findById(itemId);

        //배송정보 생성
        final Delivery delivery = new Delivery(member.getAddress());
        //주문상품 생성
        final OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //주문 생성
        final Order order = Order.createOrder(member, delivery, orderItem);

        return orderRepository.save(order);
    }

    public void cancelOrder(Long orderId) {
        final Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("orderId 가 잘못 됨"));
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
