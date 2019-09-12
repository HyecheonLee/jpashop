package com.hyecheon.jpashop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    private DeliveryStatus status;

    public Delivery(Address address) {
        this.address = address;
    }
}
