package com.app.springapp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class LocalFileUtil {

    @Value("${file.upload.dir}")
    private String uploadDir;

    public List<String> saveFiles(List<MultipartFile> files) throws IOException {
        List<String> savedNames = new ArrayList<>();
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            String original = file.getOriginalFilename();
            String ext = (original != null && original.contains("."))
                    ? original.substring(original.lastIndexOf("."))
                    : "";
            String savedName = UUID.randomUUID() + ext;
            file.transferTo(new File(dir, savedName));
            savedNames.add(savedName);
        }
        return savedNames;
    }
}
