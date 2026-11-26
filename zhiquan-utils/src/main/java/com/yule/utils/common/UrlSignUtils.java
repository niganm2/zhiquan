package com.yule.utils.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.SortedMap;

public enum UrlSignUtils {

    getUrlSignUtils;
    private static final Logger logger = LoggerFactory.getLogger(UrlSignUtils.class);

    /**
     * @param params 所有的请求参数都会在这里进行排序加密
     * @return 得到签名
     */
    public String getSign(SortedMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!"sign".equals(entry.getKey())) { //拼装参数,排除sign
                if (StringUtils.hasText(entry.getKey()) && StringUtils.hasText(entry.getValue())) {
                    sb.append(entry.getKey()).append('=').append(entry.getValue());
                }
            }
        }
        logger.info("Before Sign : {}", sb);
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

    /**
     * @param params 所有的请求参数都会在这里进行排序加密
     * @return 验证签名结果
     */
    public boolean verifySign(SortedMap<String, String> params) {
        if (params == null || !StringUtils.hasText(params.get("sign"))) {
            return false;
        }
        String sign = getSign(params);
        logger.info("verify Sign : {}", sign);
        return sign.equals(params.get("sign"));
    }

}