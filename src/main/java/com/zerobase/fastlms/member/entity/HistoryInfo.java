package com.zerobase.fastlms.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryInfo {

    private LocalDateTime loginDate;
    private String userIp;
    private String userAgent;

    public HistoryInfo(LocalDateTime loginDate, String userIp, String userAgent) {
        this.loginDate = loginDate;
        this.userIp = userIp;
        this.userAgent = userAgent;
    }
}
