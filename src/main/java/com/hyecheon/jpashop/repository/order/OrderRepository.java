package com.hyecheon.jpashop.repository.order;

import com.hyecheon.jpashop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

}


