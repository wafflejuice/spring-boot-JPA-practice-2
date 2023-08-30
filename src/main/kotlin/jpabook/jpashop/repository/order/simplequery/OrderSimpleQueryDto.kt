package jpabook.jpashop.repository.order.simplequery

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderSimpleQueryDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address?,
) {
    companion object {
        fun of(order: Order) =
            OrderSimpleQueryDto(
                orderId = order.id,
                name = order.member.name,
                orderDate = order.orderDate,
                orderStatus = order.status,
                address = order.delivery.address,
            )
    }
}
