package com.dmk.melodify.domain.member.service;

import com.dmk.melodify.domain.member.dto.JoinForm;
import com.dmk.melodify.domain.member.entity.Member;
import com.dmk.melodify.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(JoinForm joinForm) {
        Member member = Member.of(joinForm, passwordEncoder.encode(joinForm.getPassword()));
        memberRepository.save(member);
    }
}
