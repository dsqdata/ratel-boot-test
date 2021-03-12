//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.ratel.config;


import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.ratel.framework.spring.security.RatelJwtUser;
import org.ratel.modules.security.service.OnlineUserService;
import org.ratel.modules.security.service.TokenProviderService;
import org.ratel.modules.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Expression;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class RatelAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(RatelAuthenticationSuccessHandler.class);
    @Autowired
    SysUserService sysUserService;

    @Autowired
    private TokenProviderService tokenProviderService;

    @Autowired
    private OnlineUserService onlineUserService;

    @Value("${ratel.oauth.clientId:system}")
    private String clientId;

    public RatelAuthenticationSuccessHandler() {
    }

    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        RatelJwtUser userDetails = (RatelJwtUser) authentication.getPrincipal();
        String token = tokenProviderService.createToken(authentication, clientId);

        Map<String, Object> map = new LinkedHashMap();
        map.put("timestamp", System.currentTimeMillis());
        map.put("token", token);
        map.put("username", userDetails.getUsername());
        map.put("userId", userDetails.getId());
        map.put("nickName", userDetails.getNickName());

        onlineUserService.save(authentication, userDetails, token, httpServletRequest);

        JSONObject jsonObject = new JSONObject(map);

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(200);
        httpServletResponse.getWriter().write(jsonObject.toJSONString());
    }
}
