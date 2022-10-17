package com.zerobase.fastlms.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private HistoryInfo historyInfo;

    @Builder
    private LoginHistory(Member member, HistoryInfo historyInfo) {
        this.member = member;
        this.historyInfo = historyInfo;
    }
}
