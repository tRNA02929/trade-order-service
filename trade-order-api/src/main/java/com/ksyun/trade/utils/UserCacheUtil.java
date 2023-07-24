package com.ksyun.trade.utils;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.ksyun.trade.entity.UserInfoEntity;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// 用于缓存用户信息
public class UserCacheUtil {
    private static LoadingCache<Integer, UserInfoEntity> userCache;

    static {
        userCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1024)
                .expireAfterAccess(60, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, UserInfoEntity>() {
                    @CheckForNull
                    @Override
                    public UserInfoEntity load(@Nonnull Integer integer) {
                        return null;
                    }
                });
    }

    public static UserInfoEntity getUserInfo(int userId) {
        return userCache.get(userId);
    }
    public static void putUserInfo(int userId, Map<String, String> map) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUsername(map.get("username"));
        userInfoEntity.setEmail(map.get("email"));
        userInfoEntity.setPhone(map.get("phone"));
        userInfoEntity.setAddress(map.get("address"));
        userCache.put(userId, userInfoEntity);
    }
}
