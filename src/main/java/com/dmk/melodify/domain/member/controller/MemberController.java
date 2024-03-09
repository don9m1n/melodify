package com.dmk.melodify.domain.member.controller;

import com.dmk.melodify.domain.member.dto.JoinForm;
import com.dmk.melodify.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
