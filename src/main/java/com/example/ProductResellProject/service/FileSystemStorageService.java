package com.example.ProductResellProject.service;

import com.example.ProductResellProject.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileSystemStorageService { // 주석 추가 필요

    private final Path rootLocation = Path.of("upload-dir");

    public void store(MultipartFile file, Long postId) {
        log.info("file.getContentType() :" + file.getContentType()); // image 확인 구현 필요
        if(!Files.isDirectory(rootLocation)){   // 디렉토리 확인 및 생성
            try {
                Files.createDirectories(rootLocation);
            }
            catch (IOException e) {
                throw new StorageException("Could not initialize storage", e);
            }
        }

        Path destinationFile = this.rootLocation
                .resolve(postId.toString() +".jpg")
                .normalize().toAbsolutePath();

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("failed to store");
        }
    }
}
