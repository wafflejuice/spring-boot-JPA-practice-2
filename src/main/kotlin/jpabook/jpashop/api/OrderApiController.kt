package jpabook.jpashop.api

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import jpabook.jpashop.repository.order.query.OrderItemQueryDto
import jpabook.jpashop.repository.order.query.OrderQueryDto
import jpabook.jpashop.repository.order.query.OrderQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.stream.Collectors.groupingBy
import java.util.stream.Collectors.mapping
import java.util.stream.Collectors.toList


@RestController
class OrderApiController(
    private val orderRepository: OrderRepository,
    private val orderQueryRepository: OrderQueryRepository,
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

    @GetMapping("/api/v2/orders")
    fun ordersV2(): List<OrderDto> {
        val orders = orderRepository.findAllByString(OrderSearch())
        val collect = orders.map { OrderDto.of(it) }
        return collect
    }

    @GetMapping("/api/v3/orders")
    fun ordersV3(): List<OrderDto> {
        val orders = orderRepository.findAllWithItem()
        val collect = orders.map { OrderDto.of(it) }
        return collect
    }

    @GetMapping("/api/v3.1/orders")
    fun ordersV3_page(
        @RequestParam(value = "offset", defaultValue = "0") offset: Int,
        @RequestParam(value = "offset", defaultValue = "100") limit: Int,
    ): List<OrderDto> {
        val orders = orderRepository.findAllWithMemberDelivery(offset, limit)
        val collect = orders.map { OrderDto.of(it) }
        return collect
    }

    @GetMapping("/api/v4/orders")
    fun ordersV4(): List<OrderQueryDto> {
        return orderQueryRepository.findOrderQueryDtos()
    }

    @GetMapping("/api/v5/orders")
    fun ordersV5(): List<OrderQueryDto> {
        return orderQueryRepository.findAllByDto_optimization()
    }

    @GetMapping("/api/v6/orders")
    fun ordersV6(): List<OrderQueryDto> {
        val flats = orderQueryRepository.findAllByDto_flat()

        return flats.stream()
            .collect(
                groupingBy(
                    { o -> OrderQueryDto(o.orderId, o.name, o.orderDate, o.orderStatus, o.address) },
                    mapping({ o -> OrderItemQueryDto(o.orderId, o.itemName, o.orderPrice, o.count) }, toList())
                )
            )
            .map { e ->
                OrderQueryDto(e.key.orderId, e.key.name, e.key.orderDate, e.key.orderStatus, e.key.address).also {
                    it.orderItems = e.value
                }
            }
    }

    data class OrderDto(
        val orderId: Long,
        val name: String,
        val orderDate: LocalDateTime,
        val orderStatus: OrderStatus,
        val address: Address?,
        val orderItems: List<OrderItemDto>,
    ) {
        companion object {
            fun of(order: Order): OrderDto {
                order.orderItems.map { it.item.name }

                return OrderDto(
                    orderId = order.id,
                    name = order.member.name,
                    orderDate = order.orderDate,
                    orderStatus = order.status,
                    address = order.delivery.address,
                    orderItems = order.orderItems.map { OrderItemDto.of(it) },
                )
            }
        }
    }

    data class OrderItemDto(
        val itemName: String,
        val orderPrice: Int,
        val count: Int,
    ) {
        companion object {
            fun of(orderItem: OrderItem) =
                OrderItemDto(
                    itemName = orderItem.item.name,
                    orderPrice = orderItem.orderPrice,
                    count = orderItem.count,
                )
        }
    }
}
