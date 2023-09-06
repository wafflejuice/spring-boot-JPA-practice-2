package jpabook.jpashop.repository.order.query

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderFlatDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address,

    val itemName: String,
    val orderPrice: Int,
    val count: Int,
)
