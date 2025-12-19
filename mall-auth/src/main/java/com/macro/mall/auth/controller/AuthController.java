package com.peng.sms.auth.controller;

import com.peng.sms.auth.domain.UmsAdminLoginParam;
import com.peng.sms.auth.service.UmsAdminService;
import com.peng.sms.auth.service.UmsMemberService;
import com.peng.sms.common.api.CommonResult;
import com.peng.sms.common.constant.AuthConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther macrozheng
 * @description Unified authentication and authorization interface
 * @date 2024/1/30
 * @github https://github.com/macrozheng
 */
@Controller
@Tag(name = "AuthController", description = "Unified authentication and authorization interface")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsMemberService memberService;

    @Operation(summary = "Return token after login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String clientId,
                              @RequestParam String username,
                              @RequestParam String password) {
        if(AuthConstant.ADMIN_CLIENT_ID.equals(clientId)){
            UmsAdminLoginParam loginParam = new UmsAdminLoginParam();
            loginParam.setUsername(username);
            loginParam.setPassword(password);
            return adminService.login(loginParam);
        }else if(AuthConstant.PORTAL_CLIENT_ID.equals(clientId)){
            return memberService.login(username,password);
        }else{
            return CommonResult.failed("clientId is incorrect");
        }
    }
}