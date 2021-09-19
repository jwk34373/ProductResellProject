package com.example.ProductResellProject.web;

import com.example.ProductResellProject.web.dto.HelloResponceDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponceDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponceDto(name, amount);
    }
}
