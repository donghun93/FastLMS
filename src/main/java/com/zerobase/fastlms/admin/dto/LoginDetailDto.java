package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginDetailDto {
    private LocalDateTime loginDate;
    private String userIp;
    private String userAgent;

    public static LoginDetailDto of(LoginHistory loginHistory) {
        return LoginDetailDto.builder()
                .loginDate(loginHistory.getHistoryInfo().getLoginDate())
                .userIp(loginHistory.getHistoryInfo().getUserIp())
                .userAgent(loginHistory.getHistoryInfo().getUserAgent())
                .build();
    }

    public String loginDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return loginDate != null ? loginDate.format(formatter) : "";
    }
}
