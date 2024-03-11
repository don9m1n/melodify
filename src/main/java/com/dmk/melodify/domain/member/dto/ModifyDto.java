package com.dmk.melodify.domain.member.dto;

import com.dmk.melodify.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class ModifyDto {

    private String username;
    private String email;
    private String nickname;
    private String profileImg;
    private MultipartFile newProfileImg;

    public static ModifyDto fromEntity(Member member) {
        return ModifyDto.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImg(member.getProfileImg())
                .build();
    }
}
