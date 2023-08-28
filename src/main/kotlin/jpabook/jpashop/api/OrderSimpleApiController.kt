package jpabook.jpashop.api

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class OrderSimpleApiController(
    private val orderRepository: OrderRepository,
) {

    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> {
        val all = orderRepository.findAllByString(OrderSearch())
        return all
    }

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<SimpleOrderDto> {
        val orders = orderRepository.findAllByString(OrderSearch())

        return orders.map { order ->
            SimpleOrderDto.of(order = order)
        }
    }

    data class SimpleOrderDto(
        val orderId: Long,
        val name: String,
        val orderDate: LocalDateTime,
        val orderStatus: OrderStatus,
        val address: Address?,
    ) {
        companion object {
            fun of(order: Order) =
                SimpleOrderDto(
                    orderId = order.id,
                    name = order.member.name,
                    orderDate = order.orderDate,
                    orderStatus = order.status,
                    address = order.delivery.address,
                )
        }
    }
}
