package com.hyecheon.jpashop.service;

import com.hyecheon.jpashop.domain.Address;
import com.hyecheon.jpashop.domain.Member;
import com.hyecheon.jpashop.domain.Order;
import com.hyecheon.jpashop.domain.OrderStatus;
import com.hyecheon.jpashop.domain.item.Book;
import com.hyecheon.jpashop.domain.item.Item;
import com.hyecheon.jpashop.domain.item.NotEnoughStockException;
import com.hyecheon.jpashop.repository.order.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void 상품주문() {
        //given
        Member member = createMember();
        final Item item = createBook("시골 JPA", 10000L, 10L);
        Long orderCount = 2L;

        //When
        final Order order = orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        final Order getOrder = orderRepository.findById(order.getId()).get();

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000L * 2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", Optional.of(8L), Optional.of(item.getStockQuantity()));
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() {

        //given
        final Member member = createMember();
        final Item item = createBook("시골 JPA", 10000L, 10L);
        Long orderCount = 11L;

        orderService.order(member.getId(), item.getId(), orderCount);
        fail("재고 수량 부족예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() {
        final Member member = createMember();
        final Item item = createBook("시골 JPA", 10000L, 10L);
        Long orderCount = 2L;
        final Order order = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(order.getId());

        final Order getOrder = orderRepository.findById(order.getId()).get();

        assertEquals("주문 취소시 상태는 cancel 이다", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", Optional.of(10L), Optional.of(item.getStockQuantity()));

    }

    private Member createMember() {
        final Member member = new Member();
        member.setName("hello");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Item createBook(String name, Long price, Long stockQuantity) {
        final Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}