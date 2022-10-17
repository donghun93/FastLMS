package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.util.DirectoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@Service
public class FileUploadService {

    private final String BASE_URL_PATH = "/files";
    private final String BASE_LOCAL_PATH = "C:\\Users\\hasfw\\Desktop\\zerobase\\part2\\과제1\\학습관리시스템_프로젝트_시작소스_fastlms3\\fastlms3\\files";

    public String[] upload(String originalFileName, InputStream inputStream) {

        String saveFilename = "";
        String urlFilename = "";

        String[] arrFileName = DirectoryUtil.createDirectory(BASE_LOCAL_PATH, BASE_URL_PATH, originalFileName);

        saveFilename = arrFileName[0];
        urlFilename = arrFileName[1];

        try {
            File newFile = new File(saveFilename);
            FileCopyUtils.copy(inputStream, Files.newOutputStream(newFile.toPath()));
        } catch (IOException e) {
            log.info("############################ - 1");
            log.info(e.getMessage());
        }

        return arrFileName;
    }

    public void deleteFile(List<String> fileList) {

            for (String fileName : fileList) {
                try {
                    File file = new File(fileName);
                    if(file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    log.error("삭제 실패:{}", fileName, e);
                }
            }

    }
}
