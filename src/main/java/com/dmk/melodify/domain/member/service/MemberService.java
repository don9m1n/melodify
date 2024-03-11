package com.dmk.melodify.domain.member.service;

import com.dmk.melodify.common.AppConfig;
import com.dmk.melodify.common.util.DateTimeUtil;
import com.dmk.melodify.common.util.FileUtil;
import com.dmk.melodify.domain.member.dto.JoinForm;
import com.dmk.melodify.domain.member.entity.Member;
import com.dmk.melodify.domain.member.repository.MemberRepository;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.debug("프로필 이미지가 없나요? {}", profileImg.isEmpty());

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

        String profileImgRelPath = profileImgDirName + "/" + fileName;
        Member member = Member.of(joinForm, passwordEncoder.encode(joinForm.getPassword()), profileImgRelPath);
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

}
