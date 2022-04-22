package com.example.ProductResellProject.Post.domain;

import javax.persistence.Embeddable;

@Embeddable
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
