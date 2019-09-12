package com.hyecheon.jpashop.repository;

import com.hyecheon.jpashop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
