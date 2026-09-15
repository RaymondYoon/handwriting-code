package com.example.practice.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member save(Member member){
        return memberRepository.save(member);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }
    public Member findById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("회원을 찾을 수 없습니다.")));
    }

    @Transactional
    public Member update(Long id, String name, String password, String email){
        Member member = findById(id);

        member.name = name;
        member.password = password;
        member.email = email;

        return member;
    }

    public void delete(Long id){
        Member member = findById(id);
        memberRepository.delete(member);
    }

}
