package com.jypark.coding.common.advice;

import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class AdviceUtils {

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public static String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    public static String getParameters(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        StringBuilder sb = new StringBuilder();
        String str = "";
        if (!params.isEmpty()) {
            params.forEach((key, value) -> {
                sb.append(key).append("=").append(Arrays.asList(value)).append("&");
            });
            String tmpStr = sb.toString();
            str = tmpStr.substring(0, tmpStr.length() - 1);
        }
        return str;
    }
}
