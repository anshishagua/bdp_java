package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ApplicationMapper.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

@Mapper
public interface ApplicationMapper {
    Application getLatestApplication(@Param("applicationName") String applicationName);
    void addApplication(
            @Param("applicationName") String applicationName,
            @Param("message") String message,
            @Param("version") int version,
            @Param("path") String path
            );
    List<Application> getApplications(String applicationName);
    List<Application> getAllApplications();
    int deleteApplication(@Param("applicationId") int applicationId);
}
