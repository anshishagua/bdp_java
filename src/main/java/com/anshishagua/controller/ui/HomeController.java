package com.anshishagua.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * HomeController.java
 *
 * @author lixiao
 * @date 2021-03-25
 */

@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "login";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/test")
    @ResponseBody
    public Map<String, Object> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "benben");

        return map;
    }
}
