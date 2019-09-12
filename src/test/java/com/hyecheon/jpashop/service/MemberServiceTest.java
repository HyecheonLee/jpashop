package com.hyecheon.jpashop.service;

import com.hyecheon.jpashop.domain.Member;
import com.hyecheon.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given
        final Member member = new Member();
        member.setName("kim");

        //when
        final Member savedMember = memberService.join(member);
        final Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 멤버입니다."));

        //then
        assertEquals(member, findMember);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() {
        //given
        final Member member1 = new Member();
        member1.setName("kim");
        final Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        memberService.join(member2);//예외가 발생해야 한다.

        fail("예외가 발생해야 한다");
    }
}