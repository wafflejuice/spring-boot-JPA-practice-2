package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
class Delivery(
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long = 0L,

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order? = null,

    @Embedded
    val address: Address?,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus? = null, //ENUM [READY(준비), COMP(배송)])
)
