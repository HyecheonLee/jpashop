package com.hyecheon.jpashop.repository.order;

import com.hyecheon.jpashop.domain.Order;
import com.hyecheon.jpashop.domain.OrderSearch;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> findAll(OrderSearch orderSearch);
}
