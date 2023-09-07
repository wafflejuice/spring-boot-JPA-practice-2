package jpabook.jpashop.repository

import jpabook.jpashop.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    
    fun findByName(name: String): List<Member>
}
