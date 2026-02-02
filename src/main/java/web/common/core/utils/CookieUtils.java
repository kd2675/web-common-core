package web.common.core.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
public class CookieUtils {
    private static final String LOCAL_DOMAIN = "localhost";
    private static final String CONTEXT_DOMAIN = "";

    public static void createCookie(String name, String value) {
        createCookie(name, value, 60 * 60 * 24, false);
    }

    public static void createCookie(String name, String value, int maxAge) {
        createCookie(name, value, maxAge, false);
    }

    public static void createCookie(String name, String value, int maxAge, boolean secure) {
        HttpServletResponse res = HttpUtils.getResponse();

        try {
            value = URLEncoder.encode(value, "UTF-8");

            log.debug("create cookie: {}", name);
            Cookie c = new Cookie(name, value);
            c.setPath("/");
            c.setMaxAge(maxAge);
            c.setHttpOnly(true);
            c.setSecure(secure);
            c.setDomain(CONTEXT_DOMAIN);
            res.addCookie(c);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCookie(String name) {
        HttpServletResponse res = HttpUtils.getResponse();

        log.debug("delete cookie: {}", name);
        Cookie c = new Cookie(name, "");
        c.setPath("/");
        c.setMaxAge(0);
        c.setHttpOnly(true);
        c.setDomain(CONTEXT_DOMAIN);
        res.addCookie(c);
    }
}
