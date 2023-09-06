package jpabook.jpashop.repository.order.query

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class OrderQueryRepository(
    private val em: EntityManager,
) {

    fun findOrderQueryDtos(): List<OrderQueryDto> {
        val result = findOrders()
        result.map {
            val orderItems = findOrderItems(it.orderId)
            it.orderItems = orderItems
        }
        return result
    }

    private fun findOrders(): List<OrderQueryDto> {
        return em.createQuery(
            "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d", OrderQueryDto::class.java
        ).resultList
    }

    private fun findOrderItems(orderId: Long): List<OrderItemQueryDto> {
        return em.createQuery(
            "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                    " from OrderItem oi" +
                    " join oi.item i" +
                    " where oi.order.id = :orderId", OrderItemQueryDto::class.java
        ).setParameter("orderId", orderId)
            .resultList
    }

    fun findAllByDto_optimization(): List<OrderQueryDto> {
        val result = findOrders()

        val orderIds = toOrderIds(result)
        val orderItemMap = findOrderItemMap(orderIds)

        result.forEach { it.orderItems = orderItemMap[it.orderId]!! }

        return result
    }

    fun findAllByDto_flat(): List<OrderFlatDto> {
        return em.createQuery(
            "select new" +
                    " jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d" +
                    " join o.orderItems oi" +
                    " join oi.item i", OrderFlatDto::class.java
        ).resultList
    }

    private fun toOrderIds(result: List<OrderQueryDto>): List<Long> =
        result.map { it.orderId }

    private fun findOrderItemMap(orderIds: List<Long>): Map<Long, List<OrderItemQueryDto>> {
        val orderItems = em.createQuery(
            "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                    " from OrderItem oi" +
                    " join oi.item i" +
                    " where oi.order.id in :orderIds", OrderItemQueryDto::class.java
        )
            .setParameter("orderIds", orderIds)
            .resultList

        return orderItems.groupBy { it.orderId }
    }
}
