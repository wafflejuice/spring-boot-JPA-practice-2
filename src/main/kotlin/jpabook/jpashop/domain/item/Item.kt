package jpabook.jpashop.domain.item;

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.ManyToMany
import jpabook.jpashop.domain.Category
import jpabook.jpashop.exception.NotEnoughStockException

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item(
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    val id: Long = 0L,
    val name: String,
    val price: Int,
    var stockQuantity: Int,
    @ManyToMany(mappedBy = "items")
    val categories: List<Category> = emptyList(),
) {
    //==비즈니스 로직==//
    //==비즈니스 로직==//
    /**
     * stock 증가
     */
    fun addStock(quantity: Int) {
        stockQuantity += quantity
    }

    /**
     * stock 감소
     */
    fun removeStock(quantity: Int) {
        val restStock = stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock")
        }
        stockQuantity = restStock
    }
}
