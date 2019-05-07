package com.kong.example.boot.util;


import org.nutz.mapl.Mapl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LdapUtil {

    private LdapContextSource ldapContext;

    private static LdapTemplate ldapTemplate;

    @Autowired
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        LdapUtil.ldapTemplate = ldapTemplate;
    }

    /**
     * 根据用户名到LDAP服务器中获取用户的DN
     *
     * @param userName
     * @return
     * @throws
     */
    public static <T> T authenticate(final Class<T> type, String userName, String password) {
        EqualsFilter f = new EqualsFilter("uid", userName);

        boolean success = ldapTemplate.authenticate(LdapUtils.emptyLdapName(), f.toString(), password);
        if (!success) {
            // TODO: 2019/5/7 抛出自定义异常
        }
        if (success) {
            List<T> result = ldapTemplate.search(LdapUtils.emptyLdapName(), f.toString(), new AbstractContextMapper<T>() {
                @Override
                protected T doMapFromContext(DirContextOperations ctx) {
                    Attributes attributes = ctx.getAttributes();
                    return fromAttribute(type, attributes);
                }
            });
            return result.get(0);
        }
        return null;
    }

    /**
     * 根据LDAP属性转换为对象
     *
     * @param attributes
     * @return
     */
    protected static <T> T fromAttribute(Class<T> type, Attributes attributes) {
        Map<String, Object> kvs = new HashMap<String, Object>();

        NamingEnumeration<? extends Attribute> all = attributes.getAll();
        while (all.hasMoreElements()) {
            // 处理属性的多个值，这里只获取第一个值
            try {
                Attribute at = all.next();
                String key = at.getID();
                String value = null;
                NamingEnumeration<?> vs = at.getAll();

                if (vs.hasMore()) {
                    value = (String) vs.next();
                }
                if (value != null) {
                    kvs.put(key, value);
                }
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        T obj = Mapl.maplistToT(Mapl.toMaplist(kvs), type);
        return obj;
    }
}
