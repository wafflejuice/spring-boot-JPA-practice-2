package jpabook.jpashop.api

import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberApiController(
    private val memberService: MemberService,
) {

    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id = id)
    }

    data class CreateMemberResponse(
        val id: Long,
    )
}
