package com.zerobase.fastlms.admin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long id;

    private String name;
    private String filename;
    private String urlFilename;
    private String linkUrl;
    private int orderNumber;

    private String openStatus;
    private boolean showYn;

    private LocalDateTime regDate;

    @Builder
    private Banner(String name, String filename, String urlFilename, String linkUrl, int orderNumber, String openStatus, boolean showYn) {
        this.name = name;
        this.filename = filename;
        this.urlFilename = urlFilename;
        this.linkUrl = linkUrl;
        this.orderNumber = orderNumber;
        this.openStatus = openStatus;
        this.showYn = showYn;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public void updateBanner(String fileName, String urlFileName,
                             String linkUrl, String bannerName, int orderNumber,
                             String openStatus, boolean show) {
        this.filename = fileName;
        this.urlFilename = urlFileName;
        this.linkUrl = linkUrl;
        this.name = bannerName;
        this.orderNumber = orderNumber;
        this.openStatus = openStatus;
        this.showYn = show;
    }

    public void updateBanner(String linkUrl, String bannerName, int orderNumber,
                             String openStatus, boolean show) {
        this.linkUrl = linkUrl;
        this.name = bannerName;
        this.orderNumber = orderNumber;
        this.openStatus = openStatus;
        this.showYn = show;
    }
}
