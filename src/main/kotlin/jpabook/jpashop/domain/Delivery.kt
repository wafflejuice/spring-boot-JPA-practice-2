package jpabook.jpashop.domain;

import jakarta.persistence.*

@Entity
class Delivery(
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long = 0L,
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order? = null,
    @Embedded
    val address: Address?,
    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus? = null, //ENUM [READY(준비), COMP(배송)])
)
