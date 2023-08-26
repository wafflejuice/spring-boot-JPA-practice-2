package jpabook.jpashop

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Delivery
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.repository.MemberRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InitDb(
    private val memberRepository: MemberRepository,
    private val initService: InitService,
) {
    @PostConstruct
    fun init() {
        initService.dbInit1()
        initService.dbInit2()
    }

    @Component
    @Transactional
    class InitService(
        private val em: EntityManager,
    ) {
        fun dbInit1() {
            val member = createMember(
                name = "userA",
                city = "서울",
                street = "1",
                zipcode = "1111",
            )
            em.persist(member)

            val book1 = createBook(
                name = "JPA1 BOOK",
                price = 10000,
                stockQuantity = 100,
            )
            em.persist(book1)

            val book2 = createBook(
                name = "JPA2 BOOK",
                price = 20000,
                stockQuantity = 100,
            )
            em.persist(book2)

            val orderItem1 = OrderItem.createOrderItem(book1, 10000, 1)
            val orderItem2 = OrderItem.createOrderItem(book2, 20000, 2)

            val delivery = createDelivery(member)
            val order = Order.createOrder(member, delivery, listOf(orderItem1, orderItem2))
            em.persist(order)
        }

        fun dbInit2() {
            val member = createMember(
                name = "userB",
                city = "진주",
                street = "2",
                zipcode = "2222",
            )
            em.persist(member)

            val book1 = createBook(
                name = "SPRING1 BOOK",
                price = 20000,
                stockQuantity = 200,
            )
            em.persist(book1)

            val book2 = createBook(
                name = "SPRING2 BOOK",
                price = 40000,
                stockQuantity = 300,
            )
            em.persist(book2)

            val orderItem1 = OrderItem.createOrderItem(book1, 20000, 3)
            val orderItem2 = OrderItem.createOrderItem(book2, 40000, 4)

            val delivery = createDelivery(member)
            val order = Order.createOrder(member, delivery, listOf(orderItem1, orderItem2))
            em.persist(order)
        }

        private fun createDelivery(member: Member): Delivery {
            val delivery = Delivery(
                address = member.address,
            )
            return delivery
        }

        private fun createBook(
            name: String,
            price: Int,
            stockQuantity: Int,
        ): Book {
            val book1 = Book(
                name = name,
                price = price,
                stockQuantity = stockQuantity,
            )
            return book1
        }

        private fun createMember(
            name: String,
            city: String,
            street: String,
            zipcode: String,
        ): Member {
            val member = Member(
                name = name,
                address = Address(
                    city,
                    street,
                    zipcode,
                ),
                orders = mutableListOf(),
            )
            return member
        }
    }
}
