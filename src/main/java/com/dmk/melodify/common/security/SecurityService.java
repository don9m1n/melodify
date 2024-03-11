package com.dmk.melodify.common.security;

import com.dmk.melodify.domain.member.entity.Member;
import com.dmk.melodify.domain.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("시큐리티 폼 로그인시 loadByUsername() 실행!: {}", username);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found member"));

        List<GrantedAuthority> authorities = new ArrayList<>();

        switch (member.getRole()) {
            case MEMBER -> authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
            case ADMIN -> authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new MemberContext(member, authorities);
    }

}
