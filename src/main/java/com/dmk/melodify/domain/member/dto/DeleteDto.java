package com.dmk.melodify.domain.member.dto;

import com.dmk.melodify.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteDto {

    private String username;
    private LocalDateTime createdAt;

    public static DeleteDto fromEntity(Member member) {
        return DeleteDto.builder()
                .username(member.getUsername())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
