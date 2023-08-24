package jpabook.jpashop.api

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @PutMapping("/api/v2/members/{id}")
    fun updateMemberV2(
        @PathVariable("id") id: Long,
        @RequestBody @Valid request: UpdateMemberRequest,
    ): UpdateMemberResponse {
        memberService.update(id, request.name)
        val foundMember = memberService.findOne(id)

        return UpdateMemberResponse(
            id = foundMember.id,
            name = foundMember.name,
        )
    }

    data class CreateMemberRequest(
        @NotEmpty
        val name: String,
    )

    data class CreateMemberResponse(
        val id: Long,
    )

    data class UpdateMemberRequest(
        val name: String,
    )

    data class UpdateMemberResponse(
        val id: Long,
        val name: String,
    )
}
