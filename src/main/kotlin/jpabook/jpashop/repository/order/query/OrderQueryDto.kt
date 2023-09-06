package jpabook.jpashop.repository.order.query

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderQueryDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address,
) {
    var orderItems: List<OrderItemQueryDto> = emptyList()

    override fun hashCode(): Int {
        return orderId.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other is OrderQueryDto) {
            return orderId == other.orderId
        }

        return false
    }
}
