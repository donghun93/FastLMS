package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.admin.service.FileUploadService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/banner")
public class AdminBannerController {

    private final FileUploadService fileUploadService;
    private final BannerService bannerService;

    @GetMapping("/list.do")
    public String bannerList(Model model) {

        List<BannerDto> bannerList = bannerService.getBannerList();

        model.addAttribute("banners", bannerList);
        model.addAttribute("totalCount", bannerList.size());

        return "/admin/banner/list";
    }

    @GetMapping(value = {"/add.do", "/edit.do"})
    public String bannerAdd(Model model, HttpServletRequest request
            , BannerDto parameter) {

        boolean editMode = request.getRequestURI().contains("/edit.do");
        BannerDto detail = new BannerDto();

        if (editMode) {
            long id = parameter.getId();
            BannerDto bannerDto = bannerService.getBanner(id);
            if (bannerDto == null) {
                // error 처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = bannerDto;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "/admin/banner/add";
    }

    @PostMapping(value = {"/add.do", "/edit.do"})
    public String bannerAddSubmit(Model model,
                                  MultipartFile file,
                                  HttpServletRequest request,
                                  @ModelAttribute BannerDto bannerDto) {

        BannerDto dto = bannerDto;

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if(!editMode) {
            try {
                String[] fileNames = fileUploadService.upload(file.getOriginalFilename(), file.getInputStream());
                bannerService.bannerAdd(fileNames[0], fileNames[1], bannerDto);

            }  catch (Exception e) {

            }
        } else {
            try {
                String[] fileNames = null;
                if(file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
                    fileNames = fileUploadService.upload(file.getOriginalFilename(), file.getInputStream());
                    bannerService.bannerUpdate(fileNames[0], fileNames[1], bannerDto);
                } else {
                    bannerService.bannerUpdate(bannerDto);
                }
            }  catch (Exception e) {

            }
        }

        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/delete.do")
    public String deleteBanner(@RequestParam String idList) {
        List<String> fileList = bannerService.bannerDelete(idList);
        fileUploadService.deleteFile(fileList);
        return "redirect:/admin/banner/list.do";
    }
}
