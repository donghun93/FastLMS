package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.LoginDetailDto;

import java.util.List;

public interface LoginHistoryService {
    void loginLogWrite(String userId, String ip, String agent);
    List<LoginDetailDto> findLoginDetail(String userId);
}
