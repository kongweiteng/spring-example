package com.kong.example.boot.rest.test;

import com.kong.example.boot.entity.Person;
import com.kong.example.boot.util.LdapUtil;
import com.kong.example.boot.util.RespEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ldap")
@Api(tags = {"ldap使用"})
public class LdapController {
    @Autowired
    LdapTemplate ldapTemplate;


    @PostMapping("/login")
    @ApiOperation("登录方法")
    public RespEntityUtil<Person> redisSet(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        EqualsFilter f = new EqualsFilter("uid", userName);
        Person authenticate = LdapUtil.authenticate(Person.class, userName, password);
        return RespEntityUtil.ok(authenticate);
    }


}
