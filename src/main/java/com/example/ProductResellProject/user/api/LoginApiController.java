package com.example.ProductResellProject.user.api;

import com.example.ProductResellProject.user.service.UserService;
import com.example.ProductResellProject.user.api.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final UserService userService;

    @RequestMapping(value = "/signup/request", method = RequestMethod.POST)
    @ResponseBody
    public Long requestSignUp(@RequestBody @Valid UserInfoDto userInfoDto,
                              BindingResult errors) {
        if(errors.hasErrors()){
            errors.getFieldErrors().stream().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMsg = fieldError.getDefaultMessage();
                throw new IllegalArgumentException(fieldName + " : " + errorMsg);
            });
        }
        return userService.save(userInfoDto);
    }
}
