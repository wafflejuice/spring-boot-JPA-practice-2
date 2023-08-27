package jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0L,

    var name: String,

    @Embedded
    val address: Address?,

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = mutableListOf(),
)
