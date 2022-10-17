package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.LoginDetailDto;
import com.zerobase.fastlms.member.entity.HistoryInfo;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void loginLogWrite(String userId, String ip, String agent) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));

        LoginHistory loginHistory = LoginHistory.builder()
                .member(member)
                .historyInfo(createHistoryInfo(ip, agent))
                .build();
        loginHistoryRepository.save(loginHistory);
    }

    @Override
    public List<LoginDetailDto> findLoginDetail(String userId) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));

        return loginHistoryRepository.findByMemberQuery(member)
                .stream().map(LoginDetailDto::of).collect(Collectors.toList());
    }

    private HistoryInfo createHistoryInfo(String ip, String agent) {
        return new HistoryInfo(LocalDateTime.now(), ip, agent);
    }
}
