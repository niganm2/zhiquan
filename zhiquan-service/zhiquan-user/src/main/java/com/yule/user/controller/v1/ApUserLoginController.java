package com.yule.user.controller.v1;

import com.yule.model.common.dtos.ResponseResult;
import com.yule.model.user.dtos.LoginDto;
import com.yule.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/login")
public class ApUserLoginController {
    @Autowired
    private ApUserService apUserService;

    @PostMapping("login_auth)")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        return apUserService.login(loginDto);
    }
}
