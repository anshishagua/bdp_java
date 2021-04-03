package com.anshishagua.controller.ui;

import com.anshishagua.object.User;
import com.anshishagua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * RegisterController.java
 *
 * @author lixiao
 * @date 2021-03-25
 */

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserService userService;

    @RequestMapping("")
    public String home() {
        return "register";
    }
}
