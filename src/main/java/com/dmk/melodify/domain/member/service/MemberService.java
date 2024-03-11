package com.dmk.melodify.domain.member.service;

import com.dmk.melodify.common.AppConfig;
import com.dmk.melodify.common.security.MemberContext;
import com.dmk.melodify.common.util.DateTimeUtil;
import com.dmk.melodify.common.util.FileUtil;
import com.dmk.melodify.domain.member.dto.JoinForm;
import com.dmk.melodify.domain.member.dto.ModifyDto;
import com.dmk.melodify.domain.member.entity.Member;
import com.dmk.melodify.domain.member.repository.MemberRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(JoinForm joinForm) {

        memberRepository.findByUsername(joinForm.getUsername()).ifPresent(it -> {
            throw new RuntimeException("이미 가입된 회원입니다.");
        });

        MultipartFile profileImg = joinForm.getProfileImg();
        // TODO: 프로필 이미지 파일을 받은 경우에만 파일 업로드, 비어있는 경우에는 기본 이미지 세팅
        String profileImgPath = profileImg.isEmpty() ? "default.png" : uploadProfileImg(profileImg);

        Member member = Member.of(joinForm, passwordEncoder.encode(joinForm.getPassword()), profileImgPath);
        memberRepository.save(member);
    }

    public void modify(String username, ModifyDto modifyDto, MultipartFile newProfileImg) {
        Member member = getMemberByUsername(username);

        String newProfileImgPath = member.getProfileImg();
        if (!newProfileImg.isEmpty()) { // 새 프로필 이미지가 있는 경우
            // 로컬 저장소에 있는 기존 프로필 이미지 삭제
            removeProfileImg(member);
            newProfileImgPath = uploadProfileImg(newProfileImg);
        }

        member.modify(modifyDto, newProfileImgPath);
        memberRepository.save(member);
    }

    // 프로필 이미지 업로드
    private static String uploadProfileImg(MultipartFile profileImg) {
        String profileImgDirName = "member/" + DateTimeUtil.getCurrentDateFormat("yyyy_MM_dd"); // 폴더명
        String ext = FileUtil.getExt(profileImg.getOriginalFilename()); // 확장자
        String fileName = UUID.randomUUID() + "." + ext; // 파일 이름
        String profileImgDirPath = AppConfig.FILE_DIR_PATH + "/" + profileImgDirName;
        String profileImgFilePath = profileImgDirPath + "/" + fileName;

        new File(profileImgDirPath).mkdirs(); // 해당 폴더가 없는 경우 만들어준다.

        try {
            profileImg.transferTo(new File(profileImgFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return profileImgDirName + "/" + fileName;
    }

    public void changePassword(String username, String newPassword) {
        Member member = getMemberByUsername(username);
        member.changePassword(passwordEncoder.encode(newPassword));

        memberRepository.save(member);
    }

    public void removeProfileImg(Member member) {
        log.debug("profileImg: {}", member.getProfileImg());
        member.removeProfileImgOnStorage();
        member.setProfileImg(null);

        memberRepository.save(member);
    }

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }
}
