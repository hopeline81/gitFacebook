package com.example.facebookdemo.service.implementation.util;

import javax.servlet.http.HttpServletRequest;

public class GetURLUtil {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
