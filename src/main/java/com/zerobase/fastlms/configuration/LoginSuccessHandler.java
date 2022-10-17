package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.service.LoginHistoryService;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginHistoryService loginHistoryService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        try {
            User user = (User) authentication.getPrincipal();

            loginHistoryService.loginLogWrite(user.getUsername(),
                    RequestUtils.getClientIP(httpServletRequest),
                    RequestUtils.getUserAgent(httpServletRequest));

            httpServletResponse.sendRedirect("/");
        } catch (Exception e) {
            httpServletRequest.setAttribute("message", "로그인 후 처리 실패");
            httpServletResponse.sendRedirect("common/error");
        }
    }
}
