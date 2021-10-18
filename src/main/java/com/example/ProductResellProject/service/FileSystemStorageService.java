package com.example.ProductResellProject.service;

import com.example.ProductResellProject.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileSystemStorageService {

    private final Path rootLocation = Path.of("upload-dir");

    public void store(MultipartFile file, Long postId) {
        log.info("file.getOriginalFilename() : "+ file.getOriginalFilename());
        log.info("file.getContentType() :" + file.getContentType());


        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("failed to store");
        }

    }
}
