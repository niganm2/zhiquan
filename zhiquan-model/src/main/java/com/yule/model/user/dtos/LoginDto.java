package com.yule.model.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录参数")
public class LoginDto {
    /**
     * 手机号
     */
    @Schema(description = "手机号，不传则为游客登录")
    private String phone;
    /**
     * 密码
     */
    @Schema(description = "密码，不传则为游客登录")
    private String password;
}
