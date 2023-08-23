package jpabook.jpashop.api

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
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
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id = id)
    }

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Valid request: CreateMemberRequest): CreateMemberResponse {
        val member = Member(
            name = request.name,
            address = null,
        )

        val id = memberService.join(member)
        return CreateMemberResponse(id = id)
    }

    data class CreateMemberRequest(
        @NotEmpty
        val name: String,
    )

    data class CreateMemberResponse(
        val id: Long,
    )
}
