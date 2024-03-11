package com.dmk.melodify.common.security;


import com.dmk.melodify.domain.member.entity.Member;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class MemberContext extends User {

    private final Long id;
    private final String email;
    private final String profileImg;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.id = member.getId();
        this.email = member.getEmail();
        this.profileImg = member.getProfileImg();
    }
}
