package jpabook.jpashop.domain;

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member, //주문 회원

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableList<OrderItem> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery, //배송정보

    val orderDate: LocalDateTime, //주문시간

    @Enumerated(EnumType.STRING)
    val status: OrderStatus, //주문상태 [ORDER, CANCEL]
) {

    companion object {
        fun createOrder(member: Member, delivery: Delivery, orderItems: List<OrderItem>): Order {
            val order = Order(
                member = member,
                delivery = delivery,
                orderDate = LocalDateTime.now(),
                status = OrderStatus.ORDER,
            )
            orderItems.forEach {
                order.addOrderItem(it)
            }
            return order
        }
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }
}
