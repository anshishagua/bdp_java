package com.anshishagua.service;

import com.anshishagua.mybatis.mapper.ApplicationMapper;
import com.anshishagua.object.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ApplicationService.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

@Service
public class ApplicationService {
    @Autowired
    private ApplicationMapper applicationMapper;

    public Application getLatestApplication(String applicationName) {
        return applicationMapper.getLatestApplication(applicationName);
    }

    public void addApplication(String applicationName, String message, String path) {
        Application application = applicationMapper.getLatestApplication(applicationName);
        int version = 0;

        if (application != null) {
            version = application.getVersion() + 1;
        }

        applicationMapper.addApplication(applicationName, message, version, path);
    }

    public List<Application> getApplications(String applicationName) {
        return applicationMapper.getApplications(applicationName);
    }

    public List<Application> getAllApplications() {
        return applicationMapper.getAllApplications();
    }

    public int deleteApplication(int applicationId) {
        return applicationMapper.deleteApplication(applicationId);
    }
}
