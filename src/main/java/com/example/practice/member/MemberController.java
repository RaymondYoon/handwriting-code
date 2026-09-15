package com.example.practice.member;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping
    public Member save(@RequestBody Member member) {
        return memberService.save(member);
    }

    @GetMapping
    public List<Member> findAll() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public Member findById(@PathVariable Long id){
        return memberService.findById(id);
    }

    @PutMapping("/{id}")
    public Member update(
            @PathVariable Long id,
            @RequestBody Member member
    ){
        return memberService.update(
                id,
                member.name,
                member.password,
                member.email
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        memberService.delete(id);
    }


}
