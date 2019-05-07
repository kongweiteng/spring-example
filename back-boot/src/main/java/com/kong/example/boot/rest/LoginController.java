package com.kong.example.boot.rest;


import com.kong.example.boot.util.CookieUtils;
import com.kong.example.boot.util.RespEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = {"用户认证"})
public class LoginController {
    @Autowired
    HttpSession httpSession;

    @PostMapping("/login1")
    @ApiOperation("登录")
    public RespEntityUtil<String> login() {


        return RespEntityUtil.ok(20000, "admin-token");

    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public RespEntityUtil<Map> info(HttpServletRequest request, HttpServletResponse response) {
        String cookie = CookieUtils.getCookie(request, "vue_admin_template_token");
        System.err.println(cookie);

        CookieUtils.writeCookie(response, "nihao", "nihao");


        Object o = httpSession.getAttribute("vue_admin_template_token");
        if (o == null) {
            o = "spring boot 牛逼了!!!有端口" + request.getLocalPort() + "生成";
            httpSession.setAttribute("vue_admin_template_token", o);
        }
//        roles: ['admin'],
//        introduction: 'I am a super administrator',
//                avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
//                name: 'Super Admin'

        Map map = new HashMap();
        map.put("roles", Arrays.asList("admin"));
        map.put("introduction", "I am a super administrator");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");

        return RespEntityUtil.ok(20000, map);

    }


    @PostMapping("/logout")
    @ApiOperation("登出")
    public RespEntityUtil<String> logout() {
        return RespEntityUtil.ok(20000, "ok");

    }


}
