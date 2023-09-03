package jpabook.jpashop.api

import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

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
