package com.papertrail.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {
    public static byte[] readFileFromPath(String filePath) {
        if(StringUtils.isBlank(filePath)){
            return null;
        }
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            log.warn("Failed to read file, no File found at : {}", filePath);
        }
        return null;
    }
}
