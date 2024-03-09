package com.dmk.melodify.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class JoinForm {

    private String username;

    private String password;

    private String email;

    private String nickname;

    private MultipartFile profileImg;

}
