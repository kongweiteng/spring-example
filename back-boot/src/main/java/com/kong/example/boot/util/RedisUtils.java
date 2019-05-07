package com.kong.example.boot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtils {

    private static RedisTemplate redisTemplate;

    //    @Resource(name = "redisTemplate")
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    public static Boolean setString(String key, Object value, long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, time, unit);
        return true;
    }

    public static Boolean setString(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    public static <T> T getString(String key, Class<T> clazz) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o != null) {
            return JSON.parseObject(o.toString(), clazz, new Feature[0]);
        }
        return null;
    }

    public static Object getString(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        return o;
    }

    public static Boolean deleteString(String key) {
        Boolean delete = redisTemplate.opsForValue().getOperations().delete(key);
        return delete;
    }

    public static Boolean expireString(String key, long timeout, TimeUnit timeUnit) {
        Boolean expire = redisTemplate.opsForValue().getOperations().expire(key, timeout, timeUnit);
        return expire;
    }


    public static Boolean putAllHash(Object key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    public static <T> T getHash(Object key, Object hashKey, Class<T> clazz) {
        Object o = redisTemplate.opsForHash().get(key, hashKey);
        if (o != null) {
            T t = null;
            try {
                t = JSON.parseObject(JSON.toJSONString(o), clazz, new Feature[0]);
                return t;
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("RedisUtils:getHash:" + key + hashKey + clazz);
                return null;
            }
        }
        return null;
    }

    public static <T> List<T> getAllHash(Object key, Class<T> clazz) {
        List<T> ts = new ArrayList<>();
        log.info("getAllHash从redis获取数据开始key：" + key + ",time:" + System.currentTimeMillis());
        ArrayList allHash = getAllHash(key);
        log.info("getAllHash从redis获取数据结束key：" + key + ",time:" + System.currentTimeMillis());
        if (allHash != null) {
            for (Object o : allHash) {
                T t = JSON.parseObject(JSON.toJSONString(o), clazz);
                ts.add(t);
            }
        }
        return ts;
    }


    public static ArrayList getAllHash(Object key) {
        Object o = redisTemplate.opsForHash().values(key);
        if (o != null && ((List) o).size() > 0) {
            return JSON.parseObject(JSON.toJSONString(o), ArrayList.class, new Feature[0]);
        }
        return null;
    }

    public static Boolean putHash(Object key, Object hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("RedisUtils:putHash:" + key + hashKey + value);
        }
        return true;
    }

    public static Long getHashSize(Object key) {
        Long size = redisTemplate.opsForHash().size(key);
        return size;
    }

    public static Boolean deleteHash(Object key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
        return true;
    }

    public static Boolean leftPushAllList(Object key, Collection value) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("RedisUtils:putHash:" + key + value);
        }
        return true;
    }


    public static <T> List<T> getAllList(Object key, Class<T> clazz) {
//        List<T> ts = new ArrayList<>();
//        List range = redisTemplate.opsForList().range(key, 0, getListSize(key));
//        if (range != null) {
//            for (Object object : range) {
//                T t = JSON.parseObject(JSON.toJSONString(object), clazz);
//                ts.add(t);
//            }
//        }
        Long listSize = getListSize(key);
        int i = Integer.parseInt(listSize.toString());
        return getListByStartAndEnd(key, clazz, 0, i);
    }


    public static Long getListSize(Object key) {
        Long size = redisTemplate.opsForList().size(key);
        return size;
    }

    public static <T> List<T> getListByStartAndEnd(Object key, Class<T> clazz, int start, int end) {
        List<T> ts = new ArrayList<>();
        List range = redisTemplate.opsForList().range(key, start, end);
        if (range != null) {
            for (Object object : range) {
                T t = JSON.parseObject(JSON.toJSONString(object), clazz);
                ts.add(t);
            }
        }
        return ts;
    }

    /**
     * redis 计数器
     */
    public static Long number(String key, Long num) {
        Long increment = redisTemplate.opsForValue().increment(key, num);
        return increment;
    }

}
