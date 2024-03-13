package com.dmk.melodify.common.security;

import com.dmk.melodify.common.util.UrlEncoderUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String referer = request.getHeader("REFERER");
        log.debug("로그아웃 요청이 들어온 주소: {}", referer);

        String requestType = referer.substring(referer.lastIndexOf("/") + 1);
        log.debug("어떤 api에서 요청이 들어왔는가: {}", requestType);


        // toastr 알림 메세지 생성
        String title = "";
        String message = "";
        switch (requestType) {
            case "delete" -> {
                title = UrlEncoderUtil.encode("%s".formatted("회원탈퇴 성공!"));
                message = UrlEncoderUtil.encode("%s".formatted("아쉽지만 보내드릴게요.."));
            }
            case "modify-password" -> {
                title = UrlEncoderUtil.encode("%s".formatted("비밀번호 변경 성공!"));
                message = UrlEncoderUtil.encode("%s".formatted("변경된 비밀번호로 다시 로그인 해주세요!"));
            }
            default -> {
                title = UrlEncoderUtil.encode("%s".formatted("로그아웃 성공!"));
                message = UrlEncoderUtil.encode("%s".formatted("다시 와주실 거죠..?"));
            }
        }

        String redirectUrl = "/?title=%s&message=%s".formatted(title, message);
        log.debug("redirectUrl: {}", redirectUrl);

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(redirectUrl);
    }
}
