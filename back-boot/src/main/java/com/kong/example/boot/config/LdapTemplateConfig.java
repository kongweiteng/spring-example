package com.kong.example.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapTemplateConfig {
    @Value("${spring.ldap.urls}")
    private String url;
    @Value("${spring.ldap.base}")
    private String base;

    @Bean
    public LdapTemplate ldapTemplate() {
        LdapContextSource ldapContext = new LdapContextSource();
        ldapContext.setUrl(url);
        ldapContext.setBase(base);
        ldapContext.setAnonymousReadOnly(true);
        ldapContext.afterPropertiesSet();
        return new LdapTemplate(ldapContext);
    }

}
