package com.hyecheon.jpashop.service;

import com.hyecheon.jpashop.domain.Member;
import com.hyecheon.jpashop.repository.MemberRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Data
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     *
     * @param member
     * @return member
     */
    public Member join(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }


    private Optional<Boolean> validateDuplicateMember(Member member) throws IllegalStateException {
        final List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        return Optional.of(true);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("잘못된 ID 입니다."));
    }
}
