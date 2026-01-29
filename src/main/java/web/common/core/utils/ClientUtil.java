package web.common.core.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ClientUtil {

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * User-Agent를 기반으로 디바이스/클라이언트 종류 가져오기
     */
    public static String getDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return "UNKNOWN";
        }

        userAgent = userAgent.toLowerCase();

        if (userAgent.contains("postmanruntime")) {
            return "POSTMAN";
        } else if (userAgent.contains("android")) {
            return "ANDROID";
        } else if (userAgent.contains("iphone") || userAgent.contains("ipad") || userAgent.contains("ipod")) {
            return "IOS";
        } else if (userAgent.contains("windows")) {
            return "WINDOWS";
        } else if (userAgent.contains("macintosh") || userAgent.contains("mac os")) {
            return "MAC";
        } else if (userAgent.contains("linux")) {
            return "LINUX";
        } else {
            return "UNKNOWN";
        }
    }
}