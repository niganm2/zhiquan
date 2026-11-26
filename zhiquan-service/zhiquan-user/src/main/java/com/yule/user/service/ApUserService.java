package com.yule.user.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yule.model.common.dtos.ResponseResult;
import com.yule.model.user.dtos.LoginDto;
import com.yule.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser> {
    /**
     * app登录
     * @param loginDto
     * @return
     */
    public ResponseResult login(LoginDto loginDto);
}
