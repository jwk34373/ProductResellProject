package com.example.ProductResellProject.web;

import com.example.ProductResellProject.service.AuthService;
import com.example.ProductResellProject.web.dto.TokenDto;
import com.example.ProductResellProject.web.dto.TokenRequestDto;
import com.example.ProductResellProject.web.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    @PostMapping("/login/request")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.login(userRequestDto));
    }
    // test !!!

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}