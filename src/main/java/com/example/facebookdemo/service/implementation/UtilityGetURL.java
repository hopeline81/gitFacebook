package com.example.facebookdemo.service.implementation;

import javax.servlet.http.HttpServletRequest;

public class UtilityGetURL {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
