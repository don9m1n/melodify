package com.dmk.melodify.common.data;

import com.dmk.melodify.domain.member.entity.Member;
import com.dmk.melodify.domain.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevInitData {

    @Bean
    public CommandLineRunner init(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String password = passwordEncoder.encode("1234");
            memberRepository.save(Member.of("user1", password, "user1@naver.com", "user11", "https://picsum.photos/200/300"));
            memberRepository.save(Member.of("user2", password, "user2@naver.com", "user22", "https://picsum.photos/200/300"));
        };
    }
}
