package com.peng.sms.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.peng.sms.common.api.CommonResult;
import com.peng.sms.common.constant.AuthConstant;
import com.peng.sms.util.StpMemberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;

/**
 * @auther macrozheng
 * @description Sa-Token related configuration
 * @date 2023/11/28
 * @github https://github.com/macrozheng
 */
@Configuration
public class SaTokenConfig {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Register Sa-Token global filter
     */
    @Bean
    public SaReactorFilter getSaReactorFilter(IgnoreUrlsConfig ignoreUrlsConfig) {
        return new SaReactorFilter()
                // Intercept address
                .addInclude("/**")
                // Configure whitelist paths
                .setExcludeList(ignoreUrlsConfig.getUrls())
                // Authentication method: executed on each access
                .setAuth(obj -> {
                    // Directly allow OPTIONS preflight requests
                    SaRouter.match(SaHttpMethod.OPTIONS).stop();
                    // Login authentication: Mall frontend member authentication
                    SaRouter.match("/mall-portal/**", r -> StpMemberUtil.checkLogin()).stop();
                    // Login authentication: Admin backend user authentication
                    SaRouter.match("/mall-admin/**", r -> StpUtil.checkLogin());
                    // Permission authentication: Admin backend user permission verification
                    // Get permission rules required for each interface path cached in Redis
                    Map<Object, Object> pathResourceMap = redisTemplate.opsForHash().entries(AuthConstant.PATH_RESOURCE_MAP);
                    // Get permissions required to access the current interface (when one path corresponds to multiple resources, having any one resource allows access to that path)
                    List<String> needPermissionList = new ArrayList<>();
                    // Get current request path
                    String requestPath = SaHolder.getRequest().getRequestPath();
                    // Create path matcher
                    PathMatcher pathMatcher = new AntPathMatcher();
                    Set<Map.Entry<Object, Object>> entrySet = pathResourceMap.entrySet();
                    for (Map.Entry<Object, Object> entry : entrySet) {
                        String pattern = (String) entry.getKey();
                        if (pathMatcher.match(pattern, requestPath)) {
                            needPermissionList.add((String) entry.getValue());
                        }
                    }
                    // Authenticate when the interface requires permissions
                    if(CollUtil.isNotEmpty(needPermissionList)){
                        SaRouter.match(requestPath, r -> StpUtil.checkPermissionOr(Convert.toStrArray(needPermissionList)));
                    }
                })
                // setAuth method exception handling
                .setError(this::handleException);
    }

    /**
     * Custom exception handling
     */
    private CommonResult handleException(Throwable e) {
        // Set error return format to JSON
        ServerWebExchange exchange = SaReactorSyncHolder.getContext();
        HttpHeaders headers = exchange.getResponse().getHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Cache-Control","no-cache");
        CommonResult result = null;
        if(e instanceof NotLoginException){
            result = CommonResult.unauthorized(null);
        }else if(e instanceof NotPermissionException){
            result = CommonResult.forbidden(null);
        }else{
            result = CommonResult.failed(e.getMessage());
        }
        return result;
    }
}

