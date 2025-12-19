package com.peng.sms.auth.service;

import com.peng.sms.auth.domain.UmsAdminLoginParam;
import com.peng.sms.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther macrozheng
 * @description The background user Service remotely invokes the Service
 * @date 2024/1/30
 * @github https://github.com/macrozheng
 */
@FeignClient("mall-admin")
public interface UmsAdminService {

    @PostMapping("/admin/login")
    CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam);
}
