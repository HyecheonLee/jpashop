package com.hyecheon.jpashop.repository.order;

import com.hyecheon.jpashop.domain.Order;
import com.hyecheon.jpashop.domain.OrderSearch;
import com.hyecheon.jpashop.domain.QMember;
import com.hyecheon.jpashop.domain.QOrder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {
    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {
        final QOrder order = QOrder.order;
        final QMember member = QMember.member;

        final JPQLQuery<Order> query = from(order);

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query.leftJoin(order.member, member)
                    .where(member.name.contains(orderSearch.getMemberName()));
        }
        if (orderSearch.getOrderStatus() != null) {
            query.where(order.status.eq(orderSearch.getOrderStatus()));
        }
        return query.fetch();
    }
}
