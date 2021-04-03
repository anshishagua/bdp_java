package com.anshishagua.controller.ui;

import com.anshishagua.object.Application;
import com.anshishagua.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * ApplicationController.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

@Controller
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping("")
    public String home() {
        return "application";
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        List<Application> applications = applicationService.getAllApplications();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list_application");
        modelAndView.addObject("applications", applications);

        return modelAndView;
    }

    @RequestMapping("/deploy")
    public ModelAndView deploy(
            @RequestParam("application_name") String applicationName,
            @RequestParam("message") String message,
            @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("application");

        try {
            String destPath = String.format(
                    "/tmp/application-%s.zip",
                    UUID.randomUUID().toString().replace("-", ""));

            Files.copy(file.getInputStream(), Paths.get(destPath));
            applicationService.addApplication(applicationName, message, destPath);
        } catch (IOException ex) {
            modelAndView.addObject("error", ex.toString());

            return modelAndView;
        }


        return null;
    }
}
