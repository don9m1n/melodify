package com.dmk.melodify.domain.member.controller;

import com.dmk.melodify.domain.member.dto.JoinForm;
import com.dmk.melodify.domain.member.dto.ModifyDto;
import com.dmk.melodify.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/modify")
    public String modifyForm(Authentication authentication, Model model) {
        ModifyDto modifyDto = ModifyDto.fromEntity(memberService.getMemberByUsername(authentication.getName()));
        model.addAttribute("modifyDto", modifyDto);

        return "member/modify";
    }

    @PostMapping("/modify")
    public String modify(Authentication authentication, ModifyDto modifyDto, MultipartFile newProfileImg) {
        memberService.modify(authentication.getName(), modifyDto, newProfileImg);
        return "redirect:/";
    }

    @GetMapping("/modify-password")
    public String modifyPasswordForm() {
        return "member/modify-password";
    }

    @PostMapping("/modify-password")
    public String modifyPassword(Authentication authentication, String newPassword, String newPasswordConfirm) {
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다!");
        }

        memberService.changePassword(authentication.getName(), newPassword);
        return "redirect:/members/logout";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/join")
    public String joinForm(@ModelAttribute JoinForm joinForm) {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(JoinForm joinForm) {
        memberService.join(joinForm);
        return "redirect:/members/login";
    }
}
