package com.ksyun.trade.dto;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.Objects;

public class StringRedisUpstream {

    // 限流脚本
    private static final String lua = "" +
            "local waterKey = KEYS[1]\n" +
            "local lastTimeKey = KEYS[2]\n" +
            "local water = tonumber(redis.call('get', waterKey) or 0)\n" +
            "local limit = tonumber(ARGV[1])\n" +
            "local now = tonumber(ARGV[2])\n" +
            "local lastTime = tonumber(redis.call('get', lastTimeKey) or now)\n" +
//            "local t = (now - lastTime)*5/1000\n"+
//            "return t\n";
            "water = water - (now - lastTime)*5/1000\n" +
            "if water < 0 then\n" +
            "    water = 0\n" +
            "end\n" +
            "if limit - water < 1   then\n" +
            "    return 0\n" +
            "else\n" +
            "    redis.call(\"set\", waterKey, water+1)\n" +
            "    redis.call(\"set\", lastTimeKey, now)\n" +
            "    return 1\n" +
            "end";

    private static final ArrayList<String> keys;

    static {
        keys = new ArrayList<>();
        keys.add("libo14:listUpstreamInfo:water");
        keys.add("libo14:listUpstreamInfo:lastTime");
    }

    public static boolean listUpstreamInfo(StringRedisTemplate stringRedisTemplate) {
        Object o = stringRedisTemplate.execute(
                RedisScript.of(lua, Object.class),
                keys, "5", Long.toString(System.currentTimeMillis()));

        if (Objects.equals(o.toString(), "[1]")) {
            return true;
        } else {
            return false;
        }
    }
}
