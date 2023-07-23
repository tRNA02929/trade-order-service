package com.ksyun.trade.utils;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.ksyun.trade.dto.UserInfoDTO;
import com.ksyun.trade.rest.RestResult;
import org.springframework.web.client.RestTemplate;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserCacheUtil {
    private static LoadingCache<Integer, UserInfoDTO> userCache;

    static {
        userCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1024)
                .expireAfterAccess(60, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, UserInfoDTO>() {
                    @CheckForNull
                    @Override
                    public UserInfoDTO load(@Nonnull Integer integer) {
                        return null;
                    }
                });
    }

    public static UserInfoDTO getUserInfo(int userId) {
        return userCache.get(userId);
    }
    public static void putUserInfo(int userId, Map<String, String> map) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername(map.get("username"));
        userInfoDTO.setEmail(map.get("email"));
        userInfoDTO.setPhone(map.get("phone"));
        userInfoDTO.setAddress(map.get("address"));
        userCache.put(userId, userInfoDTO);
    }
}
