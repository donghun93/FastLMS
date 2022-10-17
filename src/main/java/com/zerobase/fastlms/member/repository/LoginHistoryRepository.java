package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    @Query("select lh from LoginHistory lh join lh.member m where m =:member order by lh.historyInfo.loginDate desc ")
    List<LoginHistory> findByMemberQuery(Member member);
}
