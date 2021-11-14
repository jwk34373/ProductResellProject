package com.example.ProductResellProject.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("aaa")
    public String aaa() {
        return "aaa";
    }

    @PostMapping("token")
    public String token() {
        return "<h1>token</h1>";
    }
}
