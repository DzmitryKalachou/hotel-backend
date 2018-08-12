package com.example.hotel.security;


import java.util.HashMap;

public final class SecurityContextHolder {

    private static final String USER_ID_PARAM = "USER_ID_PARAM";
    private static final String TOKEN_PARAM = "TOKEN";

    private static final ThreadLocal<HashMap<String, String>> threadLocal = new ThreadLocal<>();

    private static SecurityContextHolder securityContextHolder;

    private SecurityContextHolder() {

    }

    public static SecurityContextHolder getInstance() {
        synchronized (SecurityContextHolder.class) {
            if (securityContextHolder == null) {
                securityContextHolder = new SecurityContextHolder();
            }
            return securityContextHolder;
        }
    }

    public void putUserId(final Long userId) {
        final HashMap<String, String> map = extractMap();
        map.put(USER_ID_PARAM, userId.toString());
        threadLocal.set(map);
    }

    public Long getUserId() {
        final HashMap<String, String> map = extractMap();
        final String userId = map.get(USER_ID_PARAM);
        return userId != null ? Long.parseLong(userId) : null;
    }

    public void putToken(final String token) {
        final HashMap<String, String> map = extractMap();
        map.put(TOKEN_PARAM, token);
        threadLocal.set(map);
    }

    public String getToken() {
        final HashMap<String, String> map = extractMap();
        return map.get(TOKEN_PARAM);
    }

    private HashMap<String, String> extractMap() {
        final HashMap<String, String> map = threadLocal.get();
        if (map == null) {
            return new HashMap<>();
        }
        return map;
    }

    void clean() {
        threadLocal.remove();
    }

}
