package com.example.ProductResellProject.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/api/login")
    public String requestLogin(@PathVariable String userId,
                               @PathVariable String userPwd){
        class Res{
            String id;
            String pwd;
        }
        Res res = new Res();
        res.id = userId;
        res.pwd = userPwd;

        Long answer = 123L;

        return "hi";
    }
}