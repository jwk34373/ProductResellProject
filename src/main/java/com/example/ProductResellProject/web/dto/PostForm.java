package com.example.ProductResellProject.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostForm {

    private Long id;
    private String title;
    private String content;
//    private List<MultipartFile> imageFiles;
    private MultipartFile imageFile; //

}
