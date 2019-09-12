package com.hyecheon.jpashop.domain;

import com.hyecheon.jpashop.domain.item.Item;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long orderPrice;

    private Long count;

    public static OrderItem createOrderItem(Item item, Long orderPrice, Long count) {
        final OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    /*주문 취소*/
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    /*주문상품 전체 가격 조회*/
    public Long getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
