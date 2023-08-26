package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jpabook.jpashop.domain.Category

@Entity
@DiscriminatorValue("B")
class Book(
    name: String,
    price: Int,
    stockQuantity: Int,
    categories: List<Category> = emptyList(),
    var author: String? = null,
    var isbn: String? = null,
) : Item(
    name = name,
    price = price,
    stockQuantity = stockQuantity,
    categories = categories,
)
