package com.yule.user.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yule.model.common.dtos.ResponseResult;
import com.yule.model.common.enums.AppHttpCodeEnum;
import com.yule.model.user.dtos.LoginDto;
import com.yule.model.user.pojos.ApUser;
import com.yule.user.mapper.ApUserMapper;
import com.yule.user.service.ApUserService;
import com.yule.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    /**
     * app登录
     *
     * @param loginDto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto loginDto) {
        //1.正常登录: 用户名 + 密码
        if (StringUtils.isNotBlank(loginDto.getPhone()) && StringUtils.isNotBlank(loginDto.getPassword())) {
            //1.1 查询用户信息：根据手机号查询
            ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, loginDto.getPhone()));
            if (apUser == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户信息不存在");
            }
            //1.2 对比密码
            String salt = apUser.getSalt(); // 盐值
            String password = loginDto.getPassword(); // 用户输入的密码
            String md5Password = DigestUtils.md5DigestAsHex((password + salt).getBytes()); // 加密后的密码
            //1.2.1 对比密码
            if (!md5Password.equals(apUser.getPassword())) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            //1.3 返回数据 JWT
            String token = AppJwtUtil.getToken(apUser.getId().longValue());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            //1.4 过滤敏感数据
            apUser.setSalt("");
            apUser.setPassword("");
            map.put("user", apUser);

            return ResponseResult.okResult(map);
        } else {
            //2.游客登陆
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(0L));
            return ResponseResult.okResult(map);
        }
    }
}
