package jpabook.jpashop.api

import jpabook.jpashop.domain.Order
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderSimpleApiController(
    private val orderRepository: OrderRepository,
) {

    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): MutableList<Order>? {
        val all = orderRepository.findAllByString(OrderSearch())
        return all
    }
}
