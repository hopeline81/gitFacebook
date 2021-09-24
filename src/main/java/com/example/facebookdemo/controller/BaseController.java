package com.example.facebookdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {

    public ModelAndView send(String viewName) {
        return new ModelAndView(viewName);
    }

    public ModelAndView send(String viewName, String objectName, Object object) {
        ModelAndView modelAndView = new ModelAndView(viewName + ".html");
        modelAndView.addObject(objectName, object);
        return modelAndView;
    }

    public ModelAndView redirect(String endPoint) {

        return new ModelAndView("redirect:" + endPoint);
    }

    public ModelAndView redirect(String endPoint, String objectName, Object object) {
        ModelAndView modelAndView = new ModelAndView(("redirect:" + endPoint));
        modelAndView.addObject(objectName, object);
        return modelAndView;
    }
}
