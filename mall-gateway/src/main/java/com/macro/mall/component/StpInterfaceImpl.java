package com.macro.mall.component;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.macro.mall.common.constant.AuthConstant;
import com.macro.mall.common.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auther macrozheng
 * @description Custom permission validation interface extension
 * @date 2024/1/25
 * @github https://github.com/macrozheng
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // Return the list of permission codes owned by this loginId
        if(StpUtil.getLoginType().equals(loginType)){
            // Backend user needs to return
            UserDto userdto = (UserDto) StpUtil.getSession().get(AuthConstant.STP_ADMIN_INFO);
            return userdto.getPermissionList();
        }else{
            // Frontend user does not need to return
            return null;
        }
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // Return the list of role codes owned by this loginId
        return null;
    }

}

