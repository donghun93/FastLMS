package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    @Transactional
    public void bannerAdd(String fileName, String urlFileName, BannerDto bannerDto) {

        Banner banner = Banner.builder()
                .name(bannerDto.getBannerName())
                .linkUrl(bannerDto.getBannerLinkUrl())
                .orderNumber(bannerDto.getOrderNumber())
                .openStatus(bannerDto.getOpen())
                .showYn(bannerDto.isShow())
                .filename(fileName)
                .urlFilename(urlFileName)
                .build();

        banner.setRegDate(LocalDateTime.now());
        bannerRepository.save(banner);
    }

    @Transactional
    public void bannerUpdate(String fileName, String urlFileName, BannerDto bannerDto) {
        Banner banner = bannerRepository.findById(bannerDto.getId())
                .orElseThrow(() -> new IllegalStateException("배너가 존재하지 않습니다."));
        banner.updateBanner(fileName, urlFileName,
                bannerDto.getBannerLinkUrl(),
                bannerDto.getBannerName(),
                bannerDto.getOrderNumber(),
                bannerDto.getOpen(),
                bannerDto.isShow());

    }

    @Transactional
    public void bannerUpdate(BannerDto bannerDto) {
        Banner banner = bannerRepository.findById(bannerDto.getId())
                .orElseThrow(() -> new IllegalStateException("배너가 존재하지 않습니다."));
        banner.updateBanner(
                bannerDto.getBannerLinkUrl(),
                bannerDto.getBannerName(),
                bannerDto.getOrderNumber(),
                bannerDto.getOpen() ,
                bannerDto.isShow());

    }

    public List<BannerDto> getBannerList() {
        return bannerRepository.findAll(Sort.by(Sort.Direction.DESC, "orderNumber"))
                .stream().map(BannerDto::of)
                .collect(Collectors.toList());
    }

    public List<BannerDto> getBannerShow() {
        return bannerRepository.findByShowYnTrue()
                .stream().map(BannerDto::of)
                .collect(Collectors.toList());
    }

    public BannerDto getBanner(Long id) {
        return bannerRepository.findById(id)
                .map(BannerDto::of)
                .orElseThrow(() -> new IllegalStateException("배너가 존재하지 않습니다."));
    }

    public List<String> bannerDelete(String idList) {
        List<String> lists = new ArrayList<>();

        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }

                if (id > 0) {
                    Banner banner = bannerRepository.findById(id).orElseThrow(() ->
                            new IllegalStateException("배너가 존재하지 않습니다."));
                    lists.add(banner.getFilename());
                    bannerRepository.deleteById(id);
                }
            }
        }

        return lists;
    }
}
