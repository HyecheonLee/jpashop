package com.hyecheon.jpashop.repository;

import com.hyecheon.jpashop.domain.Order;
import com.hyecheon.jpashop.domain.OrderSearch;
import com.hyecheon.jpashop.domain.QOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    public List<Order> findAll(OrderSearch orderSearch) {
        final JPAQuery<Order> query = new JPAQuery<>(em);
        final QOrder order = QOrder.order;
        final BooleanBuilder builder = new BooleanBuilder();
        if (orderSearch.getOrderStatus() != null) {
            builder.and(order.status.eq(orderSearch.getOrderStatus()));
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            builder.and(order.member.name.contains(orderSearch.getMemberName()));
        }
        return query.from(order)
                .where(builder)
                .fetch();
    }
}
