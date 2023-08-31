package jpabook.jpashop.api

import jpabook.jpashop.domain.Order
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(
    private val orderRepository: OrderRepository,
) {

    @GetMapping("/api/v1/orders")
    fun ordersV1(): List<Order> {
        val all = orderRepository.findAllByString(OrderSearch())
        all.map { order ->
            order.member.name
            order.delivery.address
            order.orderItems.map { orderItem ->
                orderItem.item.name
            }
        }

        return all
    }
}
