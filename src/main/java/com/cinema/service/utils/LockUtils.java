package com.cinema.service.utils;

import java.util.concurrent.ConcurrentHashMap;

public class LockUtils {
    private static final ConcurrentHashMap<Object, Object> locks = new ConcurrentHashMap<>();

    public static Object lockFor(Object object) {
        return locks.computeIfAbsent(object, k -> new Object());
    }
}
