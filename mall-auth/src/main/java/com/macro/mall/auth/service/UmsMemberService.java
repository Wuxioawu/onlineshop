package com.peng.sms.auth.service;

import com.peng.sms.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther macrozheng
 * @description The front-end member Service remotely invokes the Service
 * @date 2024/1/30
 * @github https://github.com/macrozheng
 */
@FeignClient("mall-portal")
public interface UmsMemberService {
    @PostMapping("/sso/login")
    CommonResult login(@RequestParam("username") String username, @RequestParam("password") String password);
}
