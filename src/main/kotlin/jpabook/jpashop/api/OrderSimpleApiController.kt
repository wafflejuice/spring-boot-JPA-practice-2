package jpabook.jpashop.api

import jpabook.jpashop.domain.Order
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderSimpleApiController(
    private val orderRepository: OrderRepository,
    private val orderSimpleQueryRepository: OrderSimpleQueryRepository,
) {

    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> {
        val all = orderRepository.findAllByString(OrderSearch())
        return all
    }

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<OrderSimpleQueryDto> {
        val orders = orderRepository.findAllByString(OrderSearch())

        return orders.map { order ->
            OrderSimpleQueryDto.of(order = order)
        }
    }

    @GetMapping("/api/v3/simple-orders")
    fun ordersV3(): List<OrderSimpleQueryDto> {
        val orders = orderRepository.findAllWithMemberDelivery()

        return orders.map { order ->
            OrderSimpleQueryDto.of(order = order)
        }
    }

    @GetMapping("/api/v4/simple-orders")
    fun ordersV4(): List<OrderSimpleQueryDto> {
        return orderSimpleQueryRepository.findOrderDtos()
    }
}
