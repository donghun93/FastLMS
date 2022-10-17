package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BannerDto {

    private Long id;
    private String bannerName;
    private String bannerLinkUrl;
    private String bannerFileUrl;
    private String open;
    private int orderNumber;
    private boolean show;
    private LocalDateTime regDate;

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getName())
                .bannerFileUrl(banner.getUrlFilename())
                .bannerLinkUrl(banner.getLinkUrl())
                .open(banner.getOpenStatus())
                .orderNumber(banner.getOrderNumber())
                .show(banner.isShowYn())
                .regDate(banner.getRegDate())
                .build();
    }

    public String getRegDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDate != null ? regDate.format(formatter) : "";

    }
}
