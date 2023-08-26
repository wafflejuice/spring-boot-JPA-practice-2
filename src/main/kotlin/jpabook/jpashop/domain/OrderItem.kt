package jpabook.jpashop.domain;

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jpabook.jpashop.domain.item.Item

@Entity
@Table(name = "order_item")
class OrderItem(
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item, //주문 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null, //주문
    val orderPrice: Int, //주문 가격
    val count: Int, //주문 수량
) {
    companion object {
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem(
                item = item,
                orderPrice = orderPrice,
                count = count,
            )

            item.removeStock(count)
            return orderItem
        }
    }
}
