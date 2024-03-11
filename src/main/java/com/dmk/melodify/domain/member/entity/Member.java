package com.dmk.melodify.domain.member.entity;

import com.dmk.melodify.common.auditing.BaseEntity;
import com.dmk.melodify.common.util.AppConfig;
import com.dmk.melodify.domain.member.dto.JoinForm;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.File;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String nickname;

    @Setter
    private String profileImg;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    private Member(String username, String password, String email, String nickname, String profileImg, MemberRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.role = role;
    }

    public static Member of(JoinForm form, String password, String profileImgRelPath) {
        return Member.builder()
                .username(form.getUsername())
                .password(password)
                .email(form.getEmail())
                .nickname(form.getNickname())
                .role(MemberRole.MEMBER)
                .profileImg(profileImgRelPath)
                .build();
    }

    public void removeProfileImgOnStorage() {
        if (profileImg == null || profileImg.isEmpty()) {
            return;
        }

        String profileImgPath = getProfileImgPath();
        log.debug("profileImgPath: {}", profileImgPath);
        new File(profileImgPath).delete();
    }

    private String getProfileImgPath() {
        return AppConfig.FILE_DIR_PATH + "/" + profileImg;
    }
}
