package com.yule.user.controller.v1;

import com.yule.model.common.dtos.ResponseResult;
import com.yule.model.user.dtos.LoginDto;
import com.yule.user.service.ApUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "APP用户登录", description = "手机号密码登录、游客登录")
@RestController
@RequestMapping("api/v1/login")
public class ApUserLoginController {
    @Autowired
    private ApUserService apUserService;

    @Operation(summary = "登录", description = "传手机号和密码为正常登录，不传则为游客登录")
    @PostMapping("login_auth")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        return apUserService.login(loginDto);
    }
}
